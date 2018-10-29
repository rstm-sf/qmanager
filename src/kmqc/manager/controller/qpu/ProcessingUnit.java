package kmqc.manager.controller.qpu;

import java.util.ArrayList;
import java.util.List;

import kpfu.terentyev.quantum.emulator.api.Helper;

/**
* Класс, реализующий работу процессорного устройства.
*
* @author rstm-sf
* @version alpha
*/
public class ProcessingUnit {

    /**
    * Создание процессорного устройства с указанным количеством транзисторов. 
    *
    * @param helper             Обеспечивает связь с API.
    * @param countOfTransistors Количество транзисторов.
    */
    public ProcessingUnit(Helper helper, int countOfTransistors) {
        this.helper = helper;
        this.countOfTransistors = countOfTransistors;
        unit = new ArrayList<Transistor>(countOfTransistors);
        for (int i = 0; i < countOfTransistors; i++) {
            unit.add(new Transistor(this.helper));
        }
    }

    /**
    * Переносит состояние кубита в "левый" вывод указанного транзистора.
    *
    * @param idxTransistor Индекс транзистора.
    * @param qubitInfo     Состояние кубита.
    */
    public void setLeftState(
        int idxTransistor, Helper.QubitInfo qubitInfo) {
        unit.get(idxTransistor).setLeftState(qubitInfo);
    }

    /**
    * Переносит состояние кубита в "центральный" вывод указанного транзистора.
    *
    * @param idxTransistor Индекс транзистора.
    * @param qubitInfo     Состояние кубита.
    */
    public void setCenterState(
        int idxTransistor, Helper.QubitInfo qubitInfo) {
        unit.get(idxTransistor).setCenterState(qubitInfo);
    }

    /**
    * Переносит состояние кубита в "правый" вывод указанного транзистора.
    *
    * @param idxTransistor Индекс транзистора.
    * @param qubitInfo     Состояние кубита.
    */
    public void setRightState(
        int idxTransistor, Helper.QubitInfo qubitInfo) {
        unit.get(idxTransistor).setRightState(qubitInfo);
    }

    /**
    * Возвращает состояние кубита из "левого" вывода указанного транзистора,
    * обнуляя состояние ячейки.
    *
    * @param idxTransistor Индекс транзистора.
    * @return Состояние кубита.
    */
    public Helper.QubitInfo getRidLeftState(int idxTransistor) {
        return unit.get(idxTransistor).getRidLeftState();
    }

    /**
    * Возвращает состояние кубита из "центрального" вывода указанного транзистора,
    * обнуляя состояние ячейки.
    *
    * @param idxTransistor Индекс транзистора.
    * @return Состояние кубита.
    */
    public Helper.QubitInfo getRidCenterState(int idxTransistor) {
        return unit.get(idxTransistor).getRidCenterState();
    }

    /**
    * Возвращает состояние кубита из "правого" вывода указанного транзистора,
    * обнуляя состояние ячейки.
    *
    * @param idxTransistor Индекс транзистора.
    * @return Состояние кубита.
    */
    public Helper.QubitInfo getRidRightState(int idxTransistor) {
        return unit.get(idxTransistor).getRidRightState();
    }

    /**
    * Включение режима QET в указанном транзисторе,
    * действующий на "левый" и "правый" вывод.
    *
    * @param idxTransistor Индекс транзистора.
    * @param theta         Угол поворота.
    */
    public void opQET(int idxTransistor, double theta) throws Exception {
        unit.get(idxTransistor).opQET(theta);
    }

    /**
    * Включение режима PHASE в указанном транзисторе,
    * действующий на "левый" и "правый" вывод.
    *
    * @param idxTransistor Индекс транзистора.
    * @param theta         Угол поворота.
    */
    public void opPHASE(int idxTransistor, double theta) throws Exception {
        unit.get(idxTransistor).opPHASE(theta);
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
        unit.get(idxTransistor).opCQET(theta);
    }

    private Helper helper;

    private int countOfTransistors;
    private List<Transistor> unit;
}