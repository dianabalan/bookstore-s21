package model;

public enum CoverType {

    PAPERBACK, HARD_COVER;

    public static CoverType safeValueOf(String value) {
        try {
            return CoverType.valueOf(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
