package bot;

import handlers.RequestDispatcher;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.*;

public class HomeworkBot extends TelegramLongPollingBot {
    final String BOT_TOKEN = System.getenv("HOMEWORK_BOT_TOKEN");
    final LinkedBlockingQueue<Update> queue = new LinkedBlockingQueue<>();
    final ExecutorService executor = Executors.newFixedThreadPool(3);
    final MainProcess process = new MainProcess();

    public HomeworkBot(DefaultBotOptions options) {
        super(options);
        process.start();
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            queue.put(update);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "HomeworkBot";
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    private class MainProcess extends Thread {
        @Override
        public void run() {
            Queue<Future<SendMessage>> futureQueue = new ArrayDeque<>();
            while (true){
                try {
                    if (!queue.isEmpty()){
                        Update update = queue.take();
                        queue.remove(update);
                        Future<SendMessage> future = executor.submit(() -> new RequestDispatcher().dispatch(update));
                        futureQueue.add(future);
                    }
                    for (Future<SendMessage> future : futureQueue){
                        if (future != null &&(future.isDone() || future.isCancelled())){
                            execute(future.get());
                            futureQueue.remove(future);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
