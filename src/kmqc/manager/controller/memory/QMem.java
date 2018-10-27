package kmqc.manager.controller.memory;

import java.util.ArrayList;

import kpfu.terentyev.quantum.emulator.api.QManager;
import kpfu.terentyev.quantum.util.ComplexDouble;

public class QMem {

    public QMem(QManager helper, int maxCellQMem) {
        this.helper = helper;
        this.maxCellQMem = maxCellQMem;
        memory = new ArrayList<CellQMem>(maxCellQMem);
        for (int i = 0; i < maxCellQMem; i++) {
            memory.add(new CellQMem());
        }
    }

    public getMaxCountOfQubits() {
        return maxCellQMem;
    }

    public void setState(int idxCell, QManager.QubitInfo qubitInfo) {
        checkIdx(idxCell);
        memory[idxCell].setState(qubitInfo);
    }

    public QManager.Qubit getRidState(int idxCell) {
        checkIdx(idxCell);
        return memory[idxCell].getRidState();
    }

    public void initState(
        int idxCell, ComplexDouble alpha, ComplexDouble beta) {
        checkIdx(idxCell);
        memory[idxCell].setState(helper.initNewQubit(alpha, beta));
    }

    public int measureState(int idxCell) {
        checkIdx(idxCell);
        return memory[idxCell].measure();
    }

    private void checkIdx(int idx) {
        if (idx < 0 || idx >= maxCellQMem) {
            throw new Exception("Address is out of available range");
        }
    }

    private QManager helper;

    private int maxCellQMem;
    private ArrayList<CellQMem> memory;
}