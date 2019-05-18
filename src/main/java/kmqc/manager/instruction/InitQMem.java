package main.java.kmqc.manager.instruction;

import main.java.kmqc.simulator.util.Complex;

/**
* Наследник класса QInstruction.
* Реализует инструкцию инициализации кубита в ячейке памяти.
*
* @author rstm-sf
* @version alpha
*/
public class InitQMem extends QInstruction {

    /**
    * Создание инструкции инициализации.
    * 
    * @param idx   Индекс ячейки классической памяти.
    * @param alpha Амплитуда вероятности состояния &#124;0&gt;.
    * @param beta  Амплитуда вероятности состояния &#124;1&gt;.
    */
    public InitQMem(int idx, Complex alpha, Complex beta) {
        this.idx = idx;
        this.alpha = alpha;
        this.beta = beta;
    }

    /**
    * @see QInstruction#execute()
    */
    public void execute() {
        try {
            qController.init(idx, alpha, beta);
        } catch (Exception exc) {
            throw new IllegalStateException(exc);
        }
    }

    /** Индекс ячейки квантовой памяти */
    private int idx;

    /** Амплитуда вероятности состояния &#124;0&gt */
    private Complex alpha;

    /** Амплитуда вероятности состояния &#124;1&gt */
    private Complex beta;
}