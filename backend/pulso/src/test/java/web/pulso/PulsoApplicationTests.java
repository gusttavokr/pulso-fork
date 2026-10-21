package web.pulso;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import web.pulso.config.BaseTestContainers;

@ActiveProfiles("test")
@SpringBootTest
class PulsoApplicationTests extends BaseTestContainers {

    @Test
    void contextLoads() { }
}