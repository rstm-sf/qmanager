package main.java.kmqc.manager.instruction;

/**
* Наследник класса QInstruction.
* Реализует инструкцию базовой операции QET.
*
* @author rstm-sf
* @version alpha
*/
public class QET extends QInstruction {

    /**
    * Создание инструкции QET.
    * 
    * @param idxTransistor Индекс транзистора.
    * @param theta         Угол поворота.
    */
    public QET(int idxTransistor, double theta) {
        this.idxTransistor = idxTransistor;
        this.theta = theta;
    }

    /**
    * @see QInstruction#execute()
    */
    public void execute() {
        try {
            qController.opQET(idxTransistor, theta);
        } catch (Exception exc) {
            throw new IllegalStateException(exc);
        }
    }

    /** Индекс транзистора в процессорном устройстве */
    private int idxTransistor;

    /** Угол поворота */
    private double theta;
}