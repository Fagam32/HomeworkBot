package handlers;

import callbacks.RequestType;
import callbacks.SubjectType;
import entities.Homework;
import entities.User;
import entities.UserHomework;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import orm.services.HomeworkService;
import orm.services.UserHomeworkService;
import orm.services.UserService;
import utils.Role;
import utils.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TextMessageHandler implements Handler {
    final UserService service = new UserService();
    final SendMessage sendMessage = new SendMessage();
    final Update update;
    String messageText;

    public TextMessageHandler(Update update) {
        this.update = update;
        this.messageText = update.getMessage().getText();
        sendMessage.setChatId(Utils.getIdFromUpdate(update));
    }

    @Override
    public SendMessage handle() {
        User user = service.findUserById(Utils.getIdFromUpdate(update));

        switch (user.getCurrentCallback()) {
            case ADD_TASK:
                return addTask();
            case REMOVE_TASK:
                return removeTask();
            case REGISTER:
                return registration();
            case RENAME:
                return rename();
            case COMMIT_TASKS:
                return commitTasks();
            case CANCEL_COMMIT:
                return cancelCommit();
        }
        return sendMessage.setText("Something went wrong");
    }

    private SendMessage commitTasks () {
        List<Integer> taskIds = getIntegersFromString();
        UserService userService = new UserService();
        User user = userService.findUserById(Utils.getIdFromUpdate(update));
        HomeworkService homeworkService = new HomeworkService();
        UserHomeworkService userHomeworkService = new UserHomeworkService();
        for (Integer i : taskIds) {
            if (i < 0) continue;
            UserHomework userHomework = new UserHomework();
            userHomework.setUser(user);
            userHomework.setHomework(homeworkService.findById(i));
            userHomework.setDone(true);
            userHomeworkService.save(userHomework);
        }
        //TODO check if task exists
        return sendMessage.setText("Hope, it works");
    }

    private SendMessage cancelCommit() {
        List<Integer> taskIds = getIntegersFromString();
        UserService userService = new UserService();
        User user = userService.findUserById(Utils.getIdFromUpdate(update));
        UserHomeworkService userHomeworkService = new UserHomeworkService();
        for (Integer i : taskIds) {
            if (i < 0) continue;
            UserHomework userHomework = new UserHomework();
            userHomework.setUser(user);
            userHomework.setHomework(new Homework(i));
            userHomework.setDone(false);
            userHomeworkService.save(userHomework);
        }
        //TODO check if task exists
        return sendMessage.setText("Hope, it works");
    }

    private List<Integer> getIntegersFromString() {
        String[] numbers = messageText.split("[,|\\s]+");
        return Arrays.stream(numbers).map(s -> {
            s = s.strip();
            int integer;
            try {
                integer = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                return -1;
            }
            return integer;
            }).collect(Collectors.toList());
        }

        private SendMessage addTask () {
            SubjectType type = null;
            for (SubjectType tmp : SubjectType.values) {
                System.out.println(tmp.ordinal());
                if (Integer.parseInt(String.valueOf(messageText.charAt(0))) - 1 == tmp.ordinal()) {
                    type = tmp;
                    break;
                }
            }
            if (type == null)
                return sendMessage.setText("Wrong type of Subject");
            messageText = messageText.substring(1).strip();
            Homework homework = new Homework(type, messageText);
            HomeworkService service = new HomeworkService();
            service.save(homework);
            return sendMessage.setText("Homework for " + homework.getType() + " successfully added");
        }

        private SendMessage removeTask () {
            Homework homework = new Homework();
            try {
                homework.setId(Integer.parseInt(messageText));
            } catch (NumberFormatException e) {
                return sendMessage.setText("Caught NumberFormatException. You lost");
            }
            HomeworkService service = new HomeworkService();
            service.remove(homework);
            return sendMessage.setText("Deleted homework:\nID: " + homework.getId() + "\nTask: " + homework.getTask());
        }

        private SendMessage registration () {
            User user = new User(Utils.getIdFromUpdate(update), messageText, Role.USER, RequestType.NO_REQUEST);
            service.save(user);
            return sendMessage.setText("Welcome to HomeworkBot, " + user.getNickname());
        }

        private SendMessage rename () {
            User user = service.findUserById(Utils.getIdFromUpdate(update));
            if (user == null) {
                return sendMessage.setText("You should apply for /registration first");
            }
            user.setNickname(messageText);
            service.save(user);
            return sendMessage.setText("Welcome to HomeworkBot, " + user.getNickname());
        }
    }
