package kmqc.manager;

import java.util.ArrayList;
import java.util.List;

import kmqc.manager.controller.qpu.AddrDevice;
import kmqc.manager.controller.qpu.Placing;
import kmqc.manager.controller.QController;
import kmqc.manager.instruction.*;

import kpfu.terentyev.quantum.util.Complex;
import kpfu.terentyev.quantum.util.ComplexDouble;

/**
* Главный класс для проверки наработок.
*
* @author rstm-sf
* @version alpha
*/
public class Main {

    public static void main(String[] args) throws Exception {
        testDeutsch();
        System.out.print("\n");
        testCNOT();
    }

    /**
    * Проверка на алгоритме Дойча.
    */
    public static void testDeutsch() {
        System.out.print("Start Deutsch algorithm\n");

        int idxCMem = 0;
        LogicalQubit q0 = new LogicalQubit(0, 1);
        LogicalQubit q1 = new LogicalQubit(2, 3);
        int idxTransistor = 0;

        List<QInstruction> instructions = new ArrayList<>();
        instructions.addAll(initCBit(idxCMem));
        instructions.addAll(initLogicalQubit(q0));
        instructions.addAll(initLogicalQubit(q1));
        instructions.addAll(gateX(idxTransistor, q1));
        instructions.addAll(gateH(idxTransistor, q0));
        instructions.addAll(gateH(idxTransistor, q1));
        instructions.addAll(gateCNOT(idxTransistor, q1, q0));
        instructions.addAll(gateH(idxTransistor, q0));
        instructions.addAll(measure(q0, idxCMem));

        for (QInstruction instruction : instructions) {
            instruction.execute();
        }

        System.out.print(
            "q0: " + QInstruction.getIdxCMem(idxCMem).toString() + "\n");
        System.out.print("End algorithm\n");
    }

    public static void testCNOT() {
        System.out.print("Start CNOT Test\n");
        System.out.print("q0 │ q1 | res\n");
        testCNOT_0();
        testCNOT_1();
        testCNOT_2();
        testCNOT_3();
        System.out.print("End Test\n");
    }

    public static void testCNOT_0() {
        System.out.print(" 0 │  0 │  ");

        int idxCMem = 0;
        LogicalQubit q0 = new LogicalQubit(0, 1);
        LogicalQubit q1 = new LogicalQubit(2, 3);
        int idxTransistor = 0;

        List<QInstruction> instructions = new ArrayList<>();
        instructions.addAll(initCBit(idxCMem));
        instructions.addAll(initLogicalQubit(q0));
        instructions.addAll(initLogicalQubit(q1));
        instructions.addAll(gateCNOT(idxTransistor, q0, q1));
        instructions.addAll(measure(q1, idxCMem));

        for (QInstruction instruction : instructions) {
            instruction.execute();
        }

        System.out.print(QInstruction.getIdxCMem(idxCMem).toString() + "\n");
    }

    public static void testCNOT_1() {
        System.out.print(" 1 │  0 │  ");

        int idxCMem = 0;
        LogicalQubit q0 = new LogicalQubit(0, 1);
        LogicalQubit q1 = new LogicalQubit(2, 3);
        int idxTransistor = 0;

        List<QInstruction> instructions = new ArrayList<>();
        instructions.addAll(initCBit(idxCMem));
        instructions.addAll(initLogicalQubit(q0));
        instructions.addAll(initLogicalQubit(q1));
        instructions.addAll(gateX(idxTransistor, q0));
        instructions.addAll(gateCNOT(idxTransistor, q0, q1));
        instructions.addAll(measure(q1, idxCMem));

        for (QInstruction instruction : instructions) {
            instruction.execute();
        }

        System.out.print(QInstruction.getIdxCMem(idxCMem).toString() + "\n");
    }

