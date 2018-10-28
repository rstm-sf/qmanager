package kmqc.manager.controller.memory;

import kpfu.terentyev.quantum.emulator.api.Helper;

public class CellQMem {

    public CellQMem(Helper helper) {
        this.helper = helper;
        state = null;
    }

    public void setState(Helper.QubitInfo state) {
        this.state = state;
    }

    public Helper.QubitInfo getRidState() {
        Helper.QubitInfo temp = state;
        state = null;
        return temp;
    }

    public int measure() throws Exception {
        int result = helper.measure(state);
        state = null;
        return result;
    }

    private Helper helper;

    private Helper.QubitInfo state;
}