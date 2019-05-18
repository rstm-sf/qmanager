package main.java.kmqc.manager.instruction;

import main.java.kmqc.manager.controller.QController;

/**
* Абстрактный класс для создания инструкций, выполняемых с помощью контроллера,
* содержащий в себе статический объект класса QController и 
* статический метод получения значения бита.
*
* @author rstm-sf
* @version alpha
*/
public abstract class QInstruction {

    /**
    * Метод исполнения инструкции.
    */
    public abstract void execute();

    protected static QController qController = new QController();

    /**
    * Получение значение бита в указанной ячейке классической памяти.
    * @param idx Индекс ячейки.
    * @return Значение бита.
    */
    public static Integer getIdxCMem(int idx) {
        return qController.getIdxCMem(idx);
    }
}