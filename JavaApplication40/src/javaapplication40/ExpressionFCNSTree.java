package javaapplication40;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Integer.max;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExpressionFCNSTree {

    FCNSTreeNode root;

    public ExpressionFCNSTree() {
        root = null;
    }

    public ExpressionFCNSTree(FCNSTreeNode root) {
        this.root = root;
    }

    public boolean equals(ExpressionFCNSTree another) {
        if (root == null) {
            return false;
        }
        return root.equals(another.root);

    }

    public int size() {
        return calculateSize(root);
    }

    private int calculateSize(FCNSTreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + calculateSize(node.firstChild) + calculateSize(node.nextSibling);
    }

    //start here
    // return the height of the tree
    // return -1 for null tree
    public int height() {
        if (root == null) {
            return -1;
        }

        return calculateHeight(root);
    }

    private int calculateHeight(FCNSTreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + max(calculateHeight(node.firstChild), calculateHeight(node.nextSibling));
    }

    public int countNode(String s) {
        // count how many nodes in the tree are with the specified symbol s
        return calculateCountNode(root, s);
    }

    private int calculateCountNode(FCNSTreeNode node, String s) {
        if (node == null) {
            return 0;
        }

        if (node.element.equals("s")) {
            return 1 + calculateCountNode(node.firstChild, s) + calculateCountNode(node.nextSibling, s);
        } else {
            return 0 + calculateCountNode(node.firstChild, s) + calculateCountNode(node.nextSibling, s);
        }
    }

    public int countNan() {
        // count and return how many nodes are marked as not-a-number
        return calculateCountNan(root);
    }

    private int calculateCountNan(FCNSTreeNode node) {
        if (node == null) {
            return 0;
        }

        if (node.nan) {
            return 1 + calculateCountNan(node.firstChild) + calculateCountNan(node.nextSibling);
        } else {
            return 0 + calculateCountNan(node.firstChild) + calculateCountNan(node.nextSibling);
        }
    }

    public String toStringPreFix() {
        // return a string representation of pre-order tree traversal
        // there should be exactly one single space after each tree node 
        // see main method below for examples
        return printPreorder(root);
    }

    String printPreorder(FCNSTreeNode node) {
        String order = "";
        if (node == null) {
            return order;
        }

        /* first print data of node */
        order += node.element + " ";

        /* then recur on left sutree */
        printPreorder(node.firstChild);

        /* now recur on right subtree */
        printPreorder(node.nextSibling);

        return order;
    }

    public String toStringPostFix() {
        // return a string representation of post-order tree traversal
        // there should be exactly one single space after each tree node 
        // see main method below for examples
        return printPostfixorder(root);

    }

    String printPostfixorder(FCNSTreeNode node) {
        String order = "";
        if (node == null) {
            return order;
        }

        // recur on left sutree 
        printPostfixorder(node.firstChild);

        /* now recur on right subtree */
        printPostfixorder(node.nextSibling);

        order += node.element + " ";

        return order;
    }

    public String toStringLevelOrder() {
        // return a string representation of level-order (breadth-first) tree traversal
        // there should be exactly one single space after each tree node 
        // see main method below for examples

        return traverseTree(root);
    }

    String traverseTree(FCNSTreeNode rot) {
        String order = "";
        if (rot == null) {
            return order;
        }

        while (rot != null) {
            order += " " + rot.element;
            if (rot.firstChild != null) {
                order += traverseTree(rot.firstChild);
            }
            rot = rot.nextSibling;
        }

        return order;
    }

    public void buildTree(String fileName) throws FileNotFoundException {
        // This method need to open file specified by the string fileName, 
        // read in a one-line numeric expression in prefix notation, and 
        // construct a first-child-next-sibling expression tree base on the input

        // set root to be the newly constructed tree root 
        // if there is any exception, root should be null
        List<String> records = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                records.add(line);

            }
            reader.close();

            String[] arr = records.get(0).split(" ");

            FCNSTreeNode n1 = new FCNSTreeNode(arr[arr.length - 1]);
            FCNSTreeNode n2 = new FCNSTreeNode(arr[arr.length - 2], null, n1);

            for (int i = arr.length - 3; i >= 0; i--) {
                n1 = new FCNSTreeNode(arr[i], n2, null);
                if (i == 0) {
                    root = n1;
                    break;
                }

                i--;

                n2 = new FCNSTreeNode(arr[i], null, n1);
                if (i == 0) {
                    root = n2;
                    break;
                }

            }

        } catch (IOException e) {
            System.err.format("Exception occurred trying to read '%s'.", fileName);
            root = null;

        }

        // prefix: * 1 + 2 3 (expr1.txt)
    }

    public ExpressionBinaryTree buildBinaryTree() {
        // construct the binary tree representation of this expression 
        // and return the tree root
        BinaryTreeNode rot = buildBinary(root);

        ExpressionBinaryTree treee = new ExpressionBinaryTree(rot);
        return treee;
    }

    public BinaryTreeNode buildBinary(FCNSTreeNode root1) {
        BinaryTreeNode rot = new BinaryTreeNode(root1.element);
        if (root1.firstChild == null) {
            return rot;
        }

        BinaryTreeNode left = new BinaryTreeNode(root1.firstChild.element);
        FCNSTreeNode nextSibling = root1.firstChild.nextSibling;
        BinaryTreeNode right = new BinaryTreeNode(nextSibling.element);
        rot.left = buildBinary(root1.firstChild);
        rot.right = buildBinary(nextSibling);

        return rot;

    }

    public String toStringPrettyInFix() {
        // return a string representation as the normal human-friendly infix expression
        // it is like we are simulating in-order tree traversal of the binary tree
        // Format of the infix string: 
        //  - there should be no space, but  
        //  - parenthesizes must be inserted wrapping around the sub-expression of each operator
        // see main method below for examples

        String representation = toStringLevelOrder();
        String[] arr = representation.split(" ");

        String repr = "";
        for (int i = 0; i < arr.length; i++) {
            repr += "(" + arr[i] + ")";

        }

        return repr;
    }

    public Integer evaluate() {
        // This method evaluates the expression and marks every tree node with:
        //    - operand node: node.value should be the integer value of the operand
        //    - operator node: node.value should be the integer value associated with
        //                     the sub-expression rooted at the node 
        // Return: the integer value of the root node 

        // return null for null tree
        // if there is a division by zero: keep node.value to be null and set node.nan to be true.
        // not-a-number should be propagated: for an operator, if any of its operand is not-a-number,
        //   then the node of this operator should also be marked as not-a-number
        if (root == null) {
            return null;
        }

        return getValue(root);
    }

    public Integer getValue(FCNSTreeNode node) {
        if (node.element.equals("+")) {
            node.value = getValue(node.firstChild) + getValue(node.firstChild.nextSibling);
            node.nan = false;
        } else if (node.element.equals("-")) {
            node.value = getValue(node.firstChild) - getValue(node.firstChild.nextSibling);
            node.nan = false;
        } else if (node.element.equals("*")) {
            node.nan = false;
            node.value = getValue(node.firstChild) * getValue(node.firstChild.nextSibling);
        } else if (node.element.equals("/")) {
            if (getValue(node.firstChild.nextSibling) == 0) {
                node.nan = true;
                node.value = null;
            } else {
                node.nan = false;
                node.value = getValue(node.firstChild) / getValue(node.firstChild.nextSibling);
            }
        } else if (node.element.equals("%")) {
            node.nan = false;
            node.value = getValue(node.firstChild) % getValue(node.firstChild.nextSibling);
        } else {
            node.value = Integer.parseInt(node.element);

        }

        return node.value;

    }

    public Integer evaluateNonRec() {
        // This method evaluates the expression leaving the tree unchanged 
        // You must implement it as a non-recursive method
        // Return: the integer value of answer

        // return null for null tree
        // For this method only, assume there are no division-by-zero issues in the input
        if (root == null) {
            return null;
        }

        return root.value;
    }

    //----------------------------------------------------
    // example testing code... make sure you pass all ...
    // and edit this as much as you want!
    public static void main(String[] args) throws FileNotFoundException {

        //     *     *
        //   /  \       /
        //  /    \                1
        //  1     +   ==>    \
        //       / \                +
        //      2   3      /
        //                        2
        //         \
        //                          3
        //
        // prefix: * 1 + 2 3 (expr1.txt)
        FCNSTreeNode n1 = new FCNSTreeNode("3");
        FCNSTreeNode n2 = new FCNSTreeNode("2", null, n1);
        FCNSTreeNode n3 = new FCNSTreeNode("+", n2, null);
        FCNSTreeNode n4 = new FCNSTreeNode("1", null, n3);
        FCNSTreeNode n5 = new FCNSTreeNode("*", n4, null);
        ExpressionFCNSTree etree = new ExpressionFCNSTree(n5);

        if (etree.size() == 5 && etree.height() == 4 && etree.countNan() == 0 && etree.countNode("+") == 1) {
            System.out.println("Yay 1");
        }

        if (etree.toStringPreFix().equals("* 1 + 2 3 ") && etree.toStringPrettyInFix().equals("(1*(2+3))")) {
            System.out.println("Yay 2");

        }

        if (etree.toStringPostFix().equals("3 2 + 1 * ") && etree.toStringLevelOrder().equals("* 1 + 2 3 ")) {
            System.out.println("Yay 3");

        }

        if (etree.evaluateNonRec() == 5) {
            System.out.println("Yay 4");
        }

        if (etree.evaluate() == 5 && n4.value == 1 && n3.value == 5 && !n5.nan) {
            System.out.println("Yay 5");

        }

        ExpressionFCNSTree etree2 = new ExpressionFCNSTree();
        etree2.buildTree("expressions/expr1.txt"); // construct expression tree from pre-fix notation

        if (etree2.equals(etree)) {
            System.out.println("Yay 6");
        }

        BinaryTreeNode bn1 = new BinaryTreeNode("1");
        BinaryTreeNode bn2 = new BinaryTreeNode("2");
        BinaryTreeNode bn3 = new BinaryTreeNode("3");
        BinaryTreeNode bn4 = new BinaryTreeNode("+", bn2, bn3);
        BinaryTreeNode bn5 = new BinaryTreeNode("*", bn1, bn4);
        ExpressionBinaryTree btree = new ExpressionBinaryTree(bn5);

        //construct binary tree from first-child-next-sibling tree
        ExpressionBinaryTree btree2 = etree.buildBinaryTree();
        if (btree2.equals(btree)) {
            System.out.println("Yay 7");
        }

        ExpressionFCNSTree etree3 = new ExpressionFCNSTree();
        etree3.buildTree("expressions/expr5.txt"); // an example of an expression with division-by-zero
        if (etree3.evaluate() == null && etree3.countNan() == 1) {
            System.out.println("Yay 8");

        }

    }
}

