import java.util.Timer;

public class NewsSitesObjects {


    public void createEightObjcet() throws Exception {
        int period = 10800000 ; //each three hours  , 3 hours = 10800000 ms
        Timer time = new Timer(); // Instantiate Timer Object
        ScheduledTask st = new ScheduledTask(); // Instantiate SheduledTask class
        int z = 0;
       // for (int i = 0; i < 8; i++) {
            Crawler milliyet = new MilliyetCrawler();
            Crawler hurriyet = new HurriyetCrawler();

            Crawler[] array = {milliyet, hurriyet}; // Array for objects
            for (int j = 0; j < array.length; j++) {
                array[j].getLinks(); // to get links from sites and store them in the Arraylist.
                array[j].storeInDatabase(); // to store links inside Databases
                array[j].plotGraph(); // to plot the graph
            }
            System.out.println("                             %d                                     " + z);
            System.out.println("********************************************************************** ");
            Thread.sleep(period);
//            if (i == 7) {
//                System.out.println("Application Terminates ");
//                System.exit(0);
//            }

      //  }
    }
}
