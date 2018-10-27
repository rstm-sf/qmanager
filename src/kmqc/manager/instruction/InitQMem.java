package kmqc.manager.instruction;

import kpfu.terentyev.quantum.util.ComplexDouble;

public class InitQMem extends QInstruction {

    public InitQMem(int idx, ComplexDouble alpha, ComplexDouble beta) {
        this(idx, alpha, beta);
    }

    public void execute() {
        qController.init(idx, alpha, beta);
    }

    private int idx;
    private ComplexDouble alpha;
    private ComplexDouble beta;
}