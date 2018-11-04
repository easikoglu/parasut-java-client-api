package model.auth;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import model.auth.ParasutAuthRequest;

@Data
@EqualsAndHashCode(callSuper = true)
public class ParasutAuthPasswordRequest extends ParasutAuthRequest {


    String username;
    String password;

    @Builder
    public ParasutAuthPasswordRequest(String clientId, String clientSecret, String redirectUri, String grant_type, String username, String password) {
        super(clientId, clientSecret, redirectUri, grant_type);
        this.username = username;
        this.password = password;
    }
}
