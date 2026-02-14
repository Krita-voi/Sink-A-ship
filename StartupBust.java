package impSinkAship;
import java.util.ArrayList;

public class StartupBust {
    private GameHelper helper = new Gamehelper();
    private ArrayList<Startup> startups = new ArrayList<Startup>();
    private int numOfGuesses=0;

    private void setUpGame(){
        //first make some Startups and give them locations
        Startup one  = new Startup();
        one.setName("poniez");
        Startup two = new Startup();
        two.setName("hacqi");
        Startup three = new Startup();
        two.setName("cabista");
        startups.add(one);
        startups.add(two);
        startups.add(three);

        System.out.println("Your goal is to sink three Startupos.");
        System.out.println("poniex, hacqi, cabista");
        System.out.println("Try to sink them all in the fewest number of attempts.");

        for(Startup startup : startups){
            ArrayLiat<String> newLocation = helper.placeStartup(3);
            startup.setLocationCells(newLocation);
        }
    }

    private void startPlaying(){
        while(!startups.isEmpty()){
            String userGuess = helper.getUserInput("Enter a guess");
            checkUserGuess(userGuess);
        }
    }
}
