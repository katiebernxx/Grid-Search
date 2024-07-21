/* Katie Bernard
 * 10/24/2022
 */
public class CellStack{

    //INNER NODE CLASS
    private class Node{
        Cell cell;
        Node next;

        public Node(Cell cell){
            this.cell=cell;
            this.next = null;
        }
    }

    private Node head; //this should always be the top of the stack
    private int size;

    public CellStack(){ //Constructor
        head = null;
        size = 0;
    }

    //STACK METHODS

    public void push(Cell c){ //Add given cell to top of stack (head)
        Node newNode = new Node(c);
        newNode.next = head;
        head = newNode;
        size++;
    }

    public Cell peek(){ //return top of stack (head)
        return head.cell;
    }

    public Cell pop(){ //remove and return top of stack(head)
        if(size > 0){
            Node toRemove = head;
            head = head.next;
            size --;
            return toRemove.cell;
        }
        else{
            return null; //returns null if stack is empty
        }
    }

    public int size(){ //returns size of stack
        return size;
    }

    public boolean empty(){ //returns true if the stack is empty
        return size == 0;
    }
    
    public static void main(String[] args){
        CellStack s = new CellStack();
        s.push(new Cell(2,4,Cell.Type.FREE));
        s.push(new Cell(3,1,Cell.Type.FREE));
        s.push(new Cell(5,0,Cell.Type.OBSTACLE));
        System.out.println(s.peek());
        System.out.println(s.pop());
        System.out.println(s.pop());
        System.out.println(s.size());

    }
}