//=======================================
// Tree node class implemented
class FCNSTreeNode {

    //members
    String element; //symbol represented by the node, can be either operator or operand (integer)
    Boolean nan; //boolean flag, set to be true if the expression is not-a-number
    Integer value;  //integer value associated with the node, used in evaluation
    FCNSTreeNode firstChild;
    FCNSTreeNode nextSibling;

    //constructors
    public FCNSTreeNode(String el) {
        element = el;
        nan = false;
        value = null;
        firstChild = null;
        nextSibling = null;
    }

    //constructors
    public FCNSTreeNode(String el, FCNSTreeNode fc, FCNSTreeNode ns) {
        element = el;
        nan = false;
        value = null;
        firstChild = fc;
        nextSibling = ns;
    }

    // toString
    @Override
    public String toString() {
        return element.toString();
    }

    // compare two nodes 
    // return true if: 1) they have the same element; and
    //                 2) their have matching firstChild (subtree) and nextSibling (subtree)
    public boolean equals(FCNSTreeNode another) {
        if (another == null) {
            return false;
        }

        if (!this.element.equals(another.element)) {
            return false;
        }

        if (this.firstChild == null) {
            if (another.firstChild != null) {
                return false;
            }
        } else if (!this.firstChild.equals(another.firstChild)) {
            return false;
        }

        if (this.nextSibling == null) {
            if (another.nextSibling != null) {
                return false;
            }
        } else if (!this.nextSibling.equals(another.nextSibling)) {
            return false;
        }

        return true;

    }

}
