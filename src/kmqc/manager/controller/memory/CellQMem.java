package kmqc.manager.controller.memory;

import kpfu.terentyev.quantum.emulator.api.QManager;

public class CellQMem {

    public CellQMem(QManager helper) {
        this.helper = helper;
        state = null;
    }

    public void setState(QManager.QubitInfo state) {
        this.state = state;
    }

    public QManager.QubitInfo getRidState() {
        QManager.QubitInfo temp = state;
        state = null;
        return temp;
    }

    public int measure() throws Exception {
        int result = helper.measure(state);
        state = null;
        return result;
    }

    private QManager helper;

    private QManager.QubitInfo state;
}