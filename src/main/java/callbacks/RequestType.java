package callbacks;

public enum RequestType implements Callback {
    SHOW_TASKS("SHOW_TASKS"),
    COMMIT_TASKS("COMMIT_TASKS"),
    CANCEL_COMMIT("CANCEL_COMMIT"),
    REGISTER("REGISTER"),
    RENAME("RENAME"),
    ADD_TASK("ADD_TASK"),
    REMOVE_TASK("REMOVE_TASK"),
    NO_REQUEST("NO_REQUEST");

    private final String text;

    RequestType(String text) {
        this.text = text;
    }

    @Override
    public Class<? extends RequestType> getType() {
        return this.getClass();
    }

    @Override
    public String getText() {
        return text;
    }
}
