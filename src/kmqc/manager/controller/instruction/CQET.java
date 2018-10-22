package kmqc.manager.controller.instruction;

public class CQET extends QInstruction {
    public CQET(int transistorIdx, double theta) {
        this.transistorIdx = transistorIdx;
        this.theta = theta;
    }

    public void execute() {
        QInstruction.emulator.cQET(transistorIdx, theta);
    }

    private int transistorIdx;
    private double theta;
}