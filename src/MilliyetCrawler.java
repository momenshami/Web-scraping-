
import com.mongodb.*;
import com.mongodb.client.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.mongodb.client.MongoDatabase;


public class MilliyetCrawler extends Crawler {
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public void getLinks() throws Exception {
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.milliyet.com.tr/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        org.jsoup.select.Elements links1 = doc.select(".flashbar   a");
        org.jsoup.select.Elements links2 = doc.select(".flashbar1 .top_p1 a");
        org.jsoup.select.Elements links3 = doc.select(".flashbar1 .top_p2 a");
        org.jsoup.select.Elements links4 = doc.select(".flashbar1 .tnw a");
        org.jsoup.select.Elements links5 = doc.select(".manset  a");


        ArrayList<String> linksList = new ArrayList<String>();
        extractLinks(links1, linksList);
        extractLinks(links2, linksList);
        extractLinks(links3, linksList);
        extractLinks(links4, linksList);
        extractLinks(links5, linksList);

        ArrayList<String> fileArray = new ArrayList();
        ArrayList<String> newsList = new ArrayList<String>();


        newsList = getNewUsingBoilerPipe(linksList);
        storeInDatabase(newsList,linksList);
    }

    @Override
    public void graphs(String webSiteName, DBCollection newsInfo) {
        super.graphs(webSiteName, newsInfo);
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
        DBCollection counter = db.getCollection("Countermilliyet");
        BasicDBObject searchQuery = new BasicDBObject();
        graphs("milliyet", counter);

    }


    private void extractLinks(Elements links, ArrayList<String> linksList) {
        for (Element element : links) {
            String link1 = element.attr("href");
            if (!linksList.contains(link1)) {
                linksList.add(link1);
            }
        }
    }

    public void storeInDatabase(ArrayList<String> newsList,ArrayList<String> linksList) {

        MongoClient mongoClient = new MongoClient("localhost", 27017); // connect to database
        MongoDatabase database = mongoClient.getDatabase("news2"); // create database
        MongoCollection<org.bson.Document> collection = database.getCollection("milliyetTables"); // create collection

        MongoDatabase database2 = mongoClient.getDatabase("linksCounter2");
        MongoCollection<org.bson.Document> collection2 = database2.getCollection("Countermilliyet"); // create collection
        ArrayList<String> newlinks = new ArrayList<String>();

        storeInMongodb(linksList, newsList, collection, collection2, "milliyet");

    }
}
