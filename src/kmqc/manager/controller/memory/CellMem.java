package kmqc.manager.controller.memory;

import kpfu.terentyev.quantum.emulator.api.QManager;

public class CellMem {

    public CellMem() {
        state = null;
    }

    public void setState(QManager.QubitInfo state) {
        this.state = state;
    }

    public QManager.QubitInfo getState() {
        return state;
    }

    public QManager.QubitInfo getRidState() {
        QManager.QubitInfo temp = state;
        state = null;
        return temp;
    }

    private QManager.QubitInfo state;
}