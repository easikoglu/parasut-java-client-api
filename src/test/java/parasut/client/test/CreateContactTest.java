package parasut.client.test;

import model.contact.ParasutContact;
import model.contact.ParasutContactRequest;
import model.contact.enums.ParasutContactAccountType;
import model.request.ParasutSimpleRequest;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class CreateContactTest extends BaseTest {


    /**
     * create parasut contact
     */
    @Test
    public void generateContact_withParasutContactRequestParam() {

        ParasutContact parasutContact = ParasutContact.create(httpOptions, ParasutContactRequest.builder()
                        .accountType(ParasutContactAccountType.CUSTOMER.getValue())
                        .email("test@parasut.com")
                        .name("test parasut")
                        .build(),
                ParasutSimpleRequest.builder()
                        .companyId(companyId)
                        .token(parasutGetToken.getAccessToken())
                        .build()
        );
        assertThat(parasutContact.getData().getAttributes().get("email"), equalTo("test@parasut.com"));

    }
}
