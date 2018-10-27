package kmqc.manager.instruction;

public class Measure extends QInstruction {

    public Measure(int idxQMem, int idxCMem) {
        this.idxQMem = idxQMem;
        this.idxCMem = idxCMem;
    }

    public void execute() {
        try {
            qController.measure(idxQMem, idxCMem);
        } catch (Exception exc) {
            throw new IllegalStateException(exc);
        }
    }

    private int idxQMem;
    private int idxCMem;
}