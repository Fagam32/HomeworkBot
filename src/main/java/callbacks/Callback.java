package callbacks;

public interface Callback {
    Class<? extends Enum> getType();
    String getText();
}