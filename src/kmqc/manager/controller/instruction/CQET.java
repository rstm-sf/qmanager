package kmqc.manager.controller.instruction;

public class CQET extends QInstruction {
    public CQET(double theta) {
        this.theta = theta;
    }

    @Override
    public void Excecute() {
        QInstruction.emulator.cQET(transistorIdx, theta);
    }

    private double theta;
    private final int transistorIdx = 1;
}