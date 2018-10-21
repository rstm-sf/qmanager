package kmqc.manager.controller.instruction;

public class QET extends QInstruction {
    public QET(double theta) {
        this.theta = theta;
    }

    @Override
    public void Excecute() {
        QInstruction.emulator.QET(transistorIdx, theta);
    }

    private double theta;
    private final int transistorIdx = 1;
}