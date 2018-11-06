package model.auth;


import http.HttpOptions;
import http.ParasutHttpClient;

import java.time.LocalDateTime;

/**
 * @author erhanasikoglu
 */

public class ParasutGetToken {


    String accessToken;
    String refreshToken;
    String tokenType;
    Integer expiresIn;
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

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }


    @Override
    public String toString() {
        return "ParasutGetToken{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", expiresIn=" + expiresIn +
                ", expireDate=" + expireDate +
                '}';
    }
}
