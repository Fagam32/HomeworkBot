package handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface Handler {
    SendMessage handle();
}
