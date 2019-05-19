package kmqc.manager.instruction;

import kmqc.manager.controller.QController;
import kmqc.qsimulator.QsimulatorLib;

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
    
    protected static QsimulatorLib.Simulator qSim = new QsimulatorLib.Simulator(1, 2);

    /**
    * Получение значение бита в указанной ячейке классической памяти.
    * @param idx Индекс ячейки.
    * @return Значение бита.
    */
    public static Integer getIdxCMem(int idx) {
        return qController.getIdxCMem(idx);
    }
}