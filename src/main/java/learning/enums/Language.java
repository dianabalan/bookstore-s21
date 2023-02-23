package learning.enums;

public enum Language {

    ENGLISH("en","English"),
    SPANISH("es", "Spanish"),
    ROMANIAN("ro", "Romanian"),
    GERMAN("de", "German"),
    FRENCH("fr", "French");

    private String code;
    private String label;

    Language(String code, String label){
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }
}
