package kmqc.manager.instruction;

public class CQET extends QInstruction {

    public CQET(int idxTransistor, double theta) {
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