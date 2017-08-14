import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;
import de.l3s.boilerpipe.sax.HTMLDocument;
import de.l3s.boilerpipe.sax.HTMLFetcher;
import org.bson.Document;
import org.codehaus.jackson.map.ObjectMapper;
import org.jfree.ui.RefineryUtilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.io.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public abstract class Crawler {
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public abstract void getLinks() throws Exception;
    public abstract void plotGraph(String s);

    public ArrayList<String> getNewUsingBoilerPipe(ArrayList<String> linksList) throws IOException {
        ArrayList<String> newsList = new ArrayList<String>();
        try {
            for (int i = 0; i < linksList.size(); i++) {

                URL url = new URL(linksList.get(i));
                String link = linksList.get(i);//"" )

                final HTMLDocument htmlDoc = HTMLFetcher.fetch(new URL(linksList.get(i)));

                final TextDocument doc = new BoilerpipeSAXInput(htmlDoc.toInputSource()).getTextDocument();
                String content = CommonExtractors.ARTICLE_EXTRACTOR.getText(doc);
                String titleWithContent = doc.getTitle() + " \n" + content;
                System.out.println(link);
                System.out.println(titleWithContent);


                newsList.add(titleWithContent);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return newsList;
    }

    public void storeInMongodb(ArrayList<String> linksList, ArrayList<String> newsList, MongoCollection<Document>
            collection, MongoCollection<Document> collection2, String webSiteName) {

        ArrayList<String> newlinks = new ArrayList<String>();

        MongoClient mongoClient = new MongoClient("localhost", 27017); // connect to database
        MongoDatabase database = mongoClient.getDatabase("news2"); // create database

        MongoDatabase database2 = mongoClient.getDatabase("linksCounter2");

        ObjectMapper mapper = new ObjectMapper();

        for (int i = 0; i < linksList.size(); i++) {
            if (linksList.size() == newsList.size()) {
                org.bson.Document toInsert = new org.bson.Document("links", linksList.get(i)).append("news", newsList.get(i)); // object to inset
                if (collection.find().first() == null) {
                    collection.insertOne(toInsert);
                    newlinks.add(linksList.get(i));
                } else {
                    BasicDBObject filter = new BasicDBObject();
                    filter.put("links", linksList.get(i));
                    if (collection.find(filter).first() == null) {
                        collection.insertOne(toInsert);
                        newlinks.add(linksList.get(i));
                    } else {
                        System.out.println("Sorry " + linksList.get(i) + " already exists");
                    }
                }
            }
        }
        for (int j = 0; j < newlinks.size(); j++) {
            System.out.println(linksList.get(j) + " is a new links  ");

        }
        Date date = new Date();
        String linksDate = sdf.format(date);
        System.out.println("We have in    " + webSiteName + " new   " + newlinks.size() + " link at " + linksDate);

        org.bson.Document toInsert2 = new org.bson.Document("date", linksDate).append("newLinks", newlinks.size()).append("webSiteName", webSiteName); // object to inset
        collection2.insertOne(toInsert2);

    }

    public void graphs(String webSiteName, DBCollection counter) {
        plotGraph chart = new plotGraph(
                "" + webSiteName,
                "links by time ", counter);

        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);

    }


// for writing to file

/*
    public boolean isFileNotExist(String fileName) {
        File f = new File(fileName);
        if (!f.exists()) {
            System.out.println(fileName + "file is not  exist . you should create file first");
            return true;
        }
        System.out.println(fileName + "file is already exist");
        return false;
    }

    public void createNewFile(String fileName) throws IOException {
        try {
            String filename = fileName;
            FileWriter fw; //the true will append the new data

            fw = new FileWriter(filename, false);

            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void addLinksToFile(String link, String linksFile) throws IOException {
        FileWriter fw;
        fw = new FileWriter(linksFile, true);
        fw.write(link);
        fw.write("\n");
        fw.close();
    }


    public void writeToFile(String content, String Link, String FileName) throws IOException {
        try {
            String filename = FileName;
            FileWriter fw; //the true will append the new data
            fw = new FileWriter(filename, true);
            fw.write(Link);
            fw.write(content + "\n\n");//appends the string to the file

            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public boolean isLinkNotExist(String link, String linksFile) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(linksFile));
            ArrayList<String> fileArray = new ArrayList();
            fileArray = readFromFile(fileArray, linksFile);


            if (br.readLine() == null) {
                addLinksToFile(link, linksFile);
                // return  false;
            } else {
                for (int i = 0; i < fileArray.size(); i++) {
                    String linkInArray = fileArray.get(i);
                    if (link.contains(linkInArray))
                        System.out.println(link);
                    addLinksToFile(link, linksFile);
                }

            }


        } catch (FileNotFoundException ex) {
            System.out.println("No File Found!");

        } catch (IOException e) {
            e.printStackTrace();
        }


        return false;
    }
    */
}