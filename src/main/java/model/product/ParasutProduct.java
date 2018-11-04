package model.product;


import http.HttpOptions;
import http.ParasutHttpClient;
import model.request.*;
import generic.enums.ParasutObjectType;
import generic.util.ParasutUtil;

/**
 * @author erhanasikoglu
 */

public class ParasutProduct extends ParasutGenericRequest {


    public static ParasutProduct create(HttpOptions httpOptions, ParasutProductRequest productRequest, ParasutSimpleRequest simpleRequest) {
        String url = httpOptions.getBaseUrl() + httpOptions.getApiVersion() + "/" + simpleRequest.getCompanyId() + "/" + ParasutObjectType.PRODUCTS.getValue();
        return ParasutHttpClient.builder().addUrl(url).withHeaders(getHttpHeaders(simpleRequest))
                .withData(ParasutGenericRequest.builder().data(ParasutRequest.builder()
                        .type(ParasutObjectType.PRODUCTS.getValue())
                        .attributes(ParasutUtil.populateAttributesWithObjectFields(productRequest, true))
                        .build()))
                .build()
                .post(ParasutProduct.class);

    }


}
