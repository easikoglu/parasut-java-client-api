package model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author erhanasikoglu
 */
@Data
@AllArgsConstructor
public abstract class Request {

    private String token;

}
