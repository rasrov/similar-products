package client;

import com.rasrov.similarproducts.client.MockApiClient;
import com.rasrov.similarproducts.config.WebClientConfiguration;
import com.rasrov.similarproducts.domain.ProductDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collection;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MockApiClientServiceTest {


    @Mock
    private WebClientConfiguration webClientConfiguration;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private MockApiClient mockApiClient;

    @BeforeEach
    void setUp() {
        given(webClientConfiguration.mockApiWebClient()).willReturn(webClient);
    }

    @Test
    void should_execute_similar_ids_request() {
        var productId = 1;
        var similarIds = Set.of(2, 3, 4);

        given(webClient.get()).willReturn(requestHeadersUriSpec);
        given(requestHeadersUriSpec.uri(String.format("/%s/similarids", productId))).willReturn(requestHeadersSpec);
        given(requestHeadersSpec.retrieve()).willReturn(responseSpec);
        given(responseSpec.bodyToFlux(Integer.class)).willReturn(Flux.fromIterable(similarIds));

        StepVerifier.create(mockApiClient.similarIds(productId))
                .expectNextMatches(result ->
                        result.size() == 3)
                .verifyComplete();
    }

    @Test
    void should_execute_product_detail_request() {
        var productId = 2;
        var productDetail = new ProductDetail(productId, "mock", 0.0, false, null);

        given(webClient.get()).willReturn(requestHeadersUriSpec);
        given(requestHeadersUriSpec.uri(String.format("/%s", productId))).willReturn(requestHeadersSpec);
        given(requestHeadersSpec.retrieve()).willReturn(responseSpec);
        given(responseSpec.bodyToMono(ProductDetail.class)).willReturn(Mono.just(productDetail));

        StepVerifier.create(mockApiClient.productDetail(productId))
                .expectNextMatches(result ->
                        result.id().equals(productId))
                .verifyComplete();
    }

}
