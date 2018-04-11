package javaapplication40;

/**************************************************************************
  * @author Niha Imam
  * CS310 Spring 2018
  * Project 3
  * George Mason University
  * 
  * File Name: Stack.java
  *
  * Description: Generic implementation of a queue (FIFO) 
  ***************************************************************************/

public class Queue<AnyType>{
  
  class Node<AnyType>{
    public AnyType data;
    public Node<AnyType> next;
    
    public Node(AnyType d ){
      this.data = d;
      this.next = null;
    }
    
    public Node(AnyType d, Node<AnyType> n){
      this.data = d;
      this.next = n;
    }
  }
  
  private Node<AnyType> front;
  private Node<AnyType> back;
  int size = 0;
  
  /**
   * queue constructor to initialize the internal attributes
   */
  public Queue(){
    front = back = null;
  }
  
  /**
   * check if queue is empty
   * @returns true if empty, else false
   */
  public boolean isEmpty(){
    return front == null;
  }
  
  /**
   * get/peek the front(least recently inserted item) of the queue
   * @returns the front of the queue, return null if queue is empty
   */
  public AnyType getFront(){
    if (isEmpty())
      return null;
    return front.data;
  }
  
  /**
   * insert a new item to the back of the queue
   * @param value to insert
   */
  public void enqueue(AnyType value){
    if (isEmpty())
      back = front = new Node<AnyType>(value);
    else
      back = back.next = new Node<AnyType>(value);
    size++;
  }
  
  /**
   * remove the element from the front(least recently inserted item) of the queue
   * @returns the element that needs to be removed, return null if queue is empty
   */
  public AnyType dequeue(){
    if (isEmpty())
      return null;
    AnyType returnValue = front.data;
    front = front.next;
    size--;
    return returnValue;
  }
  
  //helper toString method
  public String toString(){
    StringBuilder s = new StringBuilder("Single Linked List with " + size + " items:");
    if (size>0){
      Node<AnyType> current = front;
      int index = 0;
      while (current != null){
        s.append(" [" + current.data + "]-->");
        current = current.next;
        index ++;
        if (index % 8 == 0){
          s.append("\n");
        }
      }
      s.append(" null");
      s.append("\nhead-->[" + front.data + "]\n");
      s.append("tail-->[" + back.data + "]\n"); 
    }
    else{
      if (front == null)
        s.append("\nhead-->null\n");
      if (back == null)
        s.append("tail-->null\n");
    }
    return s.toString();
  }
  
  //----------------------------------------------------
  // example testing code... make sure you pass all ...
  // and edit this as much as you want!
  
  public static void main(String[] args){
    Queue<Integer> iq = new Queue<Integer>();
    if (iq.isEmpty() && iq.getFront()==null){
      System.out.println("Yay 1");
    }
    
    iq.enqueue(new Integer(12));
    iq.enqueue(new Integer(20));
    iq.enqueue(new Integer(-233));
    
    if (iq.dequeue()==12 && iq.getFront()==20 ){
      System.out.println("Yay 2");
    }
    
    if (iq.dequeue()==20 && iq.dequeue()==-233 && iq.isEmpty() ){
      System.out.println("Yay 3");
    }
    
  }
  
}