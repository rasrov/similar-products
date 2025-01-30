package config;

import com.rasrov.similarproducts.config.ApiConfiguration;
import com.rasrov.similarproducts.config.MockApiConfiguration;
import com.rasrov.similarproducts.config.WebClientConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ApiConfiguration.class, WebClientConfiguration.class})
@TestPropertySource("classpath:application.properties")
public class WebClientConfigTest {

    @Autowired
    private MockApiConfiguration mockApiConfiguration;

    @Autowired
    private WebClientConfiguration webClientConfiguration;

    @Test
    void web_client_should_be_configured_properly() {
        assertThat(webClientConfiguration).isNotNull();
        assertThat(webClientConfiguration.mockApiWebClient()).isNotNull();
    }

}
