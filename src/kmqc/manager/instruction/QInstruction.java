package kmqc.manager.instruction;

import java.util.ArrayList;

import kmqc.manager.controller.QController;

public abstract class QInstruction {
    protected static QController qController = new QController();
    protected static ArrayList<Integer> cResults = new ArrayList<>();

    public ArrayList<Integer> getCResults() {
        return cResults;
    }

    public QInstruction() {}

    public abstract void execute();
}