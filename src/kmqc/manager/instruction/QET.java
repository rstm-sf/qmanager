package kmqc.manager.instruction;

public class QET extends QInstruction {
    public QET(int transistorIdx, double theta) {
        this.transistorIdx = transistorIdx;
        this.theta = theta;
    }

    public void execute() {
        QInstruction.qController.opQET(transistorIdx, theta);
    }

    private int transistorIdx;
    private double theta;
}