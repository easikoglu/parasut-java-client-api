package model.auth.enums;

public enum ParasutGrantType {

    PASSWORD("password"),
    REFRESH_TOKEN("refresh_token");

    String value;

    ParasutGrantType(String value) {
        this.value = value;
    }


    public String getValue() {
        return value;
    }
}
