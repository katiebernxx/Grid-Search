/* Katie Bernard
 * 10/24/2022
 */
import java.util.ArrayList;
import java.text.*;

public class GridSearchExploration{
    Landscape scape;
    LandscapeDisplay display;
    int DFSvertsVisited = 0; //visited verticies for depth first search
    int BFSvertsVisited = 0;  //visited verticies for breadth first search  

    public GridSearchExploration(Landscape l){
        scape = l;
              
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
        DFSvertsVisited++;

        while(!stack.empty()){
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

                    stack.push(n);
                    DFSvertsVisited++; //add to the number of visited verticies
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
        BFSvertsVisited++; 

        while(!queue.empty()){
                       
            Cell cur = queue.poll();
            ArrayList<Cell> neighbors = scape.getNeighbors(cur);
            for(int i = 0; i<neighbors.size(); i++){
                Cell n = neighbors.get(i);
                if(n.type != Cell.Type.OBSTACLE && n.visited == false){
                    n.visited = true;
                    n.prev = cur;

                    if(n.type == Cell.Type.TARGET){
                        return true;
                    }

                    queue.offer(n);
                    BFSvertsVisited++; //add to the number of visited verticies
                }
            }
        }
        return false;
  
    }
       
    public static void main(String[] args)  throws InterruptedException{
        int trials = 100; //Number of trials that each analysis is performed and averaged over

        //DENSITY OF OBSTACLES VS PROBABILITY OF REACHING TARGET
        System.out.println();
        System.out.println("Relationship between the density of obstacles and the probability of successfully reaching the target:");
        System.out.println();
        double density = 0.00;    
      
        while (density <=1){
            Exploration e = new Exploration();
            System.out.println(e.densityExploration(density, trials));
            density = density + 0.10;
        }

        //DFS LENGTH PATH VS VERTICIES VISITED

        System.out.println();
        System.out.println("Depth First Search: Relationship between the number of verticies visited and the minimal length path");
        System.out.println();

        int i = 0;
        int totalVisited = 0;
        int totalGreens = 0;
        while(i<trials){ 
            Landscape l2 = new Landscape(35, 35, .25);
            GridSearchExploration gse = new GridSearchExploration(l2);
            gse.depthFirstSearch(0);
            totalVisited = totalVisited + gse.DFSvertsVisited;
            totalGreens = totalGreens + l2.greensCounter();
            i++;
        }

        DecimalFormat df = new DecimalFormat("#.##");
        double aveVisited = totalVisited / trials;
        double aveGreens = totalGreens / trials;
        double ratio = aveGreens / aveVisited;
        String formattedRatio = df.format(ratio);
        System.out.println("Average number of verticies visited over " + trials + " trials: " + aveVisited);        
        System.out.println("Average number of verticies in minimal length path over " + trials + " trials: " + aveGreens);
        System.out.println("Ratio of minimal length path length to number of verticies visited on average in depth first search trials: " + formattedRatio);
        
        //BFS LENGTH PATH VS VERTICIES VISITED
        System.out.println();
        System.out.println("Depth First Search: Relationship between the number of verticies visited and the minimal length path");
        System.out.println();

        int b = 0;
        int bTotalVisited = 0;
        int bTotalGreens = 0;
        while(b<trials){ 
            Landscape l2 = new Landscape(35, 35, .25);
            GridSearchExploration gse = new GridSearchExploration(l2);
            gse.breadthFirstSearch(0);
            bTotalVisited = bTotalVisited + gse.BFSvertsVisited;
            bTotalGreens = bTotalGreens + l2.greensCounter();
            b++;
        }

        double bAveVisited = bTotalVisited / trials;
        double bAveGreens = bTotalGreens / trials;
        double bRatio = bAveGreens / bAveVisited;
        String bFormattedRatio = df.format(bRatio);
        System.out.println("Average number of verticies visited over " + trials + " trials: " + bAveVisited);        
        System.out.println("Average number of verticies in minimal length path over " + trials + " trials: " + bAveGreens);
        System.out.println("Ratio of minimal length path length to number of verticies visited on average in depth first search trials: " + bFormattedRatio);
        
         //BFS LENGTH PATH VS DFS LENGTH PATH
         System.out.println();
         System.out.println("Length of Breadth First Search path (minimal length path) compared to length of Depth First Search path");
         System.out.println();
         double pathComparison = bAveGreens / aveGreens;
         String formattedComparison = df.format(pathComparison);
         System.out.println("Average Length of minimal path over " + trials + " trials: " + bAveGreens);
         System.out.println("Average Length of DFS path over " + trials + " trials: " + aveGreens);
         System.out.println("Ratio of minimal path length to DFS path length: " + formattedComparison);

    }        
}
