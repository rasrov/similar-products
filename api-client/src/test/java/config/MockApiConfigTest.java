package config;

import com.rasrov.similarproducts.config.ApiConfiguration;
import com.rasrov.similarproducts.config.MockApiConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ApiConfiguration.class)
@TestPropertySource("classpath:application.properties")
public class MockApiConfigTest {

    private final static String PROTOCOL = "http";
    private final static String DOMAIN = "localhost";
    private final static String PORT = "3001";
    private final static String RESOURCE = "product";
    private final static String MOCK_API_URL = "%s://%s:%s/%s";

    @Autowired
    private MockApiConfiguration mockApiConfiguration;

    @Test
    void mock_api_should_be_configured_properly() {
        assertThat(mockApiConfiguration).isNotNull();
        assertThat(mockApiConfiguration.protocol()).isEqualTo(PROTOCOL);
        assertThat(mockApiConfiguration.domain()).isEqualTo(DOMAIN);
        assertThat(mockApiConfiguration.port()).isEqualTo(PORT);
        assertThat(mockApiConfiguration.resource()).isEqualTo(RESOURCE);
        assertThat(mockApiConfiguration.mockApiUrl()).isEqualTo(String.format(MOCK_API_URL, PROTOCOL, DOMAIN, PORT, RESOURCE));
    }

}
