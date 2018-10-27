package kmqc.manager.controller.qpu;

public class AddrDevice {

    public AddrDevice(int idxTransistor, Placing placing) {
        this.idxTransistor = idxTransistor;
        this.placing = placing;
    }

    public int idxTransistor;
    public Placing placing;
}