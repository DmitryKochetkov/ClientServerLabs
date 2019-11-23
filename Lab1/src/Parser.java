import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser { //грамматический анализатор
    List<Token> tokens;
    int pos = 0;

    public Parser(String text) {
        Lexer lexer = new Lexer(text);
        this.tokens = lexer.lex();
    }

    void error(String message) {
        if (pos < tokens.size()) {
            Token t = tokens.get(pos);
            throw new RuntimeException(message + " at " + pos);
        }
        else
            throw new RuntimeException(message + " at end");
    }

    Token match(TokenType expected) {
        if (pos < tokens.size()) {
            Token t = tokens.get(pos);
            if (t.type == expected) { pos++; return t;}
        }
        return null;
    }

    void run2() { //run2 и require используют метод рекурсивного спуска, а не конечные автоматы
        require(TokenType.NUMBER);
        while (pos < tokens.size()) { //while is equivalent to Kleene star
            require(TokenType.ADD); //S_FINAL to S_LOOP
            require(TokenType.NUMBER); //S_LOOP to S_FINAL
        }
    }

    Node elem() {
        Token n = match(TokenType.NUMBER);
        if (n != null) return new Node.NumberNode(n);
        error("Number expected!");
        return null;
    }

    Node parse() {
        Node e1 = elem();
        while (pos < tokens.size()) {
            Token op = require(TokenType.ADD);
            Node e2 = elem();
            e1 = new Node.BinOpNode(op, e1, e2);
        }
        return e1;
    }

    Node parse2() {
        Node e1 = elem();
        while (pos < tokens.size()) {
            Token op = require(TokenType.ADD, TokenType.SUB, TokenType.MUL, TokenType.DIV);
            Node e2 = elem();
            e1 = new Node.BinOpNode(op, e1, e2);
        }
        return e1;
    }

    int eval(Node n) {
        if (n instanceof Node.NumberNode) {
            Node.NumberNode nn = (Node.NumberNode) n;
            return Integer.parseInt(nn.number.text);
        }
        else if (n instanceof Node.BinOpNode) {
            Node.BinOpNode bn = (Node.BinOpNode) n;
            int l = eval(bn.left);
            int r = eval(bn.right);
            return l+r;
        }
        else if (n instanceof Node.VarNode) {
            Node.VarNode vn = (Node.VarNode) n;
            System.out.println("Evaluation for " + vn.id.text + " required: ");
            Scanner s = new Scanner(System.in);
            return Integer.parseInt(s.nextLine());
        }
        return 0;
    }

    int eval2(Node n) {
        if (n instanceof Node.NumberNode) {
            Node.NumberNode nn = (Node.NumberNode) n;
            return Integer.parseInt(nn.number.text);
        }
        else if (n instanceof Node.BinOpNode) {
            Node.BinOpNode bn = (Node.BinOpNode) n;
            int l = eval(bn.left);
            int r = eval(bn.right);
            if (bn.op.type == TokenType.ADD) return l+r;
            else if (bn.op.type == TokenType.SUB) return l-r;
            else if (bn.op.type == TokenType.MUL) return l*r;
            else if (bn.op.type == TokenType.DIV) return l/r;
        }
        else if (n instanceof Node.VarNode) {
            Node.VarNode vn = (Node.VarNode) n;
            System.out.println("Evaluation for " + vn.id.text + " required: ");
            Scanner s = new Scanner(System.in);
            return Integer.parseInt(s.nextLine());
        }
        return 0;
    }

    Token require(TokenType type) {
        Token t = match(type);
        if (t == null)
            error("Error, position " + pos + ". Token " + type + " expected!");
        return t;
    }

    Token require(TokenType type1, TokenType type2) {
        Token t1 = match(type1);
        Token t2 = match(type2);
        if (t1 == null && t2 == null)
            error("Error, position " + pos + ". Unexpected token");
        if (t1 == null)
            return t2;
        else return t1;
    }

    Token require(TokenType ... types) {
        for (TokenType t: types)
        {
            Token token = match(t);
            if (token != null)
                return token;
        }
        error("Error, position " + pos + ". Unexpected token");
        return null;
    }
}