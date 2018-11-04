package model.invoice;


import http.HttpOptions;
import http.ParasutHttpClient;
import model.request.*;
import generic.enums.ParasutObjectType;
import generic.util.ParasutUtil;

import java.util.*;

/**
 * @author erhanasikoglu
 */
public class ParasutInvoice extends ParasutGenericRequest {


    public static ParasutInvoice create(HttpOptions httpOptions, ParasutInvoiceRequest invoiceRequest, ParasutSimpleRequest simpleRequest) {


        String url = httpOptions.getBaseUrl() + httpOptions.getApiVersion() + "/" + simpleRequest.getCompanyId() + "/" + ParasutObjectType.SALES_INVOICES.getValue();
        return ParasutHttpClient.builder().addUrl(url)
                .withHeaders(getHttpHeaders(simpleRequest))
                .withData(ParasutGenericRequest.builder().data(ParasutRequest.builder()
                        .type(ParasutObjectType.SALES_INVOICES.getValue())
                        .attributes(ParasutUtil.populateAttributesWithObjectFields(invoiceRequest.attributes, true))
                        .relationships(getInvoiceRelation(invoiceRequest))
                        .build()))
                .build()
            .post(ParasutInvoice.class);
    }


    private static Map<Object, Object> getInvoiceRelation(ParasutInvoiceRequest invoiceRequest) {


        Map<Object, Object> relationship = new HashMap<>();
        relationship.put("contact", ParasutSimpleDataRequest.builder().data(ParasutRequest.builder()
                .id(invoiceRequest.getContactId())
                .type(ParasutObjectType.CONTACTS.getValue())
                .build()).build());


        List<ParasutRequest> tagList = new ArrayList<>();

        if (Objects.nonNull(invoiceRequest.getTagList())) {
            invoiceRequest.getTagList().forEach(s -> tagList.add(ParasutRequest.builder()
                    .id(s)
                    .type(ParasutObjectType.TAGS.getValue())
                    .build()));

            relationship.put("tags", ParasutSimpleDataRequest.builder().data(tagList).build());
        }

        if (Objects.nonNull(invoiceRequest.getCategoryId())) {
            relationship.put("category", ParasutSimpleDataRequest.builder().data(ParasutRequest.builder()
                    .id(invoiceRequest.getCategoryId())
                    .type(ParasutObjectType.ITEM_CATEGORIES.getValue())
                    .build()).build());
        }


        List<ParasutRequest> parasutRequestList = new ArrayList<>();

        for (ParasutInvoiceDetails detail : invoiceRequest.getDetails()) {

            parasutRequestList.add(ParasutRequest.builder()
                    .type(ParasutObjectType.SALES_INVOICE_DETAILS.getValue())
                    .attributes(ParasutUtil.populateAttributesWithObjectFields(detail, true))
                    .relationships(new HashMap<Object, Object>() {
                        {
                            put("product", ParasutSimpleDataRequest.builder().data(ParasutRequest.builder()
                                    .type(ParasutObjectType.PRODUCTS.getValue())
                                    .id(detail.getProductId())
                                    .build()).build());
                        }
                    })
                    .build());
        }

        relationship.put("details", ParasutSimpleDataRequest.builder().data(parasutRequestList).build());


        return relationship;
    }


}
