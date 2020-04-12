package handlers;

import callbacks.RequestType;
import orm.services.UserService;
import entities.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import utils.Keyboards;
import utils.Role;
import utils.Utils;

public class CommandHandler implements Handler {

    final UserService service = new UserService();
    final SendMessage sendMessage = new SendMessage();
    final Update update;

    public CommandHandler(Update update) {
        this.update = update;
        sendMessage.setChatId(Utils.getIdFromUpdate(update));
    }

    @Override
    public SendMessage handle() {
        String text = update.getMessage().getText().strip();
        if (text.startsWith("/start")) {
            return sendMessage.setText("Hello, first of all you should /register .");
        } else if (text.startsWith("/register")) {
            User user = service.findUserById(Utils.getIdFromUpdate(update));
            user = (user == null) ? new User(Utils.getIdFromUpdate(update), "", Role.USER, RequestType.REGISTER) : user;
            user.setCurrentCallback(RequestType.REGISTER);
            service.save(user);
            return sendMessage.setText("Enter you nickname");
        } else if (text.startsWith("/help")) {
            return sendMessage.setText("/register for registration \n/showkeyboard or just /keyboard is for keyboard \n/rename is for changing name\n/help is for help");
        } else if (text.startsWith("/showkeyboard") || text.startsWith("/keyboard")) {
            Role role = service.findUserById(Utils.getIdFromUpdate(update)).getRole();
            return sendMessage.setText("Choose one these").setReplyMarkup(Keyboards.getFirstKeyboard(role));
        } else if (text.startsWith("/rename")) {
            User user = service.findUserById(Utils.getIdFromUpdate(update));
            user.setCurrentCallback(RequestType.REGISTER);
            service.save(user);
            return sendMessage.setText("Type your new name");
        } else return sendMessage.setText("Something went wrong :(");
    }

}
