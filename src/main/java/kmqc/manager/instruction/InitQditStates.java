package kmqc.manager.instruction;

import kmqc.manager.controller.QController;

/**
* Наследник класса QInstruction.
* Реализует инструкцию базовой операции InitQditStates.
*
* @author rstm-sf
* @version alpha
*/
public class InitQditStates extends QInstruction {

    /**
    * Создание инструкции InitQditStates.
    *
    * @param idx_qreg
    * @param i
    */
    public InitQditStates(long nreg, long dim) {
        this.nreg = nreg;
        this.dim = dim;
    }

    /**
    * @see QInstruction#execute()
    */
    public void execute() {
        qController = new QController(nreg, dim);
    }

    private long nreg;

    private long dim;
}
