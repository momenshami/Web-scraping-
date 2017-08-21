import java.util.ArrayList;
import java.util.Timer;

public class NewsSitesObjects {



    public void createEightObjcet() throws Exception {
        double timeForCompileThisPogram = 3 ;
        int period =80000; //10800000 each three hours
        Timer time = new Timer(); // Instantiate Timer Object
        ScheduledTask st = new ScheduledTask(); // Instantiate SheduledTask class
        int z = 0;
        for (int i = 0; i <= 8; i++) {
             Crawler milliyet = new MilliyetCrawler();
            Crawler sabah = new SabahCrawler();
            Crawler bbc = new BbcCrwler();
            Crawler cnn = new CnnCrawler();
            Crawler odatv = new OdatvCrawler();
            Crawler sozcu = new SozcuCrawler();
            Crawler aljazeera = new AljazeeraCrawler();
            Crawler hurriyet = new HurriyetCrawler();

            Crawler[] array = {milliyet, sabah, bbc, cnn, aljazeera, sozcu, hurriyet, odatv};


            ArrayList<String> newsList = new ArrayList<String>();
            for (int j = 0; j < array.length; j++) {

               array[j].getLinks();
               array[j].storeInDatabase();
                array[j].plotGraph();
            }
                z++;
            System.out.println("*********************************************************************\n\n\n\n");
            System.out.println("                              %d                                     " + z);
            System.out.println("********************************************************************** ");
            Thread.sleep(period);
            if (z == 8) {
                System.out.println("Application Terminates");
                System.exit(0);
            }

        }
    }
}
