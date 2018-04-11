package javaapplication40;

/**************************************************************************
  * @author Niha Imam
  * CS310 Spring 2018
  * Project 3
  * George Mason University
  * 
  * File Name: Stack.java
  *
  * Description: Generic implementation of a stack (FILO) 
  ***************************************************************************/

public class Stack<AnyType>{
  
  private class Node<AnyType>{
    public AnyType data;
    public Node<AnyType> next;
    
    public Node(AnyType d, Node<AnyType> n ){
      this.data = d;
      this.next = n;
    }
  }
  
  private Node<AnyType> head;
  private int size = 0;
  
  /**
   * stack constructor used to initialize internal attributes
   */
  public Stack(){
    this.head = null;
    this.size = 0;
  }
  
  /**
   * check to see if stack is empty
   * @returns true if stack is empty else false
   */
  public boolean isEmpty(){
    return head == null;
  }
  
  /**
   * return the top of the stack
   * @returns the top of stack, if empty return null
   */
  public AnyType peek(){
    if (isEmpty())
      return null;
    return head.data;
  }
  
  /**
   * add/pushes an element on the stack
   */
  public void push(AnyType value){
    Node<AnyType> newnode = new Node<AnyType>(value, head);
    head = newnode;
    size++;
  }
  
  /**
   * remove the the top of the stack
   * @returns the top node that needs to be removed
   */
  public AnyType pop(){
    if (isEmpty()){
      return null;
    }
    AnyType rmval = peek();
    head = head.next;
    return rmval;
  }
  
  //helper to string method
  public String toString(){
    Node<AnyType> curr = head;
    String s = "";
    int index = this.size-1;
    while(curr != null){
      s += index-- + " = " + curr.data + "\n";
      curr = curr.next;
    }
    return s;
  }
  
  //----------------------------------------------------
  // example testing code... make sure you pass all ...
  // and edit this as much as you want!
  
  public static void main(String[] args){
    Stack<Integer> iStack = new Stack<Integer>();
    if (iStack.isEmpty() && iStack.peek()==null){
      System.out.println("Yay 1");
    }
    
    iStack.push(new Integer(12));
    iStack.push(new Integer(20));
    iStack.push(new Integer(-233));
    
    if (iStack.pop()==-233 && iStack.peek()==20 ){
      System.out.println("Yay 2");
    }
    
    if (iStack.pop()==20 && iStack.pop()==12 && iStack.isEmpty() ){
      System.out.println("Yay 3");
    }
    
  }
  
}