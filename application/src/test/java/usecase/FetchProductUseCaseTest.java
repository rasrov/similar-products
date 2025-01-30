package usecase;

import com.rasrov.similarproducts.domain.ProductDetail;
import com.rasrov.similarproducts.domain.ProductDetailDto;
import com.rasrov.similarproducts.usecase.FetchProductUseCase;
import com.rasrov.similarproducts.usecase.MockApiUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {FetchProductUseCase.class})
public class FetchProductUseCaseTest {

    private final static Integer PRODUCT_ID = 1;

    @Autowired
    private FetchProductUseCase fetchProductUseCase;

    @MockitoBean
    private MockApiUseCase mockApiUseCase;

    @Test
    void fetch_product_should_be_set_properly() {
        assertThat(fetchProductUseCase).isNotNull();
        assertThat(mockApiUseCase).isNotNull();
    }

    @Test
    void should_return_empty_similar_products_when_data_not_found() {
        given(mockApiUseCase.similarIds(eq(PRODUCT_ID))).willReturn(Mono.just(Set.of()));

        var similarProduct = fetchProductUseCase.similarProducts(PRODUCT_ID);
        assertThat(similarProduct).isNotNull();
        assertThat(similarProduct.productDetail()).isEmpty();
    }

    @Test
    void should_return_similar_products() {
        var product = 10;
        given(mockApiUseCase.similarIds(eq(PRODUCT_ID))).willReturn(Mono.just(Set.of(product)));
        given(mockApiUseCase.productDetail(eq(product))).willReturn(Mono.just(new ProductDetail(product, "name", 0.0, false, null)));

        var similarProduct = fetchProductUseCase.similarProducts(PRODUCT_ID);
        assertThat(similarProduct).isNotNull();
        assertThat(similarProduct.productDetail()).isNotEmpty();
        assertThat(similarProduct.productDetail().size()).isEqualTo(1);

        assertThat(similarProduct.productDetail().getFirst()).isEqualTo(productDetailDto(product));
    }

    private ProductDetailDto productDetailDto(final Integer id) {
        return new ProductDetailDto(id, "name", 0.0, false, null);
    }

}
