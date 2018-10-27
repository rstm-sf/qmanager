package kmqc.manager.controller.memory;

import java.util.ArrayList;

public class CMem {
    
    public CMem(int maxCellCMem) {
        this.maxCellCMem = maxCellCMem;
        memory = new ArrayList<Integer>(maxCellCMem);
        for (int i = 0; i < maxCellCMem; i++) {
            memory.add(new Integer.valueOf());
        }
    }

    public void setState(int idxCell, Integer state) {
        checkIdx(idxCell);
        memory[idxCell] = state;
    }

    public Integer getState(int idxCell) {
        checkIdx(idxCell);
        return memory[idxCell];
    }

    private void checkIdx(int idx) {
        if (idx < 0 || idx >= maxCellCMem) {
            throw new Exception("Address is out of available range");
        }
    }

    int maxCellCMem;

    ArrayList<Integer> memory; 
}