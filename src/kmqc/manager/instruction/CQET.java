package kmqc.manager.instruction;

public class CQET extends QInstruction {

    public CQET(int idxTransistor, double theta) {
        this.idxTransistor = idxTransistor;
        this.theta = theta;
    }

    public void execute() {
        qController.opQET(idxTransistor, theta);
    }

    private int idxTransistor;
    private double theta;
}