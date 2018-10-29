package kmqc.manager.controller.memory;

import java.util.ArrayList;
import java.util.List;

/**
* Класс, реализующий работу классической памяти.
*
* @author rstm-sf
* @version alpha
*/
public class CMem {
    
    /**
    * Создает ячейки классической памяти для хранения значения битов.
    *
    * @param maxCellCMem Количество ячеек в памяти.
    */
    public CMem(int maxCellCMem) {
        this.maxCellCMem = maxCellCMem;
        memory = new ArrayList<Integer>(maxCellCMem);
        for (int i = 0; i < maxCellCMem; i++) {
            memory.add(Integer.valueOf(0));
        }
    }

    /**
    * Сохраняет значения бита.
    *
    * @param idxCell Индекс ячейки.
    * @param state   Значение бита.
    */
    public void setState(int idxCell, Integer state) {
        memory.set(idxCell, state);
    }

    /**
    * Возвращение значения бита.
    *
    * @param idxCell Индекс ячейки.
    *
    * @return Значение бита.
    */
    public Integer getState(int idxCell) {
        return memory.get(idxCell);
    }

    int maxCellCMem;
    List<Integer> memory; 
}