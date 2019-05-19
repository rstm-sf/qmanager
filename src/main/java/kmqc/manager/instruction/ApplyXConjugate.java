package kmqc.manager.instruction;

/**
* Наследник класса QInstruction.
* Реализует инструкцию базовой операции ApplyXConjugate.
*
* @author rstm-sf
* @version alpha
*/
public class ApplyXConjugate extends QInstruction {

    /**
    * Создание инструкции ApplyXConjugate.
    *
    * @param idx_qreg
    * @param i
    * @param x
    * @param y
    */
    public ApplyXConjugate(long idx_qreg, long i, double x, double y) {
        this.idx_qreg = idx_qreg;
        this.i = i;
        this.x = x;
        this.y = y;
    }

    /**
    * @see QInstruction#execute()
    */
    public void execute() {
        qSim.applyXconjugate(idx_qreg, i, x, y);
    }

    private long idx_qreg;

    private long i;

    private double x;

    private double y;
}
