package main.java.kmqc.manager.instruction;

/**
* Наследник класса QInstruction.
* Реализует инструкцию измерения состояния кубита.
*
* @author rstm-sf
* @version alpha
*/
public class Measure extends QInstruction {

    /**
    * Создание инструкции измерения.
    * 
    * @param idxQMem Индекс ячейки квантовой памяти.
    * @param idxCMem Индекс ячейки классической памяти.
    */
    public Measure(int idxQMem, int idxCMem) {
        this.idxQMem = idxQMem;
        this.idxCMem = idxCMem;
    }

    /**
    * @see QInstruction#execute()
    */
    public void execute() {
        try {
            qController.measure(idxQMem, idxCMem);
        } catch (Exception exc) {
            throw new IllegalStateException(exc);
        }
    }

    /** Индекс ячейки квантовой памяти */
    private int idxQMem;

    /** Индекс ячейки классической памяти */
    private int idxCMem;
}