public enum BoxValue {

    OBSTACLE(0),
    FREE(1),
    START(2),
    END(3);

    public final int value;

    BoxValue(int value) {
        this.value = value;
    }

    public static BoxValue fromInt(int value) {
        return switch (value) {
            case 0 -> OBSTACLE;
            case 1 -> FREE;
            case 2 -> START;
            case 3 -> END;
            default -> throw new IllegalArgumentException("Invalid value for BoxValue: " + value);
        };
    }
}