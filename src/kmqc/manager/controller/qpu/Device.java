package kmqc.manager.controller.qpu;

import kpfu.terentyev.quantum.emulator.api.QManager;

public class Device {

    public Device(Placing placing) {
        this.placing = placing;
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

    public getPlacing() {
        return placing;
    }

    private Placing placing;

    private QManager.QubitInfo state;
}