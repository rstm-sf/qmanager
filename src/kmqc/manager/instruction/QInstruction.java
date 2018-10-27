package kmqc.manager.instruction;

import kmqc.manager.controller.QController;

public abstract class QInstruction {

    public abstract void execute();

    protected static QController qController = new QController();

    public static Integer getIdxCMem(int idx) {
        return qController.getIdxCMem(idx);
    }
}