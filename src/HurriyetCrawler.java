import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class HurriyetCrawler extends Crawler {
    private  String linksNumberDatabaseName ;
    private  String linksCollectionName;
    private   ArrayList<String> newsList;
    private   ArrayList<String> newlinks;
    private   MongoDatabase newsDB;
    private   MongoCollection<org.bson.Document> newsCollection;
    private   MongoCollection<org.bson.Document> linksCounter;
    private static    String  siteName = "Hurriyet";

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public HurriyetCrawler() {
        String newsDatabaseName = "news";
        linksNumberDatabaseName = "linksCounter1";
        String newscolection = "news";
        linksCollectionName = "hurriyetCounter";
        newsDB = MongoConnection.getDatabase(newsDatabaseName); /*  to Connect to MongoDB   */
        MongoDatabase linksNumberDB = MongoConnection.getDatabase(linksNumberDatabaseName);
        newsCollection = newsDB.getCollection(newscolection); // create collection
        linksCounter = linksNumberDB.getCollection(linksCollectionName);
        newsList = new ArrayList<String>();
        newlinks = new ArrayList<String>();
    }


    public void getLinks() throws Exception {
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.hurriyet.com.tr/").get();
        } catch (IOException e) {
            System.out.println("Enter valid url");

        }
        ArrayList<String> linksList = new ArrayList<String>();
        newsList = new ArrayList<String>();

        Elements links1 = doc.select("body > main > div > div > div:nth-child(1) > div a");
        Elements links2 = doc.select("body > main > div > div > div:nth-child(4) a");
        Elements links3 = doc.select("body > main > div > div > div:nth-child(5) > div.col-xs-8.col-sm-8.col-md-8.col-lg-8 a");
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

    public void plotGraph( ) {
        DBCollection counter =  MongoConnection.getDBCollection(linksNumberDatabaseName, linksCollectionName);
        graphs(siteName, counter);
    }

    private void extractLinks(Elements links, ArrayList<String> linksList) {
        int x = 1;
        for (Element element : links) {
            String link1 = element.attr("href");
            if (!linksList.contains(link1)) {
                if (link1.contains("http"))
                    linksList.add("" + link1);
                else
                    linksList.add("http://www.hurriyet.com.tr/" + link1);
            }
        }
    }
}
