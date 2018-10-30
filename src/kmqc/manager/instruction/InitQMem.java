package kmqc.manager.instruction;

import kpfu.terentyev.quantum.util.ComplexDouble;

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
    public InitQMem(int idx, ComplexDouble alpha, ComplexDouble beta) {
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
    private ComplexDouble alpha;

    /** Амплитуда вероятности состояния &#124;1&gt */
    private ComplexDouble beta;
}