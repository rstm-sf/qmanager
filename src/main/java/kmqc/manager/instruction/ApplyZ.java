package kmqc.manager.instruction;

/**
* Наследник класса QInstruction.
* Реализует инструкцию базовой операции ApplyZ.
*
* @author rstm-sf
* @version alpha
*/
public class ApplyZ extends QInstruction {

    /**
    * Создание инструкции ApplyZ.
    *
    * @param idx_qreg
    * @param i
    * @param tau
    */
    public ApplyZ(long idx_qreg, long i, double tau) {
        this.idx_qreg = idx_qreg;
        this.i = i;
        this.tau = tau;
    }

    /**
    * @see QInstruction#execute()
    */
    public void execute() {
        qController.opZ(idx_qreg, i, tau, false);
    }

    private long idx_qreg;

    private long i;

    private double tau;
}
