package config;

import com.rasrov.similarproducts.config.ApplicationConfiguration;
import com.rasrov.similarproducts.config.CacheConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ApplicationConfiguration.class)
@TestPropertySource("classpath:application.properties")
public class CacheConfigurationTest {

    private final static Integer CACHE_DURATION = 100;
    private final static Integer CACHE_MAX_SIZE = 50;

    @Autowired
    private CacheConfiguration cacheConfiguration;

    @Test
    void cache_should_be_configured_properly() {
        assertThat(cacheConfiguration).isNotNull();
        assertThat(cacheConfiguration.caffeineConfig()).isNotNull();
        assertThat(cacheConfiguration.duration()).isEqualTo(CACHE_DURATION);
        assertThat(cacheConfiguration.maxSize()).isEqualTo(CACHE_MAX_SIZE);
    }

}
