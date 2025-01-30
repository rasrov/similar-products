package client;

import com.rasrov.similarproducts.client.MockApiClient;
import com.rasrov.similarproducts.config.WebClientConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {MockApiClient.class, WebClientConfiguration.class})
public class MockApiClientService {

    @Autowired
    private MockApiClient mockApiClient;

    @MockitoBean
    private WebClientConfiguration webClientConfiguration;

    @Test
    void should_be_create_api_client_service_with_not_null_dependencies() {
        assertAll(
                "All dependencies are injected correctly",
                () -> assertThat(mockApiClient).isNotNull(),
                () -> assertThat(webClientConfiguration).isNotNull()

        );
    }
}
