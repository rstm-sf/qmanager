package kmqc.manager.instruction;

public class PHASE extends QInstruction {
    public PHASE(int transistorIdx, double theta) {
        this.transistorIdx = transistorIdx;
        this.theta = theta;
    }

    public void execute() {
        QInstruction.qController.opPHASE(transistorIdx, theta);
    }

    private int transistorIdx;
    private double theta;
}