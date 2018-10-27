package kmqc.manager.instruction;

public class QET extends QInstruction {

    public QET(int idxTransistor, double theta) {
        this.idxTransistor = idxTransistor;
        this.theta = theta;
    }

    public void execute() {
        try {
            qController.opQET(idxTransistor, theta);
        } catch (Exception exc) {
            throw new IllegalStateException(exc);
        }
    }

    private int idxTransistor;
    private double theta;
}