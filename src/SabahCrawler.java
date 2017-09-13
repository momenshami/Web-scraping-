import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by mohammed on 02.08.2017.
 */
public class SabahCrawler extends  Crawler {
    private  String linksNumberDatabaseName ;
    private  String linksCollectionName;
    private   ArrayList<String> newsList  ;
    private   ArrayList<String> newlinks;
    private   MongoClient mongoClient;
    private   MongoDatabase newsDB;
    private   MongoCollection<org.bson.Document> newsCollection;
    private   MongoCollection<org.bson.Document> linksCounter;
    private static    String  siteName = "Sabah";

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public SabahCrawler() {
        String newsDatabaseName = "news";
        linksNumberDatabaseName = "linksCounter1";
        String newscolection = "news";
        linksCollectionName = "sabahCounter";

        mongoClient = new MongoClient("localhost", 27017);/*  to Connect to MongoDB   */
        newsDB = mongoClient.getDatabase(newsDatabaseName); /*  to Connect to MongoDB   */
        MongoDatabase linksNumberDB = mongoClient.getDatabase(linksNumberDatabaseName);
        newsCollection = newsDB.getCollection(newscolection); // create collection
        linksCounter = linksNumberDB.getCollection(linksCollectionName);
        newsList = new ArrayList<String>();
        newlinks = new ArrayList<String>();
     }




    public void getLinks() throws Exception {
      ArrayList <String>  linksList = new ArrayList<String>();
         newsList = new ArrayList<String>();
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.sabah.com.tr/").get();
        } catch (IOException e) {
            System.out.println("Enter valid url");

        }
        System.out.println("wlocme");

        Elements links1 = doc.select("body > section > div > div:nth-child(1) > div > div > div.col-sm-7.col-sm-12.side.left > div > div a");
       Elements links2 = doc.select("body > section > div > div:nth-child(2) > div > div > ul a");
        Elements links3 = doc.select("body > section > div > div:nth-child(3) > div > div > div a");


        extractLinks(links1, linksList);
        extractLinks(links2, linksList);
        extractLinks(links3, linksList);



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

    @Override
    public void graphs(String webSiteName, DBCollection newsInfo) {
        super.graphs(webSiteName, newsInfo);
    }

    public ArrayList<String> getNewUsingBoilerPipe(ArrayList<String> linksList) throws IOException {

        return super.getNewUsingBoilerPipe(linksList);
    }

    public void plotGraph() {

        DB db = mongoClient.getDB(linksNumberDatabaseName);
        DBCollection counter = db.getCollection(linksCollectionName);
         graphs(siteName, counter);

    }

    private void extractLinks(Elements links, ArrayList<String> linksList){
        int x=1;
        for (Element element : links) {
            String link1 = element.attr("href");
            if (!linksList.contains(link1)) {
                if( link1.contains("http"))
                    linksList.add(""+link1);
                else
                    linksList.add("http://www.sabah.com.tr/"+link1);
            }
        }
    }

}
