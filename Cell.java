/* Katie Bernard
 * 10/24/2022
 */
import java.awt.Graphics;
import java.awt.Color;

public class Cell{
    public enum Type {
        FREE, OBSTACLE, START, TARGET
    }   
    boolean visited;
    Cell prev; //which cell we came from to get there
    int row, col;
    Type type;  
    
    public Cell (int row, int col, Type theType){ //constructor
        prev = null;
        visited = false;
        this.row = row;
        this.col = col;
        type = theType;
    }

    //GETTERS AND SETTERS

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public Type getType(){
        return type;
    }

    public boolean getVisited(){
        return visited;
    }

    public Cell getPrev(){
        return prev;
    }

    //sets the previous cell
    public void visitFrom(Cell c){
        visited = true;
        prev = c;
    }

    //resets the cells visited and previous cell status
    public void reset(){
        visited = false;
        prev = null;
    }

    //string output
    public String toString(){
        return(""+ row + ", " + col + ": " + type);
    }

    //Visualization of the cell
    public void draw(Graphics g, int scale, Landscape scape) {
        g.setColor(Color.BLACK);
        g.drawRect(getCol() * scale, getRow() * scale, scale, scale);
        switch (getType()) {
            case FREE:
                g.setColor(getVisited() ? Color.YELLOW : Color.GRAY);
                break;
            case OBSTACLE:
                g.setColor(Color.BLACK);
                break;
            case START:
                g.setColor(Color.BLUE);
                break;
            case TARGET:
                g.setColor(Color.RED);
                break;
        }
        g.fillRect(getCol() * scale + 2, getRow() * scale + 2, scale - 4, scale - 3);
    
        g.setColor(Color.RED);
        if (getPrev() != null && getPrev() != this) {
            int midX = ((getCol() + getPrev().getCol()) * scale + scale) / 2;
            int midY = ((getRow() + getPrev().getRow()) * scale + scale) / 2;
            g.drawLine(getCol() * scale + scale / 2, getRow() * scale + scale / 2,
                    midX, midY);
        }
        for (Cell neighbor : scape.getNeighbors(this)) {
            if (neighbor.getPrev() == this) {
                int midX = ((getCol() + neighbor.getCol()) * scale + scale) / 2;
                int midY = ((getRow() + neighbor.getRow()) * scale + scale) / 2;
                g.drawLine(getCol() * scale + scale / 2, getRow() * scale + scale / 2,
                        midX, midY);
            }
        }
    }     
    
    public static void main(String[] args){
        //tests
        Cell c = new Cell(3,4,Cell.Type.FREE);
        System.out.println(c.getCol());
        System.out.println(c.getRow());
        System.out.println(c.getType());
        System.out.println(c.getVisited());
    }
}