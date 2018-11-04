package model.product;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author erhanasikoglu
 */

@Data
@Builder
public class ParasutProductRequest {

    String code;
    String name;
    @Builder.Default
    BigDecimal vatRate = new BigDecimal(18);
    BigDecimal salesExciseDuty;
    String salesExciseDutyType;
    BigDecimal purchaseExciseDuty;
    String purchaseExciseDutyType;
    String unit;
    BigDecimal communicationsTaxRate;
    String archived;
    BigDecimal listPrice;
    String currency;
    BigDecimal buyingPrice;
    String buyingCurrency;
    Boolean inventoryTracking;
    BigDecimal initialStockCount;

}
