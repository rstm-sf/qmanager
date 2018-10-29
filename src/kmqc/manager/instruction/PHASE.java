package kmqc.manager.instruction;

public class PHASE extends QInstruction {

    public PHASE(int idxTransistor, double theta) {
        this.idxTransistor = idxTransistor;
        this.theta = theta;
    }

    public void execute() {
        try {
            qController.opPHASE(idxTransistor, theta);
        } catch (Exception exc) {
            throw new IllegalStateException(exc);
        }
    }

    private int idxTransistor;
    private double theta;
}