package chatBot;

import akka.actor.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SimpleBot extends UntypedAbstractActor
{
    private static String address = "https://mipt.ru/english/edu/faqs/";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static Map<String, String> messages = new TreeMap<>();
    private static Message message;
    private static String bot = "BOT: ";

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof String) {
            parse();
            if (messages.containsKey(message)) {
                System.out.println(bot + messages.get(message));
                System.out.println(bot + "Ask next question? (y/n)");
                return;
            }
            System.out.println(bot + "Not found!");
        }
        System.out.println(bot + "Ask next question? (y/n)");
        unhandled(message);
    }

    public static Map<String, String> parse() throws IOException {
        Document doc = Jsoup.connect(address).maxBodySize(0).get();
        Element elements = doc.select("div[class=page-text]").first(); //get(3);
        Elements sections = elements.select("div[class=o-grid]");
        sections.stream().skip(1).forEach(section -> {
            Elements questions = section.select("h6[class=u-margin--none]");
            String question = questions.text();
            Elements answers = section.select("article[class=c-article c-article--news]");
            String answer = answers.text();
            messages.put(question, answer);
        });
        return messages;
        // createJsonFile();
    }

    static void createJsonFile() throws IOException {
        message = new Message(SimpleBot.messages);
        try (FileWriter fileWriter = new FileWriter("src/main/resources/message.json")) {
            fileWriter.write(GSON.toJson(message));
        }
    }
}
