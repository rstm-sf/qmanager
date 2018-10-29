package kmqc.manager.instruction;

/**
* Наследник класса QInstruction.
* Реализует инструкцию базовой операции CQET.
*
* @author rstm-sf
* @version alpha
*/
public class CQET extends QInstruction {

    /**
    * Создание инструкции CQET.
    * 
    * @param idxTransistor Индекс транзистора.
    * @param theta         Угол поворота.
    */
    public CQET(int idxTransistor, double theta) {
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

    private int idxTransistor;
    private double theta;
}