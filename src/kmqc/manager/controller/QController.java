package kmqc.manager.controller;

import kmqc.manager.controller.memory.CMem;
import kmqc.manager.controller.memory.QMem;
import kmqc.manager.controller.qpu.AddrDevice;
import kmqc.manager.controller.qpu.Placing;
import kmqc.manager.controller.qpu.ProcessingUnit;

import kpfu.terentyev.quantum.emulator.api.Helper;
import kpfu.terentyev.quantum.util.ComplexDouble;

/**
* Класс, реализующий работу контроллера.
*
* @author rstm-sf
* @version alpha
*/
public class QController {

    /**
    * Создание:
    * помошника, обеспечивающий связь с API;
    * процессорного устройства;
    * квантовой памяти;
    * классической памяти.
    */
    public QController() {
        this.helper = new Helper();
        this.qpu = new ProcessingUnit(this.helper, 1);
        this.qmem = new QMem(this.helper, 4);
        this.cmem = new CMem(2);
    }

    /**
    * Инициализация классического бита в ячейке классической памяти. 
    *
    * @param idxCMem Индекс ячейки классической памяти.
    * @param state   Значение бита.
    */
    public void init(int idxCMem, Integer state) {
        cmem.setState(idxCMem, state);
    }

    /**
    * Инициализация состояния кубита в ячейке квантовой памяти. 
    *
    * @param idxQMem Индекс ячейки квантовой памяти.
    * @param alpha   Вероятность состояния &#124;0&gt;.
    * @param beta    Вероятность состояния &#124;1&gt;.
    */
    public void init(
        int idxQMem, ComplexDouble alpha, ComplexDouble beta) throws Exception {
        qmem.initState(idxQMem, alpha, beta);
    }

    /**
    * Измерение состояния кубита в ячейке квантовой памяти в классическю память. 
    *
    * @param idxQMem Индекс ячейки квантовой памяти.
    * @param idxCMem Индекс ячейки классической памяти.
    */
    public void measure(int idxQMem, int idxCMem) throws Exception {
        cmem.setState(idxCMem, qmem.measureState(idxQMem));
    }

    /**
    * Перенос состояния кубита из ячейки квантовой памяти в вывод транзистора. 
    *
    * @param idxQMem Индекс ячейки квантовой памяти.
    * @param addr    Адресс вывода транзистора.
    */
    public void load(int idxQMem, AddrDevice addr) {
        switch(addr.placing) {
        case Left:
            qpu.setLeftState(addr.idxTransistor, qmem.getRidState(idxQMem));
            break;
        case Center:
            qpu.setCenterState(addr.idxTransistor, qmem.getRidState(idxQMem));
            break;
        case Right:
            qpu.setRightState(addr.idxTransistor, qmem.getRidState(idxQMem));
            break;
        }
    }

    /**
    * Перенос состояния кубита из вывода транзистора в ячейку квантовой памяти. 
    *
    * @param addr    Адресс вывода транзистора.
    * @param idxQMem Индекс ячейки квантовой памяти.
    */
    public void store(AddrDevice addr, int idxQMem) {
        switch(addr.placing) {
        case Left:
            qmem.setState(idxQMem, qpu.getRidLeftState(addr.idxTransistor));
            break;
        case Center:
            qmem.setState(idxQMem, qpu.getRidCenterState(addr.idxTransistor));
            break;
        case Right:
            qmem.setState(idxQMem, qpu.getRidRightState(addr.idxTransistor));
            break;
        }
    }

    /**
    * Включение режима CQET в указанном транзисторе, действующий на все выводы.
    * "Центральный" вывод является контролирующим, представленный состоянием
    * первого физического кубита из логического кубита.
    *
    * @param idxTransistor Индекс транзистора.
    * @param theta         Угол поворота.
    */
    public void opCQET(int idxTransistor, double theta) throws Exception {
        qpu.opCQET(idxTransistor, theta);
    }

    /**
    * Включение режима QET в указанном транзисторе,
    * действующий на "левый" и "правый" вывод.
    *
    * @param idxTransistor Индекс транзистора.
    * @param theta         Угол поворота.
    */
    public void opQET(int idxTransistor, double theta) throws Exception {
        qpu.opQET(idxTransistor, theta);
    }

    /**
    * Включение режима PHASE в указанном транзисторе,
    * действующий на "левый" и "правый" вывод.
    *
    * @param idxTransistor Индекс транзистора.
    * @param theta         Угол поворота.
    */
    public void opPHASE(int idxTransistor, double theta) throws Exception {
        qpu.opPHASE(idxTransistor, theta);
    }

    /**
    * Получение значения классического бита из ячейки памяти.
    *
    * @param idx Индекс ячейки классической памяти.
    * @return Значение бита.
    */
    public Integer getIdxCMem(int idx) {
        return cmem.getState(idx);
    }

    /** Помошник, обеспечивающий связь с API */
    private Helper helper;

    /** Процессорное устройство */
    private ProcessingUnit qpu;

    /** Квантовая память */
    private QMem qmem;

    /** Классическая память */
    private CMem cmem;
}