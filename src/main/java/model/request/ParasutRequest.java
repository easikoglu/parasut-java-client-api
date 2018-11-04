package model.request;


import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author erhanasikoglu
 */
@Data
@Builder
public class ParasutRequest {

    private String id;
    private String type;

    @Builder.Default
    Map<Object, Object> attributes = new HashMap<>();
    @Builder.Default
    Map<Object, Object> relationships = new HashMap<>();

}

