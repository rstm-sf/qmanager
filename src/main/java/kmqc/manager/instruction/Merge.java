package main.java.kmqc.manager.instruction;

/**
* Наследник класса QInstruction.
* Реализует инструкцию объединения 2 кубитов в один квантовый регистр.
*
* @author rstm-sf
* @version alpha
*/

public class Merge extends QInstruction {
    
    /**
    * Создание инструкции объединения.
    * 
    * @param idxQMem1 Первый индекс ячейки квантовой памяти.
    * @param idxQMem2 Второй индекс ячейки классической памяти.
    */
    public Merge(int idxQMem1, int idxQMem2) {
        this.idxQMem1 = idxQMem1;
        this.idxQMem2 = idxQMem2;
    }

    /**
    * @see QInstruction#execute()
    */
    public void execute() {
        try {
            qController.merge(idxQMem1, idxQMem2);
        } catch (Exception exc) {
            throw new IllegalStateException(exc);
        }
    }

    /** Первый индекс ячейки квантовой памяти */
    private int idxQMem1;

    /** Второй индекс ячейки квантовой памяти */
    private int idxQMem2;
}