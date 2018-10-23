package kmqc.manager.controller.qpu;

import kpfu.terentyev.quantum.KazanModel.ProcessingAddress;
import kpfu.terentyev.quantum.KazanModel.ProcessingUnitCellAddress;

public class QRegAddr extends ProcessingAddress {
    public QRegAddr(
        int transistorIdx, TransistorCellAdrr transistorCellAddr) {
        super(transistorIdx, toProcessingUnitCellAddress(transistorCellAddr));
    }

    private static ProcessingUnitCellAddress toProcessingUnitCellAddress(
        TransistorCellAdrr addr) {
        switch (addr) {
        case Left:
            return ProcessingUnitCellAddress.Cell0;
        case Center:
            return ProcessingUnitCellAddress.ControlPoint;
        case Right:
            return ProcessingUnitCellAddress.Cell1;
        }
        return null;
    }
}