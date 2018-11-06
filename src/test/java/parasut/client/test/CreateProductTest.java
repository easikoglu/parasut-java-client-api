package parasut.client.test;

import model.product.ParasutProduct;
import model.product.ParasutProductRequest;
import model.request.ParasutSimpleRequest;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class CreateProductTest extends BaseTest {

    /**
     * create parasut product
     */
    @Test
    public void generateProduct_withParasutProductRequest() {

        ParasutProduct parasutProduct = ParasutProduct.create(httpOptions, ParasutProductRequest.builder()
                        .code("testProductCode")
                        .buyingPrice(new BigDecimal(89.9))
                        .name("testProductName")
                        .build(),
                ParasutSimpleRequest.builder()
                        .companyId(companyId)
                        .token(parasutGetToken.getAccessToken())
                        .build());
        assertThat(parasutProduct.getData().getAttributes().get("code"), equalTo("testProductCode"));

    }
}
