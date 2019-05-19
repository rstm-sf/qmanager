package kmqc.manager.instruction;

import kmqc.manager.controller.qpu.AddrDevice;

/**
* Наследник класса QInstruction.
* Реализует инструкцию переноса состояния кубита из ячейки квантовой памяти в
* вывод транзистора.
*
* @author rstm-sf
* @version alpha
*/
public class Load extends QInstruction {

    /**
    * Создание инструкции переноса.
    * 
    * @param idxQMem Индекс ячейки квантовой памяти.
    * @param addr    Адресс вывода транзистора.
    */
    public Load(int idxQMem, AddrDevice addr) {
        this.idxQMem = idxQMem;
        this.addr = addr;
    }

    /**
    * @see QInstruction#execute()
    */
    public void execute() {
        qController.load(idxQMem, addr);
    }

    /** Индекс ячейки квантовой памяти */
    private int idxQMem;

    /** Адресс вывода транзистора */
    private AddrDevice addr;
}