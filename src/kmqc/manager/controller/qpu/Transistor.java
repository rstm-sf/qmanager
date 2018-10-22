package kmqc.manager.controller.qpu;

public class Transistor {
    public Transistor(
        int idx,
        QRegAddr qRegAddr0,
        QRegAddr qRegAddrC,
        QRegAddr qRegAddr1) {
        this.idx = idx;
        this.qRegAddr0 = qRegAddr0;
        this.qRegAddrC = qRegAddrC;
        this.qRegAddr1 = qRegAddr1;
    }

    public int idx;
    public QRegAddr qRegAddr0;
    public QRegAddr qRegAddrC;
    public QRegAddr qRegAddr1;
}