package model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author erhanasikoglu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParasutSimpleDataRequest {

    Object data;

}
