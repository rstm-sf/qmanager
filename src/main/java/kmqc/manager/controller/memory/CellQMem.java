package main.java.kmqc.manager.controller.memory;

import main.java.kmqc.simulator.api.Helper;

/**
* Класс, реализующий работу ячейку квантовой памяти.
*
* @author rstm-sf
* @version alpha
*/
public class CellQMem {

    /**
    * Создание ячейки памяти. 
    *
    * @param helper Обеспечивает связь с API.
    */
    public CellQMem(Helper helper) {
        this.helper = helper;
        state = null;
    }

    /**
    * Переносит состояние кубита в ячейку памяти.
    *
    * @param state Состояние кубита.
    */
    public void setState(Helper.QubitInfo state) {
        this.state = state;
    }

    /**
    * Возвращает состояние кубита из ячейки памяти, обнуляя состояние ячейки.
    *
    * @return Состояние кубита.
    */
    public Helper.QubitInfo getRidState() {
        Helper.QubitInfo temp = state;
        state = null;
        return temp;
    }

    /**
    * Возвращает состояние кубита из ячейки памяти, обнуляя состояние ячейки.
    * Прим.: используется только для памяти, т.к. состояния не копируются.
    *
    * @return Состояние кубита.
    */
    public Helper.QubitInfo getState() {
        return state;
    }

    /**
    * Измеряет состояние кубита из ячейки памяти, обнуляя состояние ячейки.
    *
    * @return Измеренное состояние кубита.
    */
    public int measure() throws Exception {
        int result = helper.measure(state);
        state = null;
        return result;
    }

    /** Помошник, обеспечивающий связь с API */
    private Helper helper;

    /** Состояние кубита */
    private Helper.QubitInfo state;
}