    public static void testCNOT_2() {
        System.out.print(" 0 │  1 │  ");

        int idxCMem = 0;
        LogicalQubit q0 = new LogicalQubit(0, 1);
        LogicalQubit q1 = new LogicalQubit(2, 3);
        int idxTransistor = 0;

        List<QInstruction> instructions = new ArrayList<>();
        instructions.addAll(initCBit(idxCMem));
        instructions.addAll(initLogicalQubit(q0));
        instructions.addAll(initLogicalQubit(q1));
        instructions.addAll(gateX(idxTransistor, q1));
        instructions.addAll(gateCNOT(idxTransistor, q0, q1));
        instructions.addAll(measure(q1, idxCMem));

        for (QInstruction instruction : instructions) {
            instruction.execute();
        }

        System.out.print(QInstruction.getIdxCMem(idxCMem).toString() + "\n");
    }

    public static void testCNOT_3() {
        System.out.print(" 1 │  1 │  ");

        int idxCMem = 0;
        LogicalQubit q0 = new LogicalQubit(0, 1);
        LogicalQubit q1 = new LogicalQubit(2, 3);
        int idxTransistor = 0;

        List<QInstruction> instructions = new ArrayList<>();
        instructions.addAll(initCBit(idxCMem));
        instructions.addAll(initLogicalQubit(q0));
        instructions.addAll(initLogicalQubit(q1));
        instructions.addAll(gateX(idxTransistor, q0));
        instructions.addAll(gateX(idxTransistor, q1));
        instructions.addAll(gateCNOT(idxTransistor, q0, q1));
        instructions.addAll(measure(q1, idxCMem));

        for (QInstruction instruction : instructions) {
            instruction.execute();
        }

        System.out.print(QInstruction.getIdxCMem(idxCMem).toString() + "\n");
    }

    /**
    * Структура для хранения физических адресов кубитов из логического.
    */
    private static class LogicalQubit {
        public int idxFirst;
        public int idxSecond;

        /**
        * Создание логического кубита.
        *
        * @param idxFirst  Адресс первого физического кубита. 
        * @param idxSecond Адресс второго физического кубита. 
        */
        public LogicalQubit(int idxFirst, int idxSecond) {
            this.idxFirst = idxFirst;
            this.idxSecond = idxSecond;
        }
    }

    /**
    * Метод, возвращающий список инициализации классического бита.
    *
    * @param idxCMem Индекс ячейки классической памяти.
    * @return Список инструкций.
    */
    private static List<QInstruction> initCBit(int idxCMem) {
        List<QInstruction> instr = new ArrayList<>(List.of(
            new InitCMem(idxCMem, 0)
        ));
        return instr;
    }

    /**
    * Метод, возвращающий список измерения состояния логического кубита в 
    * в классический бит.
    *
    * @param q       Логический кубит.
    * @param idxCMem Индекс ячейки классической памяти.
    * @return Список инструкций.
    */
    private static List<QInstruction> measure(LogicalQubit q, int idxCMem) {
        List<QInstruction> instr = new ArrayList<>(List.of(
            new Measure(q.idxFirst, idxCMem)
        ));
        return instr;
    }

    /**
    * Метод, возвращающий список инициализации логического кубита.
    *
    * @param q Логический кубит.
    * @return Список инструкций.
    */
    private static List<QInstruction> initLogicalQubit(LogicalQubit q) {
        List<QInstruction> instr = new ArrayList<>(List.of(
            new InitQMem(q.idxFirst, Complex.zero(), Complex.zero()),
            new InitQMem(q.idxSecond, Complex.unit(), Complex.zero())
        ));
        return instr;
    }

    /**
    * Метод, возвращающий список для выполнения оператора U2 из (arXiv:1707.03429).
    * $U_2\left(\varphi,\mu\right) &= R_z\left(\varphi+\frac{\pi}{2}\right)R_x\left(\frac{\pi}{2}\right)R_z\left(\mu-\frac{\pi}{2}\right)$
    *
    * @param idxTransistor Индекс транзистора.
    * @param phi           Для угла поворота вокруг оси Z.
    * @param mu            Для угла поворота вокруг оси Z.
    * @param q             Логический кубит на который производится действие.
    * @return Список инструкций.
    */
    private static List<QInstruction> opU2(
        int idxTransistor, double phi, double mu, LogicalQubit q) {
        AddrDevice addrL = new AddrDevice(idxTransistor, Placing.Left);
        AddrDevice addrR = new AddrDevice(idxTransistor, Placing.Right);
        List<QInstruction> instr = new ArrayList<>(List.of(
            new Load(q.idxFirst, addrL),
            new Load(q.idxSecond, addrR),
            new PHASE(idxTransistor, phi + Math.PI / 2.0),
            new QET(  idxTransistor, -Math.PI / 2.0),
            new PHASE(idxTransistor, mu - Math.PI / 2.0),
            new Store(addrL, q.idxFirst),
            new Store(addrR, q.idxSecond)
        ));
        return instr;
    }

