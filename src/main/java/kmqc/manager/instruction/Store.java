package main.java.kmqc.manager.instruction;

import main.java.kmqc.manager.controller.qpu.AddrDevice;

/**
* Наследник класса QInstruction.
* Реализует инструкцию переноса состояния кубита из вывода транзистора в
* ячейку квантовой памяти.
*
* @author rstm-sf
* @version alpha
*/
public class Store extends QInstruction {

    /**
    * Создание инструкции переноса.
    * 
    * @param addr    Адресс вывода транзистора.
    * @param idxQMem Индекс ячейки квантовой памяти.
    */
    public Store(AddrDevice addr, int idxQMem) {
        this.addr = addr;
        this.idxQMem = idxQMem;
    }

    /**
    * @see QInstruction#execute()
    */
    public void execute() {
        qController.store(addr, idxQMem);
    }

    /** Адресс вывода транзистора */
    private AddrDevice addr;

    /** Индекс ячейки квантовой памяти */
    private int idxQMem;
}