package model.company;


import http.HttpOptions;
import http.ParasutHttpClient;
import model.request.ParasutGenericRequest;
import model.request.ParasutSimpleRequest;


/**
 * @author erhanasikoglu
 */
public class ParasutGetMe extends ParasutGenericRequest {

    public static ParasutGetMe get(HttpOptions httpOptions, ParasutSimpleRequest request) {
        return ParasutHttpClient.builder().addUrl(httpOptions.getBaseUrl() + httpOptions.getApiVersion() + "/me")
                .withHeaders(getHttpHeaders(request))
                .build()
                .get(ParasutGetMe.class);

    }
}
