/* Katie Bernard
 * 10/24/2022
 */
import java.util.ArrayList;
import java.lang.Math;

public class GridSearch{
    Landscape scape;
    LandscapeDisplay display;

    public GridSearch(Landscape l, LandscapeDisplay ld){ //constructor
        scape = l;
        display = ld;
       
    }

    
    /**
     * runs a depth first search algorithm to solve the maze using a stack
     * @param delay
     * @return boolean on whether it was successfully completed
     * @throws InterruptedException
     */
    public boolean depthFirstSearch(int delay) throws InterruptedException {
        CellStack stack = new CellStack();
        scape.start.visited = true;
        stack.push(scape.start);

        while(!stack.empty()){
            if (delay > 0){
                Thread.sleep(delay);
                display.repaint();
            }

            Cell cur = stack.pop();
            ArrayList<Cell> neighbors = scape.getNeighbors(cur);
            for(int i = 0; i<neighbors.size(); i++){
                Cell n = neighbors.get(i);
                if(n.type != Cell.Type.OBSTACLE && n.visited == false){
                    n.visited = true;
                    n.prev = cur;

                    if(n.type == Cell.Type.TARGET){
                        return true;
                    }

                    stack.push(n); //add n to the stack
                }
            }
        }
        return false;
  
    }

    /**
     * breadth first search algorithm to find a path through the maze using a queue
     * @param delay
     * @return boolean on whether it was successfully completed
     * @throws InterruptedException
     */
    public boolean breadthFirstSearch(int delay) throws InterruptedException {
        CellQueue queue = new CellQueue();
        scape.start.visited = true;
        queue.offer(scape.start);

        while(!queue.empty()){
           
            if (delay > 0){
                Thread.sleep(delay);
                display.repaint();
            }
            
            Cell cur = queue.poll();
            ArrayList<Cell> neighbors = scape.getNeighbors(cur);
            //for each neighbor
            for(int i = 0; i<neighbors.size(); i++){
                Cell n = neighbors.get(i);
                if(n.type != Cell.Type.OBSTACLE && n.visited == false){
                    n.visited = true;
                    n.prev = cur;

                    if(n.type == Cell.Type.TARGET){
                        return true;
                    }

                    queue.offer(n); //add n to the queue
                }
            }
        }
        return false;
  
    }

    //gets the eucliedian cost of the next cell
    public double getH(Cell n){
        int targetCol = scape.target.getCol();
        int targetRow = scape.target.getRow();
        int nCol = n.getCol();
        int nRow = n.getCol();
        //get the diagonal distance to the target
        double distance = Math.sqrt((targetCol - nCol)^2 - (targetRow - nRow)^2);
        return distance;
    }

    //A* search method: chooses next cell based on f(n)
    public boolean aSearch(int delay) throws InterruptedException{
        int g = 0;
        Cell cur = scape.start;
        while(cur != scape.target){

            //painting onto the landscape display
            if (delay > 0){
                Thread.sleep(delay);
                display.repaint();
            }

            cur.visited = true;
            ArrayList<Cell> neighbors = scape.getNeighbors(cur);
            ArrayList<Double> neighborsF = new ArrayList<Double>(); //list of the f(n) values

            for(int i = 0; i<neighbors.size(); i++){
                Cell n = neighbors.get(i);
                double h = getH(n);
                neighborsF.add(g+h);
            }

            //find the smallest f(n) in that list
            double smallestSoFar = neighborsF.get(0);
            int index = 0;
            for(int i = 0; i<neighborsF.size(); i++){
                if(neighborsF.get(i)<smallestSoFar){
                    smallestSoFar = neighborsF.get(i);
                    index = i;
                }
            }
            //new cur is the option with the smallest f(n)
            cur = neighbors.get(index);

        }
        //Now cur = target
        return true;
        
    }
       
    public static void main(String[] args)  throws InterruptedException{

        /* A* SEARCH METHOD. UNCOMMENT TO RUN 

        Landscape l3 = new Landscape(35, 35, .25);
            LandscapeDisplay ld3 = new LandscapeDisplay(l3, 20);
            GridSearch g3 = new GridSearch(l3, ld3);
            g3.aSearch(20); */
        
        while (true){
            Landscape l = new Landscape(35, 35, .25);
            LandscapeDisplay ld = new LandscapeDisplay(l, 20);
            GridSearch g = new GridSearch(l, ld);
            g.breadthFirstSearch(20);

            Landscape l2 = new Landscape(35, 35, .25);
            LandscapeDisplay ld2 = new LandscapeDisplay(l2, 20);
            GridSearch g2 = new GridSearch(l2, ld2);
            g2.depthFirstSearch(20);
        }

        
    }
}

       
        
          

