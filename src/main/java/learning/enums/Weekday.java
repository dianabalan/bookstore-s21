package learning.enums;

public enum Weekday {

    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY;

    public static Weekday safeValueOf(String name) {
        try {
            return Weekday.valueOf(name);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
