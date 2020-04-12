package callbacks;

public enum SubjectType implements Callback {

    MATH("MATH"),
    PROGRAMMING("PROGRAMMING"),
    ALGEBRA("ALGEBRA"),
    DISCRETE("DISCRETE");

    private final String text;
    public static final SubjectType[] values = values();
    SubjectType(String text) {
        this.text = text;
    }


    @Override
    public Class<? extends SubjectType> getType() {
        return this.getClass();
    }

    @Override
    public String getText() {
        return text;
    }
}
