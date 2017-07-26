import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


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


            String newsFile= "hurriyeNew.txt";
            String linksFile = "hurriyelinksList.txt";

            ArrayList<String> fileArray = new ArrayList();
            if (isFileNotExist(linksFile)){
                createNewFile(linksFile);
            }
            fileArray = readFromFile(fileArray, linksFile);
            Set<String> set = new HashSet<String>();


            for (int i = 0; i <linksList.size(); i++) {
                set.add(linksList.get(i));
            }
            for (int j = 0; j <fileArray.size() ; j++) {
                set.add(fileArray.get(j));
            }

            createNewFile(linksFile);

            Iterator it = set.iterator();
            while(it.hasNext()) {
                String ss= (String) it.next();
                addLinksToFile(ss,linksFile);
            }
            ArrayList<String> finalList= new ArrayList<String>(set);
            System.out.println(finalList.size());
            createNewFile(newsFile);
            usingBoilerPipe(finalList,newsFile);
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
