package model.invoice;

import lombok.Builder;
import lombok.Data;
import generic.annotation.IgnoreToPutJsonResponse;

import java.math.BigDecimal;

/**
 * @author erhanasikoglu
 */

@Data
@Builder
public class ParasutInvoiceDetails {

    Integer quantity;
    BigDecimal unitPrice;
    @Builder.Default
    BigDecimal vatRate = new BigDecimal(18);
    String discountType;
    BigDecimal discountValue;
    String exciseDutyType;
    BigDecimal exciseDutyValue;
    BigDecimal communicationsTaxRate;
    String description;
    @IgnoreToPutJsonResponse
    String productId;

}
