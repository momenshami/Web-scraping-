import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
 import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class aljazeeraCrawler extends  Crawler {
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public void getLinks() throws Exception {
        Document doc = Jsoup.connect("http://www.aljazeera.com.tr/front").get();
        ArrayList<String> linksList = new ArrayList<String> ();

        Elements links1 = doc.select("#block-boxes-aljazeera-main-promo-box a");


        extractLinks(links1, linksList);

        ArrayList<String> fileArray = new ArrayList();
        ArrayList<String> newsList = new ArrayList<String>();

        newsList = getNewUsingBoilerPipe(linksList);
        storeInDatabase(newsList,linksList);
    }
    public void storeInDatabase(ArrayList<String> newsList,ArrayList<String> linksList) {

        MongoClient mongoClient = new MongoClient("localhost", 27017); // connect to database
        MongoDatabase database = mongoClient.getDatabase("news2"); // create database
        MongoCollection<org.bson.Document> collection = database.getCollection("aljazeeraTables"); // create collection

        MongoDatabase database2 = mongoClient.getDatabase("linksCounter2");

        MongoCollection<org.bson.Document> collection2 = database2.getCollection("Counteraljazeera"); // create collection
        ArrayList<String>  newlinks = new ArrayList<String>();

        storeInMongodb(linksList, newsList, collection,collection2 ,"aljazeera" );

    }

    @Override
    public void storeInMongodb(ArrayList<String> linksList, ArrayList<String> newsList, MongoCollection<org.bson.Document> collection,
                               MongoCollection<org.bson.Document> collection2, String webSiteName) {
        super.storeInMongodb(linksList, newsList, collection, collection2, webSiteName);
    }
    public ArrayList<String> getNewUsingBoilerPipe(ArrayList<String> linksList) throws IOException {

        return super.getNewUsingBoilerPipe(linksList);
    }

    public void plotGraph(String s) {
        MongoClient mongoClient = new MongoClient("localhost", 27017); // connect to database

        DB db = mongoClient.getDB("linksCounter2");
        DBCollection counter = db.getCollection("Counteraljazeera");
        BasicDBObject searchQuery = new BasicDBObject();
         graphs("Aljazeera", counter);

    }

    private void extractLinks(Elements links, ArrayList<String> linksList){
        for (Element element : links) {

            String link1 = element.attr("href");
            if (!linksList.contains(link1)) {
                if(! link1.contains("http"))
                    linksList.add("http://www.aljazeera.com.tr/"+link1);
                else
                    linksList.add(""+link1);
            }
        }
    }


}