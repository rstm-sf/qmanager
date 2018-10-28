package kmqc.manager.controller.memory;

import java.util.ArrayList;
import java.util.List;

import kpfu.terentyev.quantum.emulator.api.Helper;
import kpfu.terentyev.quantum.util.ComplexDouble;

public class QMem {

    public QMem(Helper helper, int maxCellQMem) {
        this.helper = helper;
        this.maxCellQMem = maxCellQMem;
        memory = new ArrayList<CellQMem>(maxCellQMem);
        for (int i = 0; i < maxCellQMem; i++) {
            memory.add(new CellQMem(this.helper));
        }
    }

    public int getMaxCountOfQubits() {
        return maxCellQMem;
    }

    public void setState(int idxCell, Helper.QubitInfo qubitInfo) {
        memory.get(idxCell).setState(qubitInfo);
    }

    public Helper.QubitInfo getRidState(int idxCell) {
        return memory.get(idxCell).getRidState();
    }

    public void initState(
        int idxCell, ComplexDouble alpha, ComplexDouble beta) throws Exception {
        Helper.QubitInfo qubitInfo = helper.initNewQubit(alpha, beta);
        memory.get(idxCell).setState(qubitInfo);
    }

    public int measureState(int idxCell) throws Exception {
        return memory.get(idxCell).measure();
    }

    private Helper helper;

    private int maxCellQMem;
    private List<CellQMem> memory;
}