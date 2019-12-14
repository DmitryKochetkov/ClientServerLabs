import parser.Node;
import parser.Parser;

public class Calculator implements Result {

    private double Result;

    public Calculator(String s) {
        Parser parser = new Parser(s);
        Result = parser.get_eval();
    }

    @Override
    public double getResult() { return Result; }
}
