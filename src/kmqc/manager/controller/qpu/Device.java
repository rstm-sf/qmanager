package kmqc.manager.controller.qpu;

import kpfu.terentyev.quantum.emulator.api.Helper;

public class Device {

    public Device(Placing placing) {
        this.placing = placing;
        state = null;
    }

    public void setState(Helper.QubitInfo state) {
        this.state = state;
    }

    public Helper.QubitInfo getState() {
        return state;
    }

    public Helper.QubitInfo getRidState() {
        Helper.QubitInfo temp = state;
        state = null;
        return temp;
    }

    public Placing getPlacing() {
        return placing;
    }

    private Placing placing;

    private Helper.QubitInfo state;
}