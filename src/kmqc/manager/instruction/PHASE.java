package kmqc.manager.instruction;

public class PHASE extends QInstruction {

    public PHASE(int idxTransistor, double theta) {
        this.idxTransistor = idxTransistor;
        this.theta = theta;
    }

    public void execute() {
        qController.opPHASE(idxTransistor, theta);
    }

    private int idxTransistor;
    private double theta;
}