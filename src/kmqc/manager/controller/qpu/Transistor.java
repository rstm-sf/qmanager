package kmqc.manager.controller.qpu;

public class Transistor {
    public Transistor(
        int idx,
        QRegAddr qRegAddrL,
        QRegAddr qRegAddrC,
        QRegAddr qRegAddrR) {
        this.idx = idx;
        this.qRegAddrL = qRegAddrL;
        this.qRegAddrC = qRegAddrC;
        this.qRegAddrR = qRegAddrR;
    }

    public int idx;
    public QRegAddr qRegAddrL;
    public QRegAddr qRegAddrC;
    public QRegAddr qRegAddrR;
}