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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class SozcuCrawler extends Crawler {
    private  String linksNumberDatabaseName ;
    private  String linksCollectionName;
    private   ArrayList<String> newsList  ;
    private   ArrayList<String> newlinks;
    private   MongoClient mongoClient;
    private   MongoDatabase newsDB;
    private   MongoCollection<org.bson.Document> newsCollection;
    private   MongoCollection<org.bson.Document> linksCounter;
    private static    String  siteName = "Sozcu";

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public SozcuCrawler() {
        String newsDatabaseName = "news";
        linksNumberDatabaseName = "linksCounter1";
        String newscolection = "news";
        linksCollectionName = "sozcuCounter";

        mongoClient = new MongoClient("localhost", 27017);/*  to Connect to MongoDB   */
        newsDB = mongoClient.getDatabase(newsDatabaseName); /*  to Connect to MongoDB   */
        MongoDatabase linksNumberDB = mongoClient.getDatabase(linksNumberDatabaseName);
        newsCollection = newsDB.getCollection(newscolection); // create collection
        linksCounter = linksNumberDB.getCollection(linksCollectionName);
        newsList = new ArrayList<String>();
        newlinks = new ArrayList<String>();
    }
    public void getLinks() throws Exception {
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.sozcu.com.tr/").get();
        } catch (IOException e) {
            System.out.println("Enter valid url");

        }
        ArrayList<String>  linksList = new ArrayList<String> ();
         newsList = new ArrayList<String>();

        Elements links1 = doc.select("#sz_manset a");


        extractLinks(links1, linksList);


        newlinks=checkIfLinkIsAlreadyExist(linksList);
        System.out.println("size of new links array is "+ newlinks.size());
        newsList = getNewUsingBoilerPipe(newlinks);


    }
    public ArrayList<String> checkIfLinkIsAlreadyExist(ArrayList<String> linksList ) {

        return super.checkIfLinkIsAlreadyExist(linksList, this.newsCollection, this.newsDB);
    }


    public void storeInDatabase() {
        System.out.println(newsCollection.getNamespace());
        System.out.println(linksCounter.getNamespace());


        storeInMongodb(newlinks, newsList, newsCollection,linksCounter ,siteName );

    }
    private void extractLinks(Elements links, ArrayList<String> linksList){
        for (Element element : links) {

            String link1 = element.attr("href");
            if (!linksList.contains(link1)) {
                if(! link1.contains("http"))
                    linksList.add("http://www.sozcu.com.tr/"+link1);
                else
                    linksList.add(""+link1);
            }
        }
    }

    @Override
    public void storeInMongodb(ArrayList<String> linksList, ArrayList<String> newsList, MongoCollection<org.bson.Document> collection,
                               MongoCollection<org.bson.Document> collection2, String webSiteName) {
        super.storeInMongodb(linksList, newsList, collection, collection2, webSiteName);
    }
    public ArrayList<String> getNewUsingBoilerPipe(ArrayList<String> linksList) throws IOException {

        return super.getNewUsingBoilerPipe(linksList);
    }

    public void plotGraph() {
        DB db = mongoClient.getDB(linksNumberDatabaseName);
        DBCollection counter = db.getCollection(linksCollectionName);
        graphs(siteName, counter);


    }
    public void graphs(String webSiteName, DBCollection newsInfo) {
        super.graphs(webSiteName, newsInfo);
    }

}