package kmqc.manager.controller.qpu;

import kpfu.terentyev.quantum.emulator.api.Helper;

/**
* Класс, реализующий работу транзистора.
*
* @author rstm-sf
* @version alpha
*/
public class Transistor {

    /**
    * Создание транзистора. 
    *
    * @param helper Обеспечивает связь с API.
    */
    public Transistor(Helper helper) {
        this.helper = helper;
        this.devL = new Device(Placing.Left);
        this.devC = new Device(Placing.Center);
        this.devR = new Device(Placing.Right);
    }

    /**
    * Переносит состояние кубита в "левый" вывод транзистора.
    *
    * @param qubitInfo Состояние кубита.
    */
    public void setLeftState(Helper.QubitInfo qubitInfo) {
        devL.setState(qubitInfo);
    }

    /**
    * Переносит состояние кубита в "центральный" вывод транзистора.
    *
    * @param qubitInfo Состояние кубита.
    */
    public void setCenterState(Helper.QubitInfo qubitInfo) {
        devC.setState(qubitInfo);
    }

    /**
    * Переносит состояние кубита в "правый" вывод транзистора.
    *
    * @param qubitInfo Состояние кубита.
    */
    public void setRightState(Helper.QubitInfo qubitInfo) {
        devR.setState(qubitInfo);
    }

    /**
    * Возвращает состояние кубита из "левого" вывода транзистора,
    * обнуляя состояние ячейки.
    *
    * @return Состояние кубита.
    */
    public Helper.QubitInfo getRidLeftState() {
        return devL.getRidState();
    }

    /**
    * Возвращает состояние кубита из "центрального" вывода транзистора,
    * обнуляя состояние ячейки.
    *
    * @return Состояние кубита.
    */
    public Helper.QubitInfo getRidCenterState() {
        return devC.getRidState();
    }

    /**
    * Возвращает состояние кубита из "правого" вывода транзистора,
    * обнуляя состояние ячейки.
    *
    * @return Состояние кубита.
    */
    public Helper.QubitInfo getRidRightState() {
        return devR.getRidState();
    }

    /**
    * Включение режима QET, действующий на "левый" и "правый" вывод.
    *
    * @param theta Угол поворота.
    */
    public void opQET(double theta) throws Exception {
        helper.opQET(devL.getState(), devR.getState(), theta);
    }

    /**
    * Включение режима PHASE, действующий на "левый" и "правый" вывод.
    *
    * @param theta Угол поворота.
    */
    public void opPHASE(double theta) throws Exception {
        helper.opPHASE(devL.getState(), devR.getState(), theta);
    }

    /**
    * Включение режима CQET, действующий на все выводы.
    * "Центральный" вывод является контролирующим, представленный состоянием
    * первого физического кубита из логического кубита.
    *
    * @param theta Угол поворота.
    */
    public void opCQET(double theta) throws Exception {
        helper.opCQET(devL.getState(), devC.getState(), devR.getState(), theta);
    }

    /** Помошник, обеспечивающий связь с API */
    private Helper helper;

    /** "Левый" вывод */
    private Device devL;

    /** "Центральный" вывод */
    private Device devC;

    /** "Правый" вывод */
    private Device devR;
}