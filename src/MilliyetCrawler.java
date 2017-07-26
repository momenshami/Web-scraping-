import com.mongodb.client.*;
import com.mongodb.util.JSON;
import org.bson.conversions.Bson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import com.mongodb.MongoClient;
import org.codehaus.jackson.map.ObjectMapper;
import com.mongodb.client.MongoDatabase;

public class MilliyetCrawler extends Crawler {


public  String myJsonConvertor(String link)
{
    return "{'link': '"+ link+"'}";
}
    public void getLinks() throws Exception {
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.milliyet.com.tr/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        org.jsoup.select.Elements links1 = doc.select(".flashbar   a");
        org.jsoup.select.Elements links2 = doc.select(".flashbar1 .top_p1 a");
//        org.jsoup.select.Elements links3 = doc.select(".flashbar1 .top_p2 a");
//        org.jsoup.select.Elements links4 = doc.select(".flashbar1 .tnw a");
//        org.jsoup.select.Elements links5 = doc.select(".manset  a");


        ArrayList<String> linksList = new ArrayList<String> ();
        extractLinks(links1, linksList);
        extractLinks(links2, linksList);
//        extractLinks(links3, linksList);
//        extractLinks(links4, linksList);
//        extractLinks(links5, linksList);
        String newsFile= "milliyetNews.txt";
        String linksFile = "milliyetlinksList.txt";

       // addLinksToFile(linksList,linksFile);
        ArrayList<String> fileArray = new ArrayList();
        ArrayList<String> newsList = new ArrayList<String>();


        newsList=getNewUsingBoilerPipe(linksList);


//        for (int i = 00; i <newsList.size() ; i++) {
//            System.out.println(linksList.get(i));
//            System.out.println(newsList.get(i));
//        }



        for (int i = 0; i <linksList.size() ; i++) {
         //   System.out.println(myJsonConvertor(linksList.get(i)));

        }
        ObjectMapper mapper = new ObjectMapper();
       MongoClient mongoClient = new MongoClient( "localhost" , 27017 ); // connect to database

       MongoDatabase database = mongoClient.getDatabase("milliData"); // create databse
       MongoCollection<org.bson.Document> collection = database.getCollection("news"); // create collection

        for (int i = 0; i <linksList.size() ; i++) {
            String x= linksList.get(i);
            org.bson.Document toInsert = new org.bson.Document("links ", newsList.get(i));
            collection.insertOne(toInsert);
        }

        MongoDatabase database2 = mongoClient.getDatabase("milliLinks"); // create databse
        MongoCollection<org.bson.Document> collection2 = database2.getCollection("links"); // create collection

        for (int i = 0; i <linksList.size() ; i++) {
            String x= linksList.get(i);
            org.bson.Document toInsert = new org.bson.Document("links", linksList.get(i));
            org.bson.Document toInsert2 = new org.bson.Document("news", newsList.get(i));
            collection2.insertOne(toInsert2);
            collection2.insertOne(toInsert);


        }





        for(Object x : collection.find((Bson) new org.bson.Document())) {
            // System.out.println(x.toString());
        }
        for(Object x : collection2.find((Bson) new org.bson.Document())) {
            System.out.println(x.toString());
        }

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
        for (Element element : links) {
            String link1 = element.attr("href");
            if (!linksList.contains(link1)) {
                linksList.add(link1);
            }
        }
    }
    public void usingBoilerPipe(ArrayList<String> linksList, String fileName)throws Exception{
    super.usingBoilerPipe(linksList, fileName);

    }

}
