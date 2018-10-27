package kmqc.manager.instruction;

import kpfu.terentyev.quantum.util.ComplexDouble;

public class InitQMem extends QInstruction {

    public InitQMem(int idx, ComplexDouble alpha, ComplexDouble beta) {
        this.idx = idx;
        this.alpha = alpha;
        this.beta = beta;
    }

    public void execute() {
        try {
            qController.init(idx, alpha, beta);
        } catch (Exception exc) {
            throw new IllegalStateException(exc);
        }
    }

    private int idx;
    private ComplexDouble alpha;
    private ComplexDouble beta;
}