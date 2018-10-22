package kmqc.manager.controller.instruction;

public class QET extends QInstruction {
    public QET(int transistorIdx, double theta) {
        this.transistorIdx = transistorIdx;
        this.theta = theta;
    }

    public void execute() {
        QInstruction.emulator.QET(transistorIdx, theta);
    }

    private int transistorIdx;
    private double theta;
}