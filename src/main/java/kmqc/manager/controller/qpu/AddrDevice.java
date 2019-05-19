package kmqc.manager.controller.qpu;

/**
* Структура, содержащая расположение в процессорном устройстве вывода транзистора.
*
* @author rstm-sf
* @version alpha
*/
public class AddrDevice {

    /**
    * Создание струтуры для хранения расположение вывода транзистора. 
    *
    * @param idxTransistor Индекс транзистора в процессорном устройстве.
    * @param placing       Расположение в транзисторе.
    */
    public AddrDevice(int idxTransistor, Placing placing) {
        this.idxTransistor = idxTransistor;
        this.placing = placing;
    }

    /** Индекс транзистора */
    public int idxTransistor;

    /** Раположения вывода в транзисторе */
    public Placing placing;
}