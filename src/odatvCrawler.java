import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class odatvCrawler extends Crawler {
    public void getLinks() throws Exception {
        Document doc = Jsoup.connect("http://odatv.com/tops.php").get();
        ArrayList<String> linksList = new ArrayList<String> ();

        Elements links1 = doc.select("#boxed-wrap > section.container.page-content > div:nth-child(2) a");
        extractLinks(links1, linksList);

        ArrayList<String> newsList = new ArrayList<String>();

        newsList = getNewUsingBoilerPipe(linksList);
        storeInDatabase(newsList,linksList);
    }

    private void storeInDatabase(ArrayList<String> newsList, ArrayList<String> linksList) {
        MongoClient mongoClient = new MongoClient("localhost", 27017); // connect to database
        MongoDatabase database = mongoClient.getDatabase("news2"); // create database
        MongoCollection<org.bson.Document> collection = database.getCollection("odatvTables"); // create collection

        MongoDatabase database2 = mongoClient.getDatabase("linksCounter2");

        MongoCollection<org.bson.Document> collection2 = database2.getCollection("odatv"); // create collection
        ArrayList<String>  newlinks = new ArrayList<String>();

        storeInMongodb(linksList, newsList, collection,collection2 ,"odatv" );

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
        DBCollection counter = db.getCollection("odatv");
        BasicDBObject searchQuery = new BasicDBObject();
        graphs("Odatv", counter);

    }

    private void extractLinks(Elements links, ArrayList<String> linksList){
        for (Element element : links) {

            String link1 = element.attr("href");
            if (!linksList.contains(link1)) {
                if(! link1.contains("http"))
                    linksList.add("http://odatv.com/"+link1);
                else
                    linksList.add(""+link1);
            }
        }
    }


}