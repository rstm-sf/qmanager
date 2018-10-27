package kmqc.manager.controller.memory;

import java.util.ArrayList;
import java.util.List;

public class CMem {
    
    public CMem(int maxCellCMem) {
        this.maxCellCMem = maxCellCMem;
        memory = new ArrayList<Integer>(maxCellCMem);
        for (int i = 0; i < maxCellCMem; i++) {
            memory.add(Integer.valueOf(0));
        }
    }

    public void setState(int idxCell, Integer state) {
        memory.set(idxCell, state);
    }

    public Integer getState(int idxCell) {
        return memory.get(idxCell);
    }

    int maxCellCMem;
    List<Integer> memory; 
}