public class Token {
    TokenType type;
    String text;
    int index;

    public Token(TokenType type, String text, int index) {
        this.type = type;
        this.text = text;
        this.index = index;
    }
}