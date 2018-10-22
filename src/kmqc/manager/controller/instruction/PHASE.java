package kmqc.manager.controller.instruction;

public class PHASE extends QInstruction {
    public PHASE(int transistorIdx, double theta) {
        this.transistorIdx = transistorIdx;
        this.theta = theta;
    }

    public void execute() {
        QInstruction.emulator.PHASE(transistorIdx, theta);
    }

    private int transistorIdx;
    private double theta;
}