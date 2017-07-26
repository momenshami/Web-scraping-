import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.MongoCredential;
public class Main {


    public static void main(String[] args) throws Exception {
        MilliyetCrawler milliyet = new MilliyetCrawler();
//        hurriyetCrawler hurriyet= new hurriyetCrawler();
//        cnnCrawler cnn = new cnnCrawler();
//        bbcCrwler bbc = new bbcCrwler();
//        odatvCrawler odatv = new odatvCrawler();
//        sozcuCrawler sozcu = new sozcuCrawler();
//        aljazeeraCrawler aljazeera= new aljazeeraCrawler();
//        Crawler [] array = {milliyet, hurriyet, cnn, bbc, odatv, sozcu};

        milliyet.getLinks();
//        cnn.getLinks();
//        bbc.getLinks();
//        odatv.getLinks();
//        sozcu.getLinks();
//        aljazeera.getLinks();
//        hurriyet.getLinks();

    }

}