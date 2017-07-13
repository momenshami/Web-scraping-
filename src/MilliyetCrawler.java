import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

public class MilliyetCrawler extends Crawler {

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


        ArrayList<String> linksList = new ArrayList<String> ();
        extractLinks(links1, linksList);
        extractLinks(links2, linksList);
        extractLinks(links3, linksList);
        extractLinks(links4, linksList);
        extractLinks(links5, linksList);
        String fileName= "milliyetNew.txt";

        addLinksToFile( linksList);
        usingBoilerPipe(linksList, fileName);
    }

    private void addLinksToFile(ArrayList<String> linksList) {
        super.addLinksToFile(linksList,"milliyetlinksList.txt");
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
