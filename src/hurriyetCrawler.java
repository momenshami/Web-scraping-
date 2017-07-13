import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;


public class hurriyetCrawler extends Crawler {

        public void getLinks() throws Exception {
                 Document doc = Jsoup.connect("http://www.hurriyet.com.tr/").get();
            ArrayList<String> linksList = new ArrayList<String> ();

             Elements links1 = doc.select("body > main > div > div > div:nth-child(1) > div a");
             Elements links2 = doc.select("body > main > div > div > div:nth-child(4) a");
             Elements links3 = doc.select("body > main > div > div > div:nth-child(5) > div.col-xs-8.col-sm-8.col-md-8.col-lg-8 a");
         //    Elements links4 = doc.select("body > main > div > div > div:nth-child(5) > div.col-xs-4.col-sm-4.col-md-4.col-lg-4 > div a");
             //body > main > div > div > div:nth-child(5) > div.col-xs-4.col-sm-4.col-md-4.col-lg-4 > div

                extractLinks(links1, linksList);
                 extractLinks(links2, linksList);
                 extractLinks(links3, linksList);
               //  extractLinks(links4, linksList);
                 String fileName ="hurriyetNew.txt";

            addLinksToFile( linksList);
            usingBoilerPipe(linksList, fileName);
        }

    private void addLinksToFile(ArrayList<String> linksList) {
        super.addLinksToFile(linksList,"hurriyetlinksList.txt");
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
