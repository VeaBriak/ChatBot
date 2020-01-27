import akka.actor.*;
import chatBot.SimpleBot;
import java.util.Scanner;

public class Main
{
    private static Scanner scanner;
    private static ActorSystem system;
    private static ActorRef bot;

    public static void main(String[] args) throws Throwable {
        system = ActorSystem.create("ClientSystem");
        bot = system.actorOf(Props.create(SimpleBot.class));
        scanner = new Scanner(System.in);

        for (;;) {
            System.out.println("BOT: Please, ask your question");
            String question = scanner.nextLine();
            System.out.println("USER: " + question);
            bot.tell(question, ActorRef.noSender());
            if (scanner.nextLine().equals("n")) {
                system.stop(bot);
                break;
            } else if (scanner.nextLine().equals("y")) return;
        }
    }
}
