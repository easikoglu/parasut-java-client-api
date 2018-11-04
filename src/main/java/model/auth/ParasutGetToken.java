package model.auth;


import http.HttpOptions;
import http.ParasutHttpClient;

import java.time.LocalDateTime;

/**
 * @author erhanasikoglu
 */

public class ParasutGetToken {


    String access_token;
    String refresh_token;
    String token_type;
    Integer expires_in;
    LocalDateTime expireDate;


    public static ParasutGetToken get(HttpOptions httpOptions, ParasutAuthPasswordRequest parasutAuthPasswordRequest) {
        return ParasutHttpClient.builder().addUrl(httpOptions.getBaseUrl() + "/oauth/token")
                .withData(parasutAuthPasswordRequest)
                .build()
                .post(ParasutGetToken.class);

    }

    public static ParasutGetToken refresh(HttpOptions httpOptions, ParasutAuthRefreshRequest parasutAuthRefreshRequest) {
        return ParasutHttpClient.builder().addUrl(httpOptions.getBaseUrl() + "/oauth/token")
                .withData(parasutAuthRefreshRequest)
                .build()
                .get(ParasutGetToken.class);
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }


    @Override
    public String toString() {
        return "ParasutGetToken{" +
                "access_token='" + access_token + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", expires_in=" + expires_in +
                ", expireDate=" + expireDate +
                '}';
    }
}
