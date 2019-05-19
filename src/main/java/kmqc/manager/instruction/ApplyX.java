package kmqc.manager.instruction;

/**
* Наследник класса QInstruction.
* Реализует инструкцию базовой операции ApplyX.
*
* @author rstm-sf
* @version alpha
*/
public class ApplyX extends QInstruction {

    /**
    * Создание инструкции ApplyX.
    *
    * @param idx_qreg
    * @param i
    * @param x
    * @param y
    */
    public ApplyX(long idx_qreg, long i, double x, double y) {
        this.idx_qreg = idx_qreg;
        this.i = i;
        this.x = x;
        this.y = y;
    }

    /**
    * @see QInstruction#execute()
    */
    public void execute() {
        qController.opX(idx_qreg, i, x, y, false);
    }

    private long idx_qreg;

    private long i;

    private double x;

    private double y;
}
