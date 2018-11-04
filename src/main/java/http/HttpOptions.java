package http;

import lombok.Data;

/**
 * @author erhanasikoglu
 */
@Data
public class HttpOptions {

    private String apiKey;
    private String secretKey;
    private String baseUrl;
    private String apiVersion;
}
