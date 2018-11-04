package generic.enums;


/**
 * @author erhanasikoglu
 */

public enum ParasutObjectType {
    CONTACTS("contacts"),
    TAGS("tags"),
    PRODUCTS("products"),
    SALES_INVOICE_DETAILS("sales_invoice_details"),
    SALES_INVOICES("sales_invoices"),
    ITEM_CATEGORIES("item_categories");


    String value;

    ParasutObjectType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
