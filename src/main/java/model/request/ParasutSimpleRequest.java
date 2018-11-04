package model.request;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author erhanasikoglu
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ParasutSimpleRequest extends Request {
    String companyId;

    @Builder
    public ParasutSimpleRequest(String token, String companyId) {
        super(token);
        this.companyId = companyId;
    }
}
