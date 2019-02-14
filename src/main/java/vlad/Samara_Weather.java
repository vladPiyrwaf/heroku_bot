package vlad;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

public class Samara_Weather {
    private static Document getPage() throws IOException {
        String url = "https://pogoda.63.ru/?from=a_pogoda";
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }

    public static String getWeather() throws IOException {
        Document page = getPage();
        Element main_teble = page.select("div[class=today-panel__wrapper]").first();
        Elements table = page.select("div[class=today-panel__info__main]");
        Elements left_table = page.select("div[class=today-panel__temperature");
//        Elements down_table = page.select("dl");
        String weather = left_table.select("div").text();
//        String downTable = down_table.select("dl").text();
//        System.out.println(weather);
//        System.out.println(downTable);
        return  weather;
    }
}