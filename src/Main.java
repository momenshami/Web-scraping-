import com.mongodb.*;
import org.jfree.ui.RefineryUtilities;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Main {


    public static void main(String[] args) throws Exception {
        int period = 108;//10800000

        Timer time = new Timer(); // Instantiate Timer Object
        ScheduledTask st = new ScheduledTask(); // Instantiate SheduledTask class

            System.out.println("good morning");

            int z = 0;
            boolean error = true;

            while (error) {
                try {
                    MilliyetCrawler milliyet = new MilliyetCrawler();
                    SabahCrawler sabah = new SabahCrawler();
                    bbcCrwler bbc = new bbcCrwler();
                    cnnCrawler cnn = new cnnCrawler();
                    odatvCrawler odatv = new odatvCrawler();
                    sozcuCrawler sozcu = new sozcuCrawler();
                    aljazeeraCrawler aljazeera = new aljazeeraCrawler();
                    hurriyetCrawler hurriyet = new hurriyetCrawler();

                    System.out.println("welcome");
                    Crawler[] array = {milliyet, sabah, bbc, cnn, aljazeera, sozcu, hurriyet, odatv};
                    String websiteName[] = {"Millityet", "Sabah", "BBC ", " CNN", "Aljazeera ", "Sozcu", "Hurriyet", "Odatv"};

                    ArrayList<String> newsList = new ArrayList<String>();
                    for (int j = 0; j <array.length ; j++) {
                        array[j].getLinks();
                        array[j].plotGraph(websiteName[j]);
                    }

                    z++;

                    error = false;
                } catch (UnknownHostException e) {
                    System.out.println("****************************************************************************************************************************\n\n\n\n");
                    System.out.println("****************************************************************************************************************************");

                    error = true;
                }

            }
            Thread.sleep(period);
            if (z == 8) {
                System.out.println("Application Terminates");
                System.exit(0);
            }



    }


//    private static void testDatabase() {
//        MongoClient mongo = new MongoClient( "localhost" , 27017 );
//        DB db = mongo.getDB("linksCounter");
//        DBCollection table = db.getCollection("oda");
//
//        BasicDBObject searchQuery = new BasicDBObject();
//
//        DBCursor cursor = table.find(searchQuery);
//
//        while (cursor.hasNext()) {
//            String doc = cursor.next().toString();
//            System.out.println(doc);
//        }
//        BasicDBObject query = new BasicDBObject();
//        query.put("number", new BasicDBObject("$gt", 9));
//        table.remove(query );
//    }

}