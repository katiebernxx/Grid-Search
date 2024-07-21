/* Katie Bernard
 * 10/24/2022
 */
public class CellQueue{
    private class Node {
        Node next;
        Cell cell;
        Node prev;

        public Node(Cell c){
            next = null;
            prev=null;
            cell = c;
        }
    }

    Node head;
    Node tail;
    int size;

    //constructor
    public CellQueue(){
        head = null;
        tail = null;
        size = 0;
    }

    //Adds a cell to the back of the queue
    public void offer(Cell c){
        Node newNode = new Node(c);

        if(size == 0){
            head = newNode;
            tail=newNode;
            size++;
        } else { 
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
            size++;
        }
    }

    //returns the front of the queue
    public Cell peek(){
        return head.cell;
    }

    //removes and returns the front of the queue
    public Cell poll(){
        
        if(head == null){
            return null;
        }else{ 
            Node pastHead = head;
            head = head.next;
            size--;
            return pastHead.cell;
        }
        
    }

    //returns the size
    public int size(){
        return size;
    }

    //returns if empty
    public boolean empty(){
        if(size == 0){
            return true;
        }
        else{ 
            return false;
        }
    }

public static void main(String[] args){
    //TESTS
    CellQueue queue = new CellQueue();
    
    queue.offer(new Cell(2,3,Cell.Type.FREE));
    queue.offer(new Cell(1,4,Cell.Type.OBSTACLE));
    queue.offer(new Cell(5,1,Cell.Type.FREE));
    System.out.println(queue.poll());
    System.out.println(queue.poll());
    System.out.println(queue.poll());


}  
}
