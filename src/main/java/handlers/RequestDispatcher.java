package handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import utils.Utils;

public class RequestDispatcher {
    public SendMessage dispatch(Update update) {
        //TODO if user exists check
        try {
            if (update.hasCallbackQuery()) {
                return new CallbackHandler(update).handle();
            }
            if (update.hasMessage() && update.getMessage().getText().strip().startsWith("/")) {
                return new CommandHandler(update).handle();
            }
            return new TextMessageHandler(update).handle();
        } catch (Exception e) {
            return new SendMessage().setChatId(Utils.getIdFromUpdate(update)).setText("Exception caught, contact administrator to fix problem");
        }
    }
}
