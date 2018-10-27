package kmqc.manager.instruction;

import kmqc.manager.controller.qpu.AddrDevice;

public class Load extends QInstruction {

    public Load(int idxQMem, AddrDevice addr) {
        this.idxQMem = idxQMem;
        this.addr = addr;
    }

    public void execute() {
        qController.load(idxQMem, addr);
    }

    private int idxQMem;
    private AddrDevice addr;
}