package kmqc.manager.controller.qpu;

import kpfu.terentyev.quantum.KazanModel.ProcessingAddress;
import kpfu.terentyev.quantum.KazanModel.ProcessingUnitCellAddress;

public class QRegAddr extends ProcessingAddress {
    public QRegAddr(
        int transistorIdx, ProcessingUnitCellAddress transistorCellAddress) {
        super(transistorIdx, transistorCellAddress);
    }
}