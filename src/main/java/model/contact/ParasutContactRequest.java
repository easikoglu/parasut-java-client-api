package model.contact;

import lombok.Builder;
import lombok.Data;
import model.contact.enums.ParasutContactAccountType;
import model.contact.enums.ParasutContactType;

/**
 * @author erhanasikoglu
 */

@Data
@Builder
public class ParasutContactRequest {


    String email;
    String name;
    String shortName;
    @Builder.Default
    String contactType = ParasutContactType.PERSON.name().toLowerCase();
    String taxOffice;
    String taxNumber;
    String district;
    String city;
    String address;
    String phone;
    String fax;
    String isAbroad;
    String archived;
    String iban;
    @Builder.Default
    String accountType = ParasutContactAccountType.CUSTOMER.getValue();

}
