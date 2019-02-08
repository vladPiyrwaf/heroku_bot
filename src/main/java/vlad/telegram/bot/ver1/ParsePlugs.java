package vlad.telegram.bot.ver1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Pattern;

public class ParsePlugs {
    private static Document getPage() throws IOException {
        String url = "https://63.ru/text/auto/718634.html";
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }

    public static String getPlugs() throws Exception {
        // Document document = Jsoup.connect("https://63.ru/text/auto/718634.html").get();
        Document page = getPage();
        Element values_end = page.select("span[class=status]").first();

        String date = values_end.select("span").text();
        date = date.split(" ")[1];
        return date;
    }
}
