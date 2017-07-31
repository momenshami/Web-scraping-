import com.mongodb.*;

import java.util.Timer;
import java.util.TimerTask;

public class Main {


    public static void main(String[] args) throws Exception {
//        Timer time = new Timer(); // Instantiate Timer Object
//        ScheduledTask st = new ScheduledTask(); // Instantiate SheduledTask class
//
//        //for demo only.
//        for (int i = 0; i <= 5; i++) {
//            System.out.println("Execution in Main Thread...." + i);
//            Thread.sleep(200000);
//            if (i == 5) {
//                System.out.println("Application Terminates");
//                System.exit(0);
//            }
//        }
        MilliyetCrawler milliyet = new MilliyetCrawler();
        hurriyetCrawler hurriyet = new hurriyetCrawler();
        cnnCrawler cnn = new cnnCrawler();
        bbcCrwler bbc = new bbcCrwler();
        odatvCrawler odatv = new odatvCrawler();
        sozcuCrawler sozcu = new sozcuCrawler();
        aljazeeraCrawler aljazeera = new aljazeeraCrawler();
        Crawler[] array = {milliyet, cnn, bbc, odatv,aljazeera, sozcu, hurriyet};
        for (int i = 0; i < array.length; i++) {
            // array[i].getLinks();

        }

        for (int i = 0; i <  1; i++) {
            System.out.println("Execution in Main Thread...." + i);
            for (int j = 0; j < 1; j++) {
                array[6].getLinks();
            }
                Thread.sleep(2000);
                if (i == 5) {
                    System.out.println("Application Terminates");
                    System.exit(0);
                }
            }


//    }

    }

    private static void testDatabase() {
        MongoClient mongo = new MongoClient( "localhost" , 27017 );
        DB db = mongo.getDB("news");
        DBCollection table = db.getCollection("milliyetTables");

        BasicDBObject searchQuery = new BasicDBObject();

        DBCursor cursor = table.find(searchQuery);

        while (cursor.hasNext()) {
            String doc = cursor.next().get("news").toString();
            System.out.println(doc);
        }
    }

}