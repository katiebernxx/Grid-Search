/* Katie Bernard
 * 10/24/2022
 */
import java.text.*; //to format the decimal
public class Exploration{
   
    public String densityExploration(double density, int trials) throws InterruptedException{
        //EXPLORATION: What is the relationship between the density of obstacles to probability of reaching the target?
        //Outputs the percentage of trials that are successful (path to target found) for the parameter density value
        
        int numFails = 0;
        int numSuccesses = 0;
        
        for(int i = 0; i<trials; i++){ 
            Landscape l = new Landscape(35,35,density);
            GridSearchExploration g = new GridSearchExploration(l);
            boolean solutionFound = g.depthFirstSearch(0);
            if(solutionFound == true){
                numSuccesses ++;
            } else if (solutionFound == false){
                numFails++;
            }
        }
        DecimalFormat df = new DecimalFormat("#.##"); //format the decimal
        double percSuccess = ((double) numSuccesses / (double) trials);
        String formattedSuccess = df.format(percSuccess);
        String formattedDensity = df.format(density);
        return ("Successes: " + numSuccesses + " Failures: " + numFails + " Percent Success = " + formattedSuccess + " at " + formattedDensity + " density of obstacles.");


    }
    


}