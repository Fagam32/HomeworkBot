package handlers;

import callbacks.Callback;
import callbacks.RequestType;
import callbacks.SubjectType;
import entities.Homework;
import entities.User;
import entities.UserHomework;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import orm.services.HomeworkService;
import orm.services.UserService;
import utils.Keyboards;
import utils.Role;
import utils.Utils;

import java.util.HashSet;
import java.util.Set;

public class CallbackHandler implements Handler {
    final UserService userService = new UserService();
    final SendMessage sendMessage = new SendMessage();
    final Update update;

    public CallbackHandler(Update update) {
        this.update = update;
        sendMessage.setChatId(Utils.getIdFromUpdate(update));
    }

    public SendMessage handle() {
        Callback callback = Utils.getCallbackFromString(update.getCallbackQuery().getData());
        if (callback.getType() == RequestType.class) {
            switch (RequestType.valueOf(callback.getText())) {
                case ADD_TASK:
                    return addTask();
                case REMOVE_TASK:
                    return removeTask();
                case CANCEL_COMMIT:
                    return cancelCommit();
                case COMMIT_TASKS:
                    return commitTasks();
                case SHOW_TASKS:
                    return showTasks();
                case REGISTER:
                    return register();
            }
        } else if (callback.getType() == SubjectType.class) {
            return showTaskList(SubjectType.valueOf(callback.getText()));
        } else
            return sendMessage.setText("Something went wrong, try again").setReplyMarkup(Keyboards.getCommandKeyboard());

        return null;
    }


    private SendMessage removeTask() {
        User user = userService.findUserById(Utils.getIdFromUpdate(update));
        user.setCurrentCallback(RequestType.REMOVE_TASK);
        userService.save(user);
        return sendMessage.setText("Enter task id");
    }

    private SendMessage addTask() {
        User user = userService.findUserById(Utils.getIdFromUpdate(update));
        user.setCurrentCallback(RequestType.ADD_TASK);
        userService.save(user);
        return sendMessage.setText("First symbol is descriptor\n1-Math\n2-Programming\n3-Algebra\n4-Discrete");
    }

    private SendMessage showTaskList(SubjectType subject) {
        User user = userService.findUserById(Utils.getIdFromUpdate(update));
        user.setCurrentCallback(RequestType.NO_REQUEST);
        userService.save(user);
        HomeworkService homeworkService = new HomeworkService();
        Set<Homework> tasksList = new HashSet<>(homeworkService.findByType(subject));
        Set<UserHomework> userHomeworkSet = user.getUserTasks();

        for (UserHomework userHomework : userHomeworkSet) {
            if (userHomework.isDone())
                tasksList.remove(userHomework.getHomework());
        }
        if (tasksList.size() == 0)
            return sendMessage.setText("You've done all " + subject.getText() + " tasks.");
        StringBuilder sb = new StringBuilder();
        sb.append("Id Task\n");
        for (Homework task : tasksList)
            sb.append(task.toString()).append("\n");
        return sendMessage.setText(sb.toString());
    }


    private SendMessage showTasks() {
        User user = userService.findUserById(Utils.getIdFromUpdate(update));
        user.setCurrentCallback(RequestType.SHOW_TASKS);
        userService.save(user);
        return sendMessage.setText("Choose Subject").setReplyMarkup(Keyboards.getSubjectKeyboard());
    }

    private SendMessage commitTasks() {
        User user = userService.findUserById(Utils.getIdFromUpdate(update));
        user.setCurrentCallback(RequestType.COMMIT_TASKS);
        userService.save(user);
        return sendMessage.setText("Enter the ID of tasks you want to commit(You can use comma as separator)");
    }

    private SendMessage cancelCommit() {
        User user = userService.findUserById(Utils.getIdFromUpdate(update));
        user.setCurrentCallback(RequestType.CANCEL_COMMIT);
        userService.save(user);
        return sendMessage.setText("Enter the ID of tasks you want to cancel(You can use comma as separator)");
    }

    private SendMessage register() {
        User user = userService.findUserById(Utils.getIdFromUpdate(update));
        user = (user == null) ? new User(Utils.getIdFromUpdate(update), "", Role.USER, RequestType.REGISTER) : user;
        user.setCurrentCallback(RequestType.REGISTER);
        userService.save(user);
        return sendMessage.setText("Enter your nickname");
    }
}
