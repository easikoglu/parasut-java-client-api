package model.invoice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import generic.annotation.IgnoreToPutJsonResponse;
import model.invoice.enums.ParasutCurrency;
import model.invoice.enums.ParasutInvoiceDiscountType;
import model.invoice.enums.ParasutInvoiceType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static generic.util.ParasutUtil.parasutDateFormatter;

/**
 * @author erhanasikoglu
 */

@Data
@Builder
@AllArgsConstructor
public class ParasutInvoiceAttributes {


    @Builder.Default
    String itemType = ParasutInvoiceType.INVOICE.name().toLowerCase();
    String description;
    String issueDate;
    @IgnoreToPutJsonResponse
    LocalDateTime issueDateInDateFormat;
    String dueDate;
    @IgnoreToPutJsonResponse
    LocalDateTime dueDateInDateFormat;
    Integer invoiceSeries;
    Integer invoiceId;
    @Builder.Default
    String currency = ParasutCurrency.TRL.name();
    Integer exchangeRate;
    String withholdingRate;
    BigDecimal vatWithholdingRate;
    @Builder.Default
    String invoiceDiscountType = ParasutInvoiceDiscountType.PERCENTAGE.name().toLowerCase();
    BigDecimal invoiceDiscount;
    String billingAddress;
    String billingPhone;
    String billingFax;
    String taxOffice;
    String taxNumber;
    String city;
    String district;
    String isAbroad;
    String orderNo;
    String orderDate;
    @IgnoreToPutJsonResponse
    LocalDateTime orderDateInDateFormat;
    String shipmentAddres;


    public static class ParasutInvoiceAttributesBuilder {

        LocalDateTime issueDateInDateFormat;
        String issueDate;
        LocalDateTime dueDateInDateFormat;
        String dueDate;
        LocalDateTime orderDateInDateFormat;
        String orderDate;

        public ParasutInvoiceAttributesBuilder issueDateInDateFormat(LocalDateTime issueDateInDateFormat) {
            this.issueDateInDateFormat = issueDateInDateFormat;
            this.issueDate = issueDateInDateFormat.format(parasutDateFormatter);
            return this;
        }

        public ParasutInvoiceAttributesBuilder dueDateInDateFormat(LocalDateTime dueDateInDateFormat) {
            this.dueDateInDateFormat = dueDateInDateFormat;
            this.dueDate = dueDateInDateFormat.format(parasutDateFormatter);
            return this;
        }

        public ParasutInvoiceAttributesBuilder orderDateInDateFormat(LocalDateTime orderDateInDateFormat) {
            this.orderDateInDateFormat = orderDateInDateFormat;
            this.orderDate = orderDateInDateFormat.format(parasutDateFormatter);
            return this;
        }
    }


}
