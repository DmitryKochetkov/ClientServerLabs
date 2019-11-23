import java.util.List;

public class Calculator implements Result {

    enum Operations {
        ADD, SUB, MUL, DIV
    }

    private double Result;

    public Calculator(String s) {
        Parser parser = new Parser(s);
        Node node = parser.parse2();
        Result = parser.eval2(node);
    }

    @Override
    public double getResult() { return Result; }
}
