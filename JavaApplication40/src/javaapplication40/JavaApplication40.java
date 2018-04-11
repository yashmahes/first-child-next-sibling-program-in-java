
package javaapplication40;


public class JavaApplication40 {

  
    public static void main(String[] args) {
        // TODO code application logic here
        
        ExpressionFCNSTree obj =  new ExpressionFCNSTree();
        
        System.out.println("Size of tree is " + obj.size()); 
        
        FCNSTreeNode node = new FCNSTreeNode("qwerty");
        ExpressionFCNSTree obj1 =  new ExpressionFCNSTree(node);
        
        System.out.println("Size of tree1 is " + obj1.size()); 
        
       System.out.println("Height of tree1 is " + obj1.height());
       
       System.out.println("Nan in tree1 is " + obj1.countNan());
        
    }
    
}
