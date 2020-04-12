package utils;

import callbacks.RequestType;
import callbacks.SubjectType;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class Keyboards {

    public static InlineKeyboardMarkup getFirstKeyboard(Role role){
        switch (role){
            case ADMIN:
            case GOD:
                return getAdminKeyboard();
            default: return getCommandKeyboard();
        }
    }

    public static InlineKeyboardMarkup getAdminKeyboard() {
        InlineKeyboardMarkup keyboard = getCommandKeyboard();
        List<List<InlineKeyboardButton>> list = keyboard.getKeyboard();
        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Add Task").setCallbackData(RequestType.ADD_TASK.getText());
        row.add(button);

        button = new InlineKeyboardButton();
        button.setText("Remove Task").setCallbackData(RequestType.REMOVE_TASK.getText());
        row.add(button);

        list.add(row);
        return keyboard.setKeyboard(list);
    }

    public static InlineKeyboardMarkup getCommandKeyboard() {
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Show tasks").setCallbackData(RequestType.SHOW_TASKS.getText());
        row.add(button);

        button = new InlineKeyboardButton();
        button.setText("Commit tasks").setCallbackData(RequestType.COMMIT_TASKS.getText());
        row.add(button);

        button = new InlineKeyboardButton();
        button.setText("Cancel commit").setCallbackData(RequestType.CANCEL_COMMIT.getText());
        row.add(button);

        list.add(row);

        return keyboard.setKeyboard(list);
    }

    public static InlineKeyboardMarkup getSubjectKeyboard() {

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Math").setCallbackData(SubjectType.MATH.getText());
        row.add(button);

        button = new InlineKeyboardButton();
        button.setText("Programming").setCallbackData(SubjectType.PROGRAMMING.getText());
        row.add(button);

        button = new InlineKeyboardButton();
        button.setText("Algebra").setCallbackData(SubjectType.ALGEBRA.getText());
        row.add(button);

        button = new InlineKeyboardButton();
        button.setText("Discrete").setCallbackData(SubjectType.DISCRETE.getText());
        row.add(button);

        list.add(row);

        return keyboard.setKeyboard(list);

    }
}
