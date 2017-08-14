import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
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

public class bbcCrwler extends Crawler {

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public void getLinks() throws Exception {
        Document doc = Jsoup.connect("http://www.bbc.com/turkce").get();
        ArrayList<String> linksList = new ArrayList<String>();

        Elements links1 = doc.select("#comp-top-story-1 > div > div a");
        Elements links2 = doc.select("#comp-top-story-2 > div a");
        Elements links3 = doc.select("#comp-top-story-3 > div > div a");

        extractLinks(links1, linksList);
        extractLinks(links2, linksList);
        extractLinks(links3, linksList);


        ArrayList<String> linksListNonDuplicates = new ArrayList<String>();

        for (int i = 0; i < linksList.size(); i++) {
            if (!linksList.get(i).contains("http"))
                linksListNonDuplicates.add("http://www.bbc.com/" + linksList.get(i)); // to add the main link for the sub_link
        }

         ArrayList<String> newsList = new ArrayList<String>();


        newsList = getNewUsingBoilerPipe(linksListNonDuplicates);
        storeInDatabase(newsList, linksListNonDuplicates);

    }

    private void storeInDatabase(ArrayList<String> newsList, ArrayList<String> linksListNonDuplicates) {

        MongoClient mongoClient = new MongoClient("localhost", 27017); // connect to database
        MongoDatabase database = mongoClient.getDatabase("news2"); // create database
        MongoCollection<org.bson.Document> collection = database.getCollection("BBCTables"); // create collection

        MongoDatabase database2 = mongoClient.getDatabase("linksCounter2");

        MongoCollection<org.bson.Document> collection2 = database2.getCollection("CounterBBC"); // create collection
        ArrayList<String> newlinks = new ArrayList<String>();

        storeInMongodb(linksListNonDuplicates, newsList, collection, collection2, "BBC");

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
        DBCollection counter = db.getCollection("CounterBBC");
        BasicDBObject searchQuery = new BasicDBObject();
        graphs("BBC", counter);

    }

    private void extractLinks(Elements links, ArrayList<String> linksList) {
        for (Element element : links) {
            String link1 = element.attr("href");
            if (!linksList.contains(link1)) {
                linksList.add("" + link1);
            }
        }
    }


}
