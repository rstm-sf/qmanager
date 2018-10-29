package kmqc.manager.instruction;

/**
* Наследник класса QInstruction.
* Реализует инструкцию инициализации классического бита в ячейке памяти.
*
* @author rstm-sf
* @version alpha
*/
public class InitCMem extends QInstruction {

    /**
    * Создание инструкции инициализации.
    * 
    * @param idx   Индекс ячейки классической памяти.
    * @param state Значение бита.
    */
    public InitCMem(int idx, Integer state) {
        this.idx = idx;
        this.state = state;
    }

   /**
    * @see QInstruction#execute()
    */
    public void execute() {
        qController.init(idx, state);
    }

    private int idx;
    private Integer state;
}