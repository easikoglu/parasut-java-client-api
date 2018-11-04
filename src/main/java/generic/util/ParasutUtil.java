package generic.util;

import com.google.common.base.CaseFormat;
import generic.annotation.IgnoreToPutJsonResponse;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author erhanasikoglu
 */

public class ParasutUtil {


    public static DateTimeFormatter parasutDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public static Map<Object, Object> populateAttributesWithObjectFields(Object o, Boolean convertCamelToUnderScore) {
        Class<?> clazz = o.getClass();


        Map<Object, Object> attributes = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(IgnoreToPutJsonResponse.class)) {
                field.setAccessible(true);
                try {
                    Object obj = field.get(o);
                    if (Objects.nonNull(obj)) {
                        String key = field.getName();

                        if (convertCamelToUnderScore)
                            key = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, key);
                        attributes.put(key, obj);
                        //System.out.format("%n%s: %s", key, field.get(o));
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }
        return attributes;
    }


}
