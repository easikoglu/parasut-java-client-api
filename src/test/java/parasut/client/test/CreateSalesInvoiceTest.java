package parasut.client.test;

import model.contact.ParasutContact;
import model.contact.ParasutContactRequest;
import model.contact.enums.ParasutContactAccountType;
import model.invoice.ParasutInvoice;
import model.invoice.ParasutInvoiceAttributes;
import model.invoice.ParasutInvoiceDetails;
import model.invoice.ParasutInvoiceRequest;
import model.product.ParasutProduct;
import model.product.ParasutProductRequest;
import model.request.ParasutSimpleRequest;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;

public class CreateSalesInvoiceTest extends BaseTest {


    /**
     * you have to create/get parasut contactId first with using ParasutContact.
     * while populating invoice details you have to get/create products with using ParasutProduct
     */
    @Test
    public void generateSalesInvoice_withParasutInvioceRequest() {

        //create a contact on parasut api / you can map parasutContactId with your own userId then reuse it.
        ParasutContact parasutContact = ParasutContact.create(httpOptions, ParasutContactRequest.builder()
                        .accountType(ParasutContactAccountType.CUSTOMER.getValue())
                        .email("test@parasut.com")
                        .name("test parasut")
                        .build(),
                ParasutSimpleRequest.builder()
                        .companyId(companyId)
                        .token(parasutGetToken.getAccessToken())
                        .build()
        );
        //create a product in parasut api / you can map parasutProductId with your own productId then reuse it
        ParasutProduct parasutProduct = ParasutProduct.create(httpOptions, ParasutProductRequest.builder()
                        .code("testProductCode")
                        .buyingPrice(new BigDecimal(89.9))
                        .name("testProductName")
                        .build(),
                ParasutSimpleRequest.builder()
                        .companyId(companyId)
                        .token(parasutGetToken.getAccessToken())
                        .build());

        //trigger sales_invoice request
        ParasutInvoice parasutInvoice = ParasutInvoice.create(httpOptions, ParasutInvoiceRequest.builder()
                        .contactId(parasutContact.getData().getId())
                        .attributes(ParasutInvoiceAttributes.builder()
                                .invoiceId(1)
                                .issueDateInDateFormat(LocalDateTime.now())
                                .billingAddress("billingAddress")
                                .city("istanbul")
                                .orderDateInDateFormat(LocalDateTime.now())
                                .build())
                        .details(generateInvoiceDetailList(parasutProduct))
                        .build(),
                ParasutSimpleRequest.builder()
                        .companyId(companyId)
                        .token(parasutGetToken.getAccessToken())
                        .build());

        assertNotNull(parasutInvoice.getData().getId());

    }


    private ArrayList<ParasutInvoiceDetails> generateInvoiceDetailList(ParasutProduct parasutProduct) {
        return new ArrayList<ParasutInvoiceDetails>() {{


            add(ParasutInvoiceDetails.builder()
                    .unitPrice(new BigDecimal(89.0)) //price without vat value
                    .quantity(1)
                    .productId(parasutProduct.getData().getId())
                    .build());

        }};
    }
}
