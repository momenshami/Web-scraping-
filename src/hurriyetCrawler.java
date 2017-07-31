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


public class hurriyetCrawler extends Crawler {

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        public void getLinks() throws Exception {
                 Document doc = Jsoup.connect("http://www.hurriyet.com.tr/").get();
            ArrayList<String> linksList = new ArrayList<String> ();

             Elements links1 = doc.select("body > main > div > div > div:nth-child(1) > div a");
             Elements links2 = doc.select("body > main > div > div > div:nth-child(4) a");
             Elements links3 = doc.select("body > main > div > div > div:nth-child(5) > div.col-xs-8.col-sm-8.col-md-8.col-lg-8 a");




                extractLinks(links1, linksList);
                 extractLinks(links2, linksList);
                 extractLinks(links3, linksList);

            String newsFile= "hurriyeNew.txt";
            String linksFile = "hurriyelinksList.txt";

            ArrayList<String> fileArray = new ArrayList();

            ArrayList<String> newsList = new ArrayList<String>();


            newsList = getNewUsingBoilerPipe(linksList);

            MongoClient mongoClient = new MongoClient("localhost", 27017); // connect to database
            MongoDatabase database = mongoClient.getDatabase("news"); // create database
            MongoCollection<org.bson.Document> collection = database.getCollection("hurriyetTables"); // create collection

            MongoDatabase database2 = mongoClient.getDatabase("linksCounter");

            MongoCollection<org.bson.Document> collection2 = database2.getCollection("linksCounterHurriyet"); // create collection
            ArrayList<String>  newlinks = new ArrayList<String>();

            storeInMongodb(linksList, newsList, collection,collection2 ,"hurriyet" );
//            DB db = mongoClient.getDB("news");
//            DBCollection table2 = db.getCollection("hurriyetTables");
//
//            BasicDBObject searchQuery = new BasicDBObject();
//
//            DBCursor cursor = table2.find(searchQuery);
//
//            while (cursor.hasNext()) {
//                String news=  cursor.next().toString();
//                System.out.println(  "   "+ news);
//
//
//            }
        }

    @Override
    public void storeInMongodb(ArrayList<String> linksList, ArrayList<String> newsList, MongoCollection<org.bson.Document> collection,
                               MongoCollection<org.bson.Document> collection2, String webSiteName) {
        super.storeInMongodb(linksList, newsList, collection, collection2, webSiteName);
    }
    public ArrayList<String> getNewUsingBoilerPipe(ArrayList<String> linksList) throws IOException {

        return super.getNewUsingBoilerPipe(linksList);
    }

    @Override
    public boolean isFileNotExist(String FileName) {
        return super.isFileNotExist(FileName);
    }

    @Override
    public void createNewFile(String fileName) throws IOException {
        super.createNewFile(fileName);
    }

    @Override
    public boolean isLinkNotExist(String link, String linksFile){
        return super.isLinkNotExist(link, linksFile);
    }
    private ArrayList readFromFile(ArrayList fileArray, String linksFile) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File(linksFile));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            fileArray.add(line);
        }
        scanner.close();

        return fileArray;
    }


    private void extractLinks(Elements links, ArrayList<String> linksList){
        int x=1;
        for (Element element : links) {
             String link1 = element.attr("href");
            if (!linksList.contains(link1)) {
                if( link1.contains("http"))
                         linksList.add(""+link1);
                else
                    linksList.add("http://www.hurriyet.com.tr/"+link1);
             }
        }
    }
    public void usingBoilerPipe(ArrayList<String> linksList, String fileName)throws Exception{
        super.usingBoilerPipe(linksList, fileName);

    }
    }
