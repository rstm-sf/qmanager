package kmqc.manager.controller.memory;

import java.util.ArrayList;

import kpfu.terentyev.quantum.emulator.api.QManager;
import kpfu.terentyev.quantum.util.ComplexDouble;

public class QMem {

    public QMem(QManager helper, int maxCountOfQubits) {
        this.helper = helper;
        this.maxCountOfQubits = maxCountOfQubits;
        memory = new ArrayList<CellMem>(maxCountOfQubits);
        for (int i = 0; i < maxCountOfQubits; i++) {
            memory.add(new CellMem());
        }
    }

    public getMaxCountOfQubits() {
        return maxCountOfQubits;
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
        return helper.measure(memory[idxCell].getRidState());
    }

    private void checkIdx(int idx) {
        if (idx < 0 || idx >= maxCountOfQubits) {
            throw new Exception("Address is out of available range");
        }
    }

    private QManager helper;

    private int maxCountOfQubits;
    private ArrayList<CellMem> memory;
}