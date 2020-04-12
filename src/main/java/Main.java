import bot.HomeworkBot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Main {
    public static void main(String[] args) {
        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();
        try{
            DefaultBotOptions options = ApiContext.getInstance(DefaultBotOptions.class);

//            options.setProxyHost("51.178.220.190");
//            options.setProxyPort(1080);
//            options.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);

            botsApi.registerBot(new HomeworkBot(options));
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}
