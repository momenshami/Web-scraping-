
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
//                System.out.println(link1);


        ArrayList<String> linksList = new ArrayList<String>();
        extractLinks(links1, linksList);
//        extractLinks(links2, linksList);
//        extractLinks(links3, linksList);
//        extractLinks(links4, linksList);
//        extractLinks(links5, linksList);
        String newsFile = "milliyetNews.txt";
        String linksFile = "milliyetlinksList.txt";

         ArrayList<String> fileArray = new ArrayList();
        ArrayList<String> newsList = new ArrayList<String>();


        newsList = getNewUsingBoilerPipe(linksList);

        MongoClient mongoClient = new MongoClient("localhost", 27017); // connect to database
        MongoDatabase database = mongoClient.getDatabase("news"); // create database
        MongoCollection<org.bson.Document> collection = database.getCollection("milliyetTables"); // create collection

        MongoDatabase database2 = mongoClient.getDatabase("linksCounter");
        database2.drop();
        database.drop();

        MongoCollection<org.bson.Document> collection2 = database2.getCollection("Countermilliyet"); // create collection
        ArrayList<String>  newlinks = new ArrayList<String>();

        storeInMongodb(linksList, newsList, collection,collection2 ,"milliyet" );

        DB db = mongoClient.getDB("news");
        DBCollection table2 = db.getCollection("milliyetTables");

        BasicDBObject searchQuery = new BasicDBObject();

        DBCursor cursor = table2.find(searchQuery);

        while (cursor.hasNext()) {
             String news=  cursor.next().toString();
            System.out.println(  "   "+ news);


        }


     }

    @Override
    public void storeInMongodb(ArrayList<String> linksList, ArrayList<String> newsList, MongoCollection<org.bson.Document> collection,
                               MongoCollection<org.bson.Document> collection2, String webSiteName) {
        super.storeInMongodb(linksList, newsList, collection, collection2, webSiteName);
    }
//        if (isFileNotExist(linksFile)){
//            createNewFile(linksFile);
//        }
//        fileArray = readFromFile(fileArray, linksFile);
//         Set<String> set = new HashSet<String>();

    //        for (int i = 0; i <linksList.size(); i++) {
//            set.add(linksList.get(i));
//        }
//            for (int j = 0; j <fileArray.size() ; j++) {
//                    set.add(fileArray.get(j));
//               }
//
//         createNewFile(linksFile);
//
//        Iterator it = set.iterator();
//        while(it.hasNext()) {
//            String ss= (String) it.next();
//          addLinksToFile(ss,linksFile);
//         }
//        ArrayList<String> finalList= new ArrayList<String>(set);
//        System.out.println(finalList.size());
//        createNewFile(newsFile);
//      usingBoilerPipe(finalList,newsFile);
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
    public boolean isLinkNotExist(String link, String linksFile) {
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


    private void extractLinks(Elements links, ArrayList<String> linksList) {
        for (Element element : links) {
            String link1 = element.attr("href");
            if (!linksList.contains(link1)) {
                linksList.add(link1);
            }
        }
    }

    public void usingBoilerPipe(ArrayList<String> linksList, String fileName) throws Exception {
        super.usingBoilerPipe(linksList, fileName);

    }

}
