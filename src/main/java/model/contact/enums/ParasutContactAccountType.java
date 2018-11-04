package model.contact.enums;


/**
 * @author erhanasikoglu
 */
public enum ParasutContactAccountType {

    CUSTOMER("customer"), SUPPLIER("supplier");

    String value;

    ParasutContactAccountType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ParasutContactAccountType resolveFromString(String givenType) {

        for (ParasutContactAccountType type : ParasutContactAccountType.values()) {
            if (type.getValue().equals(givenType)) {
                return type;
            }
        }
        return CUSTOMER;
    }


}
