package model.request;


import http.ClientResource;
import lombok.*;
/**
 * @author erhanasikoglu
 */

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ParasutGenericRequest extends ClientResource {

    ParasutRequest data;
    List<ParasutError> errors;

}
