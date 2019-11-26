import java.util.regex.Pattern;

public enum TokenType {
    NUMBER("[0-9]+"),
    ADD("\\+"),
    SUB("-"),
    MUL("\\*"),
    DIV("/");
    Pattern pattern;

    TokenType(String regexp) { pattern = Pattern.compile(regexp); }
}
