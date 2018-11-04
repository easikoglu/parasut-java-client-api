package model.auth;


import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ParasutAuthRefreshRequest extends ParasutAuthRequest {

    String refresh_token;

    @Builder
    public ParasutAuthRefreshRequest(String clientId, String clientSecret, String redirectUri, String grant_type, String refresh_token) {
        super(clientId, clientSecret, redirectUri, grant_type);
        this.refresh_token = refresh_token;
    }
}
