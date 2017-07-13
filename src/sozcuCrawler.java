import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;


public class sozcuCrawler extends Crawler {
    public void getLinks() throws Exception {
        Document doc = Jsoup.connect("http://www.sozcu.com.tr/").get();
        ArrayList<String> linksList = new ArrayList<String> ();

        Elements links1 = doc.select("#sz_manset a");


        extractLinks(links1, linksList);

        for (int i = 0; i <linksList.size() ; i++) {
            System.out.println(i+" "+linksList.get(i));
        }
        String fileName= "sozcuNew.txt";
         addLinksToFile(  linksList);
        usingBoilerPipe(linksList, fileName);
    }

    private void addLinksToFile(ArrayList<String> linksList) {
        super.addLinksToFile(linksList,"sozculinksList.txt");
    }

    private void extractLinks(Elements links, ArrayList<String> linksList){
        for (Element element : links) {

            String link1 = element.attr("href");
            if (!linksList.contains(link1)) {
                if(! link1.contains("http"))
                    linksList.add("http://www.sozcu.com.tr/"+link1);
                else
                    linksList.add(""+link1);
            }
        }
    }
    public void usingBoilerPipe(ArrayList<String> linksList, String fileName)throws Exception{
        super.usingBoilerPipe(linksList, fileName);

    }
}