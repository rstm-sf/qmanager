package kmqc.manager.controller.memory;

import java.util.ArrayList;
import java.util.List;

import kpfu.terentyev.quantum.simulator.api.Helper;
import kpfu.terentyev.quantum.util.Complex;

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
    * @param alpha   Амплитуда вероятности состояния &#124;0&gt;.
    * @param beta    Амплитуда вероятности состояния &#124;1&gt;.
    */
    public void initState(
        int idxCell, Complex alpha, Complex beta) throws Exception {
        Helper.QubitInfo qubitInfo = helper.initNewQubit(alpha, beta);
        memory.get(idxCell).setState(qubitInfo);
    }

    /**
    * Объединение кубитов в один квантовый регистр.
    *
    * @param idxCell1 Первый индекс ячейки квантовой памяти.
    * @param idxCell2 Второй индекс ячейки квантовой памяти.
    */
    public void merge(int idxCell1, int idxCell2) throws Exception {
        helper.merge2qubit(
            memory.get(idxCell1).getState(), memory.get(idxCell2).getState());
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

    /** Помошник, обеспечивающий связь с API */
    private Helper helper;

    /** Количество ячеек в квантовой памяти */
    private int maxCellQMem;

    /** Список ячеек квантовой памяти */
    private List<CellQMem> memory;
}