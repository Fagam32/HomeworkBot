package utils;

import callbacks.Callback;
import callbacks.RequestType;
import callbacks.SubjectType;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Utils {

    public static long getIdFromUpdate(Update update) {
        return update.getMessage() != null ? update.getMessage().getChat().getId()
                : update.getCallbackQuery().getMessage().getChat().getId();
    }

    public static Callback getCallbackFromString(String s) {
        try {
            if (RequestType.valueOf(s.toUpperCase()).getType() == RequestType.class)
                return RequestType.valueOf(s);
        } catch (IllegalArgumentException ignored) {
        }
        try {
            if (SubjectType.valueOf(s.toUpperCase()).getType() == SubjectType.class)
                return SubjectType.valueOf(s);
        } catch (IllegalArgumentException ignored) {
        }
        return RequestType.NO_REQUEST;

    }
}
