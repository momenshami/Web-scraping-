

public class Main {
    public static void main(String[] args)throws Exception  {
        MilliyetCrawler milliyet = new MilliyetCrawler();
        hurriyetCrawler hurriyet= new hurriyetCrawler();
        cnnCrawler cnn  = new cnnCrawler();
        bbcCrwler bbc = new bbcCrwler();
        odatvCrawler odatv= new odatvCrawler();
        sozcuCrawler sozcu = new sozcuCrawler();
        aljazeeraCrawler aljazeera= new aljazeeraCrawler();


         milliyet.getLinks();
         cnn.getLinks();
         bbc.getLinks();
         hurriyet.getLinks();
         odatv.getLinks();
         sozcu.getLinks();
         aljazeera.getLinks();
    }

}