package kmqc.manager.instruction;

public class Measure extends QInstruction {

    public Measure(int idxQMem) {
        this.idxQMem = idxQMem;
    }

    public void execute() {
        qController.measure(idxQMem);
    }

    private int idxQMem;
}