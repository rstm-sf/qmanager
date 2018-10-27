package kmqc.manager.instruction;

import kmqc.manager.controller.qpu.AddrDevice;

public class Store extends QInstruction {

    public Store(AddrDevice addr, int idxQMem) {
        this.addr = addr;
        this.idxQMem = idxQMem;
    }

    public void execute() {
        qController.store(addr, idxQMem);
    }

    private AddrDevice addr;
    private int idxQMem;
}