import com.mongodb.*;
import java.util.Timer;

public class Databases {
    static MongoClient mongo = new MongoClient("localhost", 27017);
    private String db1Name;
    private String db2Name;



    public void showWhatInsideDatabases() {
        db1Name = "newsDB";
        db2Name = "linksCounter";
        String newscolections[] ={"milliyetNews","sabahNews","BBCNews","CNNNews","aljazeeraNews","sozcuNews","hurriyetNews","odatvNews"} ;
        String linksCollectionsName[] = {"milliyetCounter", "sabahCounter","BBCCounter","CNNCounter","aljazeeraCounter","sozcuCounter","hurriyetCounter","odatvCounter"};

        DB db = mongo.getDB(db1Name);
        DB db1 = mongo.getDB(db2Name);
        for (int i = 0; i < newscolections.length; i++) {

            String counter[] = {newscolections[i], linksCollectionsName[i]};

            printdatapase(db,db1,counter);
        }

    }



    private static void printdatapase(DB db1, DB db, String[] counter) {
        int period = 3000;//10800000

        Timer time = new Timer(); // Instantiate Timer Object
        ScheduledTask st = new ScheduledTask(); // Instantiate SheduledTask class
        for (int i = 0; i < counter.length; i++) {


            DBCollection table = db.getCollection(counter[i]);
            DBCollection table1 = db1.getCollection(counter[i]);


            BasicDBObject searchQuery = new BasicDBObject();
            BasicDBObject searchQuery1 = new BasicDBObject();


            DBCursor cursor = table.find(searchQuery);
            DBCursor cursor2 = table1.find(searchQuery1);


            while (cursor.hasNext()) {
                String doc = cursor.next().toString();
                System.out.println(doc);
            }
             while (cursor2.hasNext()) {
                String doc = cursor2.next().toString();
                System.out.println(doc);
            }
            System.out.println("*********************************************************************");

        }

    }
}
