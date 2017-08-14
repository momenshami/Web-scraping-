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


public class cnnCrawler  extends Crawler {

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public void getLinks() throws Exception {
        Document doc = Jsoup.connect("http://www.cnnturk.com/").get();
        ArrayList<String> linksList = new ArrayList<String> ();

          Elements links1 = doc.select("body > div.main-container > div.container.headline-container > div:nth-child(1) > div a");
          Elements links2 = doc.select("body > div.main-container > div.container.headline-container > div:nth-child(3) a");
          Elements links3 = doc.select("body > div.main-container > div.container.headline-container > div.row.flex-order-1 > div.col-md-8.col-sm-12 > div > ol a");

         extractLinks(links1, linksList);
         extractLinks(links2, linksList);
         extractLinks(links3, linksList);

        ArrayList<String> fileArray = new ArrayList();
        ArrayList<String> newsList = new ArrayList<String>();
        ArrayList<String> newslistWith_http = new ArrayList();

        for (int i = 0; i <linksList.size() ; i++) {
            newslistWith_http.add("https://www.cnnturk.com"+linksList.get(i));
        }


         newsList = getNewUsingBoilerPipe(newslistWith_http);
        storeInDatabase(newsList,newslistWith_http);
    }

    private void storeInDatabase(ArrayList<String> newsList, ArrayList<String> linksList) {
        MongoClient mongoClient = new MongoClient("localhost", 27017); // connect to database
        MongoDatabase database = mongoClient.getDatabase("news2"); // create database
        MongoCollection<org.bson.Document> collection = database.getCollection("CnnTable"); // create collection

        MongoDatabase database2 = mongoClient.getDatabase("linksCounter2");

        MongoCollection<org.bson.Document> collection2 = database2.getCollection("CounterCNN"); // create collection
        ArrayList<String>  newlinks = new ArrayList<String>();
        storeInMongodb(linksList, newsList, collection,collection2 ,"CNN" );
        DB db = mongoClient.getDB("linksCounter");
        DBCollection counter = db.getCollection("CounterCNN");
        BasicDBObject searchQuery = new BasicDBObject();
       // graphs("bbc", counter);
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
        DBCollection counter = db.getCollection("CounterCNN");
        BasicDBObject searchQuery = new BasicDBObject();
        graphs(s, counter);
    }


    private void extractLinks(Elements links, ArrayList<String> linksList){
        for (Element element : links) {

            String link1 = element.attr("href");

            if (!linksList.contains(link1)) {
                          linksList.add(link1);
                System.out.println("\n link is "+link1);

            }
        }
    }

}
