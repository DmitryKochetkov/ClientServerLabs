package parser;

public abstract class Node {

    static class NumberNode extends Node {
        Token number;

        public NumberNode(Token number) {
            this.number = number;
        }
    }

    static class VarNode extends Node {
        Token id;

        public VarNode(Token id) {
            this.id = id;
        }
    }

    static class BinOpNode extends Node { //Binary Operation parser.Node
        Token op;
        Node left;
        Node right;

        public BinOpNode(Token op, Node left, Node right) {
            this.op = op;
            this.left = left;
            this.right = right;
        }
    }
}
