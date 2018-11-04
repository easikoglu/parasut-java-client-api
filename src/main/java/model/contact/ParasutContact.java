package model.contact;


import http.HttpOptions;
import http.ParasutHttpClient;
import model.request.*;
import generic.enums.ParasutObjectType;
import generic.util.ParasutUtil;

/**
 * @author erhanasikoglu
 */
public class ParasutContact extends ParasutGenericRequest {


    public static ParasutContact create(HttpOptions httpOptions, ParasutContactRequest contactRequest, ParasutSimpleRequest simpleRequest) {


        String url = httpOptions.getBaseUrl() + httpOptions.getApiVersion() + "/" + simpleRequest.getCompanyId() + "/" + ParasutObjectType.CONTACTS.getValue();

        return ParasutHttpClient.builder().addUrl(url)
                .withHeaders(getHttpHeaders(simpleRequest))
                .withData(ParasutGenericRequest.builder().data(ParasutRequest.builder()
                        .type(ParasutObjectType.CONTACTS.getValue())
                        .attributes(ParasutUtil.populateAttributesWithObjectFields(contactRequest, true))
                        .build()))
                .build()
                .post(ParasutContact.class);
    }
}