    /**
    * Метод, возвращающий список для выполнения оператора U3 из (arXiv:1707.03429).
    * $U_3\left(\theta,\varphi,\mu\right) &= R_z\left(\varphi+3\pi\right)R_x\left(\pi\right)R_z\left(\theta+\pi\right)R_x\left(\pi\right)R_z\left(\mu\right)$
    *
    * @param idxTransistor Индекс транзистора.
    * @param theta         Для угла поворота вокруг оси Z.
    * @param phi           Для угла поворота вокруг оси Z.
    * @param mu            Для угла поворота вокруг оси Z.
    * @param q             Логический кубит на который производится действие.
    * @return Список инструкций.
    */
    private static List<QInstruction> opU3(
        int idxTransistor,
        double theta, double phi, double mu,
        LogicalQubit q) {
        AddrDevice addrL = new AddrDevice(idxTransistor, Placing.Left);
        AddrDevice addrR = new AddrDevice(idxTransistor, Placing.Right);
        List<QInstruction> instr = new ArrayList<>(List.of(
            new Load(q.idxFirst, addrL),
            new Load(q.idxSecond, addrR),
            new PHASE(idxTransistor, phi + 3 * Math.PI),
            new QET(  idxTransistor, -Math.PI / 2.0),
            new PHASE(idxTransistor, theta + Math.PI),
            new QET(  idxTransistor, -Math.PI / 2.0),
            new PHASE(idxTransistor, mu),
            new Store(addrL, q.idxFirst),
            new Store(addrR, q.idxSecond)
        ));
        return instr;
    }

    /**
    * Метод, возвращающий список для выполнения гейта CNOT.
    *
    * @param idxTransistor Индекс транзистора.
    * @param qA            "Контролирующий" логический кубит.
    * @param qC            Логический кубит на который производится действие.
    * @return Список инструкций.
    */
    private static List<QInstruction> gateCNOT(
        int idxTransistor, LogicalQubit qA, LogicalQubit qC) {
        AddrDevice addrL = new AddrDevice(idxTransistor, Placing.Left);
        AddrDevice addrC = new AddrDevice(idxTransistor, Placing.Center);
        AddrDevice addrR = new AddrDevice(idxTransistor, Placing.Right);
        List<QInstruction> instr = new ArrayList<>(List.of(
            new Load(qC.idxFirst, addrL),
            new Load(qC.idxSecond, addrR),
            new Load(qA.idxSecond, addrC),
            new CQET( idxTransistor, Math.PI),
            new PHASE(idxTransistor, Math.PI / 2.0),
            new Store(addrL, qC.idxFirst),
            new Store(addrR, qC.idxSecond),
            new Store(addrC, qA.idxSecond)
        ));
        return instr;
    }

    /**
    * Метод, возвращающий список для выполнения гейта X.
    *
    * @param idxTransistor Индекс транзистора.
    * @param q             Логический кубит на который производится действие.
    * @return Список инструкций.
    */
    private static List<QInstruction> gateX(int idxTransistor, LogicalQubit q) {
        List<QInstruction> instr = new ArrayList<>();
        instr.addAll(opU3(idxTransistor, Math.PI, 0.0, Math.PI, q));
        return instr;
    }

    /**
    * Метод, возвращающий список для выполнения гейта H.
    *
    * @param idxTransistor Индекс транзистора.
    * @param q             Логический кубит на который производится действие.
    * @return Список инструкций.
    */
    private static List<QInstruction> gateH(int idxTransistor, LogicalQubit q) {
        List<QInstruction> instr = new ArrayList<>();
        instr.addAll(opU2(idxTransistor, 0.0, Math.PI, q));
        return instr;
    }
}