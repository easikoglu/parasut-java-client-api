package model.invoice;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author erhanasikoglu
 */
@Data
@Builder
public class ParasutInvoiceRequest {

    ParasutInvoiceAttributes attributes;
    ArrayList<ParasutInvoiceDetails> details;
    String contactId;
    List<String> tagList;
    String categoryId;


}
