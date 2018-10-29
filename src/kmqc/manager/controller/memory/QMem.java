package kmqc.manager.controller.memory;

import java.util.ArrayList;
import java.util.List;

import kpfu.terentyev.quantum.emulator.api.Helper;
import kpfu.terentyev.quantum.util.ComplexDouble;

/**
* Класс, реализующий работу квантовой памяти.
*
* @author rstm-sf
* @version alpha
*/
public class QMem {

    /**
    * Создает ячейки квантовой памяти для хранения состояния кубитов.
    *
    * @param helper      Обеспечивает связь с API.
    * @param maxCellQMem Количество ячеек в памяти.
    */
    public QMem(Helper helper, int maxCellQMem) {
        this.helper = helper;
        this.maxCellQMem = maxCellQMem;
        memory = new ArrayList<CellQMem>(maxCellQMem);
        for (int i = 0; i < maxCellQMem; i++) {
            memory.add(new CellQMem(this.helper));
        }
    }

    /**
    * Возвращает максимальное количество ячеек памяти.
    *
    * @return Максимальное количество ячеек.
    */
    public int getMaxCountOfQubits() {
        return maxCellQMem;
    }

    /**
    * Переносит состояние кубита в ячеку памяти.
    *
    * @param idxCell   Индекс ячейки.
    * @param qubitInfo Состояние кубита.
    */
    public void setState(int idxCell, Helper.QubitInfo qubitInfo) {
        memory.get(idxCell).setState(qubitInfo);
    }

    /**
    * Возвращает состояние кубита из ячейки памяти.
    *
    * @param idxCell Индекс ячейки памяти.
    * @return Состояние кубита.
    */
    public Helper.QubitInfo getRidState(int idxCell) {
        return memory.get(idxCell).getRidState();
    }

    /**
    * Создание кубита в заданном состоянии &#124;\psi&gt; = alpha * &#124;0&gt; + beta * &#124;1&gt;.
    *
    * @param idxCell Индекс ячейки памяти.
    * @param alpha   Вероятность состояния &#124;0&gt;.
    * @param beta    Вероятность состояния &#124;1&gt;.
    */
    public void initState(
        int idxCell, ComplexDouble alpha, ComplexDouble beta) throws Exception {
        Helper.QubitInfo qubitInfo = helper.initNewQubit(alpha, beta);
        memory.get(idxCell).setState(qubitInfo);
    }

    /**
    * Измеряет состояние кубита из ячейки памяти.
    *
    * @param idxCell Индекс ячейки памяти.
    * @return Измеренное состояние кубита.
    */
    public int measureState(int idxCell) throws Exception {
        return memory.get(idxCell).measure();
    }

    private Helper helper;

    private int maxCellQMem;
    private List<CellQMem> memory;
}