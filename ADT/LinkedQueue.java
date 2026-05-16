/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ADT;

/**
 *
 * @author USER
 */

class Node {
    int data;
    Node next;
    Node(int data) {this.data = data;}
}
public class LinkedQueue implements QueueADT{
    private Node head;
    private Node tail;
    private int count = 0;
    
    @Override
    public void enqueue(int element) {
        Node newNode = new Node(element);
        if(isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        count++;
    }
    
    @Override
    public int dequeue() {
        int val = head.data;
        if(isEmpty()) {
            throw new RuntimeException("Empty");
        } else {
            val = head.data;
            head = head.next;
        }
        if (head == null) tail = null;
        count--;    
        return val;
    }
    
    @Override
    public boolean isEmpty() {return count == 0;}
    
    @Override
    public int size() {return count;}
    
}
