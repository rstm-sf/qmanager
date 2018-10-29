package kmqc.manager.instruction;

/**
* Наследник класса QInstruction.
* Реализует инструкцию базовой операции PHASE.
*
* @author rstm-sf
* @version alpha
*/
public class PHASE extends QInstruction {

    /**
    * Создание инструкции PHASE.
    * 
    * @param idxTransistor Индекс транзистора.
    * @param theta         Угол поворота.
    */
    public PHASE(int idxTransistor, double theta) {
        this.idxTransistor = idxTransistor;
        this.theta = theta;
    }

    /**
    * @see QInstruction#execute()
    */
    public void execute() {
        try {
            qController.opPHASE(idxTransistor, theta);
        } catch (Exception exc) {
            throw new IllegalStateException(exc);
        }
    }

    private int idxTransistor;
    private double theta;
}