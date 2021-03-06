package http;


import model.request.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author erhanasikoglu
 */
public class ClientResource {
    private static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";

    public ClientResource() {
    }

    protected static Map<String, String> getHttpHeaders(Request request) {
        Map<String, String> headers = new HashMap<String, String>();


        if (Objects.nonNull(request.getToken())) {

            headers.put(AUTHORIZATION, BEARER + request.getToken());
        }
        return headers;
    }

}
