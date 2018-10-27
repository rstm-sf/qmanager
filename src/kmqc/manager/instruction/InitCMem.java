package kmqc.manager.instruction;

public class InitCMem extends QInstruction {

    public InitCMem(int idx, Integer state) {
        this.idx = idx;
        this.state = state;
    }

    public void execute() {
        qController.init(idx, state);
    }

    private int idx;
    private Integer state;
}