import com.mongodb.*;
import com.mongodb.client.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.util.ArrayList;

import com.mongodb.client.MongoDatabase;

public class MilliyetCrawler extends Crawler {
    private  String linksNumberDatabaseName ;
    private  String linksCollectionName;
    private MongoClient mongoClient;
    private MongoDatabase newsDB;
    private ArrayList<String> newsList;
    private ArrayList<String> newlinks;
    private MongoCollection<org.bson.Document> newsCollection;
    private MongoCollection<org.bson.Document> linksCounter;
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    final  String  siteName = "Milliyet";

    public MilliyetCrawler() {
        String newsDatabaseName = "news";
        linksNumberDatabaseName = "linksCounter1";
        String newscolection = "news";
        linksCollectionName = "milliyetCounter";

        mongoClient = new MongoClient("localhost", 27017);/*  to Connect to MongoDB   */
        newsDB = mongoClient.getDatabase(newsDatabaseName); /*  to Connect to MongoDB   */
        MongoDatabase linksNumberDB = mongoClient.getDatabase(linksNumberDatabaseName);
        newsCollection = newsDB.getCollection(newscolection); // create collection
        linksCounter = linksNumberDB.getCollection(linksCollectionName);
        newsList = new ArrayList<String>();
        newlinks = new ArrayList<String>();
    }

    public void getLinks() throws Exception {
        ArrayList<String> linksList;
        linksList = new ArrayList<String>();
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.milliyet.com.tr/").get();
        } catch (IOException e) {
            System.out.println("Enter valid url");
        }
        org.jsoup.select.Elements links1 = doc.select(".flashbar   a");
        org.jsoup.select.Elements links2 = doc.select(".flashbar1 .top_p1 a");
        org.jsoup.select.Elements links3 = doc.select(".flashbar1 .top_p2 a");
        org.jsoup.select.Elements links4 = doc.select(".flashbar1 .tnw a");
        org.jsoup.select.Elements links5 = doc.select("#mnst11   a");

        extractLinks(links1, linksList);
        extractLinks(links2, linksList);
        extractLinks(links3, linksList);
        extractLinks(links4, linksList);
        extractLinks(links5, linksList);

        for (int i = 0; i < linksList.size(); i++) {
            System.out.println("Links list " + linksList.get(i));
        }

        newlinks = checkIfLinkIsAlreadyExist(linksList);
        newsList = getNewUsingBoilerPipe(newlinks);

    }

    public ArrayList<String> checkIfLinkIsAlreadyExist(ArrayList<String> linksList) {

        return super.checkIfLinkIsAlreadyExist(linksList, this.newsCollection, this.newsDB);
    }

    private void extractLinks(Elements links, ArrayList<String> linksList) {
        for (Element element : links) {
            String link1 = element.attr("href");
            if (!linksList.contains(link1)) {
                linksList.add(link1);
            }
        }
    }

    public void storeInDatabase() {
        System.out.println(newsCollection.getNamespace());
        System.out.println(linksCounter.getNamespace());


        storeInMongodb(newlinks, newsList, newsCollection, linksCounter, siteName);

    }

    public ArrayList<String> getNewUsingBoilerPipe(ArrayList<String> linksList) throws IOException {

        return super.getNewUsingBoilerPipe(linksList);
    }

    public void plotGraph() {

        DB db = mongoClient.getDB(linksNumberDatabaseName);
        DBCollection counter = db.getCollection(linksCollectionName);
         graphs(siteName, counter);

    }

    @Override
    public void graphs(String webSiteName, DBCollection newsInfo) {
        super.graphs(webSiteName, newsInfo);
    }

}