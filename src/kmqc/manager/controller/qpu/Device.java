package kmqc.manager.controller.qpu;

import kpfu.terentyev.quantum.emulator.api.Helper;

/**
* Класс, реализующий работу вывода транзистора.
*
* @author rstm-sf
* @version alpha
*/
public class Device {

    /**
    * Создание вывода транзистора.
    *
    * @param placing Расположение в транзисторе.
    */
    public Device(Placing placing) {
        this.placing = placing;
        state = null;
    }

    /**
    * Переносит состояние кубита в вывод транзистора.
    *
    * @param state Состояние кубита.
    */
    public void setState(Helper.QubitInfo state) {
        this.state = state;
    }

    /**
    * Возвращает состояние кубита из вывода транзистора.
    * Прим.: используется только для транзистора, т.к. состояния не копируются.
    *
    * @return Состояние кубита.
    */
    public Helper.QubitInfo getState() {
        return state;
    }

    /**
    * Возвращает состояние кубита из вывода транзистора, обнуляя состояние ячейки.
    *
    * @return Состояние кубита.
    */
    public Helper.QubitInfo getRidState() {
        Helper.QubitInfo temp = state;
        state = null;
        return temp;
    }

    /**
    * Возвращает положение вывода в транзисторе.
    *
    * @return Положение в транзисторе.
    */
    public Placing getPlacing() {
        return placing;
    }

    private Placing placing;

    private Helper.QubitInfo state;
}