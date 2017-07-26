import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;
import de.l3s.boilerpipe.sax.HTMLDocument;
import de.l3s.boilerpipe.sax.HTMLFetcher;
import org.xml.sax.SAXException;

import java.util.Scanner;

import java.io.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public abstract class Crawler {
    public abstract void getLinks() throws Exception;

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

    public void usingBoilerPipe(ArrayList<String> linksList, String fileName) throws Exception {

        try {
            for (int i = 0; i < linksList.size(); i++) {
                System.out.println("" + linksList.get(i));

                URL url = new URL(linksList.get(i));
                String link = linksList.get(i);//"" )

                final HTMLDocument htmlDoc = HTMLFetcher.fetch(url);
                final TextDocument doc = new BoilerpipeSAXInput(htmlDoc.toInputSource()).getTextDocument();
                String content = CommonExtractors.ARTICLE_EXTRACTOR.getText(doc);
                writeToFile(content, link, fileName);
                System.out.println(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getNewUsingBoilerPipe(ArrayList<String> linksList) throws IOException {
        ArrayList<String> newsList = new ArrayList<String>();
        try {
            for (int i = 0; i < linksList.size(); i++) {

                URL url = new URL(linksList.get(i));
                String link = linksList.get(i);//"" )

                final HTMLDocument htmlDoc = HTMLFetcher.fetch(url);
                final TextDocument doc = new BoilerpipeSAXInput(htmlDoc.toInputSource()).getTextDocument();
                String content = CommonExtractors.ARTICLE_EXTRACTOR.getText(doc);
                System.out.println(link);
                System.out.println(content);
                 newsList.add(content);
             }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (BoilerpipeProcessingException e) {
            e.printStackTrace();
        }



        return newsList;
    }

}