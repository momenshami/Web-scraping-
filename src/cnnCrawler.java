import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;


public class cnnCrawler  extends Crawler {

    public void getLinks() throws Exception {
        Document doc = Jsoup.connect("http://www.cnnturk.com/").get();
        ArrayList<String> linksList = new ArrayList<String> ();

          Elements links1 = doc.select("body > div.main-container > div.container.headline-container > div:nth-child(1) > div #auto-carousel > ol a");
          Elements links2 = doc.select("body > div.main-container > div.container.headline-container > div:nth-child(3) a");
          Elements links3 = doc.select("body > div.main-container > div.container.headline-container > div.row.flex-order-1 > div.col-md-8.col-sm-12 > div > ol a");

        extractLinks(links1, linksList);
         extractLinks(links2, linksList);
         extractLinks(links3, linksList);
//        for (int i = 0; i <linksList.size() ; i++) {
//            System.out.println(linksList.get(i));
//        }
        String fileName= "cnnNew.txt";
        addLinksToFile(  linksList);
        usingBoilerPipe(linksList, fileName);
    }

    private void addLinksToFile(ArrayList<String> linksList) {
        super.addLinksToFile(linksList,"cnnlinksList.txt");
    }

    private void extractLinks(Elements links, ArrayList<String> linksList){
        for (Element element : links) {

            String link1 = element.attr("href");
            if (!linksList.contains(link1)) {
                 if(! link1.contains("http"))
                    linksList.add("http://www.cnnturk.com/"+link1);
                else
                    linksList.add(""+link1);
            }
        }
    }
    public void usingBoilerPipe(ArrayList<String> linksList, String fileName)throws Exception{
        super.usingBoilerPipe(linksList, fileName);

    }
}
