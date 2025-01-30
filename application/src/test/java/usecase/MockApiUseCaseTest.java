package usecase;

import com.rasrov.similarproducts.domain.ProductDetail;
import com.rasrov.similarproducts.ports.MockApiClientPort;
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
@SpringBootTest(classes = {MockApiUseCase.class})
public class MockApiUseCaseTest {

    private final static Integer PRODUCT_ID = 1;

    @Autowired
    private MockApiUseCase mockApiUseCase;

    @MockitoBean
    private MockApiClientPort mockApiClientPort;

    @Test
    void mock_api_use_case_should_be_set_properly() {
        assertThat(mockApiUseCase).isNotNull();
        assertThat(mockApiClientPort).isNotNull();
    }

    @Test
    void similar_ids_should_be_not_null() {
        final Set<Integer> similarIds = Set.of(10, 100, 1000);

        given(mockApiClientPort.similarIds(eq(PRODUCT_ID))).willReturn(Mono.just(similarIds));

        var ids = mockApiUseCase.similarIds(PRODUCT_ID);
        assertThat(ids).isNotNull();
        assertThat(ids.block()).isEqualTo(similarIds);
    }

    @Test
    void product_detail_should_be_not_null() {
        final ProductDetail productDetail = new ProductDetail(10, "temp", 0.0, true, null);

        given(mockApiClientPort.productDetail(eq(PRODUCT_ID))).willReturn(Mono.just(productDetail));

        var product = mockApiUseCase.productDetail(PRODUCT_ID);
        assertThat(product).isNotNull();
        assertThat(product.block()).isEqualTo(productDetail);
    }

}
