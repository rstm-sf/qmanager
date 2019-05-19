package kmqc.manager.instruction;

/**
* Наследник класса QInstruction.
* Реализует инструкцию базовой операции ApplyZConjugate.
*
* @author rstm-sf
* @version alpha
*/
public class ApplyZConjugate extends QInstruction {

    /**
    * Создание инструкции ApplyZConjugate.
    *
    * @param idx_qreg
    * @param i
    * @param tau
    */
    public ApplyZConjugate(long idx_qreg, long i, double tau) {
        this.idx_qreg = idx_qreg;
        this.i = i;
        this.tau = tau;
    }

    /**
    * @see QInstruction#execute()
    */
    public void execute() {
        qSim.applyZconjugate(idx_qreg, i, tau);
    }

    private long idx_qreg;

    private long i;

    private double tau;
}
