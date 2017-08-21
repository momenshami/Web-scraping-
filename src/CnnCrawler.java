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


public class CnnCrawler  extends Crawler {
    private  String linksNumberDatabaseName ;
    private  String linksCollectionName;
    private   ArrayList<String> newsList  ;
    private   ArrayList<String> newlinks;
    private   MongoClient mongoClient;
    private   MongoDatabase newsDB;
    private   MongoCollection<org.bson.Document> newsCollection;
    private   MongoCollection<org.bson.Document> linksCounter;
    private static    String  siteName = "CNN";

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public CnnCrawler() {

        String newsDatabaseName = "newsDB";
        linksNumberDatabaseName = "linksCounter";
        String newscolection = "CNNNews";
        linksCollectionName = "CNNCounter";

        mongoClient = new MongoClient("localhost", 27017);/*  to Connect to MongoDB   */
        newsDB = mongoClient.getDatabase(newsDatabaseName); /*  to Connect to MongoDB   */
        MongoDatabase linksNumberDB = mongoClient.getDatabase(linksNumberDatabaseName);
        newsCollection = newsDB.getCollection(newscolection); // create collection
        linksCounter = linksNumberDB.getCollection(linksCollectionName);
        newsList = new ArrayList<String>();
        newlinks = new ArrayList<String>();
    }
    public void getLinks() throws Exception {
        newsList = new ArrayList<String>();
       ArrayList newslinkWith_http = new ArrayList();

        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.cnnturk.com/").get();
        } catch (IOException e) {
            System.out.println("Enter valid url");

        }
        ArrayList<String> linksList = new ArrayList<String> ();

          Elements links1 = doc.select("body > div.main-container > div.container.headline-container > div:nth-child(1) > div a");
          Elements links2 = doc.select("body > div.main-container > div.container.headline-container > div:nth-child(3) a");
          Elements links3 = doc.select("body > div.main-container > div.container.headline-container > div.row.flex-order-1 > div.col-md-8.col-sm-12 > div > ol a");

         extractLinks(links1, linksList);
         extractLinks(links2, linksList);
         extractLinks(links3, linksList);


        for (int i = 0; i <linksList.size() ; i++) {
            newslinkWith_http.add("https://www.cnnturk.com"+linksList.get(i));
        }
        newlinks=checkIfLinkIsAlreadyExist(newslinkWith_http);
        System.out.println("size of new links array is "+ newlinks.size());
//        for (int i = 0; i <newlinks.size() ; i++) {
//            System.out.println(newlinks.get(i));
//        }
        newsList = getNewUsingBoilerPipe(newlinks);

    }

    public void storeInDatabase() {
        System.out.println(newsCollection.getNamespace());
        System.out.println(linksCounter.getNamespace());

        storeInMongodb(newlinks, newsList, newsCollection,linksCounter ,siteName );

    }

    public ArrayList<String> checkIfLinkIsAlreadyExist(ArrayList<String> linksList ) {

        return super.checkIfLinkIsAlreadyExist(linksList, this.newsCollection, this.newsDB);
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



    private void extractLinks(Elements links, ArrayList<String> linksList){
        for (Element element : links) {

            String link1 = element.attr("href");

            if (!linksList.contains(link1)) {
                          linksList.add(link1);

            }
        }
    }

}
