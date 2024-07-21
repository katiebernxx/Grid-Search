/* Katie Bernard
 * 10/24/2022
 */
import java.util.Random;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Color;

public class Landscape{

    int rows;
    int cols;
    double chance;
    Cell[][] grid;
    Cell start;
    Cell target;
    int greens;
    
    public Landscape(int numRows, int numCols, double odds){ //constructor
        rows = numRows;
        cols = numCols;
        chance = odds;
        Random rand = new Random();
        grid = new Cell[rows][cols];

        //Fill in the board with obstacles and free cells
        for(int r = 0; r < rows; r++){
            for (int c = 0; c < cols; c++){

                boolean isObstacle = rand.nextDouble(1)<chance;
                if(isObstacle){
                    Cell newCell = new Cell(r, c, Cell.Type.OBSTACLE);
                    grid[r][c] = newCell;

                }
                else{
                    Cell newCell = new Cell(r, c, Cell.Type.FREE);
                    grid[r][c] = newCell;
                }
            }
        }

        //Assign start and target to random locations on the board
        int startRow = rand.nextInt(rows);
        int startCol = rand.nextInt(cols);
        int targetRow = rand.nextInt(rows);
        int targetCol = rand.nextInt(cols);

        start = new Cell(startRow, startCol, Cell.Type.START);
        grid[startRow][startCol] = start;

        target = new Cell(targetRow, targetCol, Cell.Type.TARGET);
        grid[targetRow][targetCol] = target;
    }

    //Getters and Setters

    public Cell getStart(){
        return start;
    }

    public Cell getTarget(){
        return target;
    }

    public int getRows(){
        return rows;
    }

    public int getCols(){
        return cols;
    }

    public Cell getCell(int row, int col){
        return grid[row][col];
    }

    //Resets the board's previous and visited 
    public void reset(){
        for(int r = 0; r < rows; r++){
            for (int c = 0; c < cols; c++){
                grid[r][c].prev = null;
                grid[r][c].visited = false;
            }
        }
    }

    
    //outputs a list of the cell's neighbors (not diagonal)
    public ArrayList<Cell> getNeighbors(Cell c){
        ArrayList<Cell> neighbors = new ArrayList<Cell>();
        int row = c.getRow();
        int col = c.getCol();

        //all the adjacent neighbors of the cell
        //Add to neighbors, filtering out obstacles and prev visited
        if(row!=rows-1){ 
            Cell above = grid[row+1][col];
            if(above.getType() != Cell.Type.OBSTACLE && above.visited == false){
                neighbors.add(above);
            }
        }

        if(row!=0){ 
            Cell below = grid[row-1][col];
            if(below.getType() != Cell.Type.OBSTACLE && below.visited == false){
                neighbors.add(below);
            }
        }

        if(col!=0){ 
            Cell left = grid[row][col-1];
            if(left.getType() != Cell.Type.OBSTACLE && left.visited == false){
                neighbors.add(left);
            }
        }

        if(col!=cols-1){ 
            Cell right = grid[row][col+1];
            if(right.getType() != Cell.Type.OBSTACLE && right.visited == false){
                neighbors.add(right);
            }
        }

        return neighbors;
       }

       //visualization of the landscape
       public void draw(Graphics g, int scale) {
        for(int r = 0; r < getRows(); r++){
            for(int c = 0; c < getCols(); c++){
                getCell(r, c).draw(g, scale, this);
            }
        }
        g.setColor(Color.RED);
        CellQueue queue = new CellQueue();
        queue.offer(start);
        while (!(queue.size() == 0)) {
            Cell cur = queue.poll();
    
            for (Cell neighbor : getNeighbors(cur)) {
                if (neighbor.getPrev() == cur) {
                    queue.offer(neighbor);
                    g.drawLine(cur.getCol() * scale + scale / 2, cur.getRow() * scale + scale / 2,
                            neighbor.getCol() * scale + scale / 2, neighbor.getRow() * scale + scale / 2);
                }
            }
        }
        
        if (target.getVisited()) {
            Cell cur = target.getPrev();
            while (cur != start) {
                g.setColor(Color.GREEN);
                g.fillRect(cur.getCol() * scale + 2, cur.getRow() * scale + 2, scale - 4, scale - 3);
                cur = cur.getPrev();
            }
            cur = target;
            while (cur != start) {
                g.setColor(Color.BLUE);
                g.drawLine(cur.getCol() * scale + scale / 2, cur.getRow() * scale + scale / 2,
                        cur.getPrev().getCol() * scale + scale / 2, cur.getPrev().getRow() * scale + scale / 2);
                cur = cur.getPrev();
            }
        }
    }

    
    /**
     * Counts the number of green cells (cells in most direct path line)
     */
    public int greensCounter(){
        greens = 0;
        if (target.getVisited()) {
            Cell cur = target.getPrev();
            while (cur != start) {
                greens++;
                cur = cur.getPrev();
            }
        }
        return greens;
        }


public static void main(String[] args){
    //TESTS
    Landscape l = new Landscape(10, 10, 0);
    System.out.println(l.getNeighbors(l.grid[4][4]));

}
}

