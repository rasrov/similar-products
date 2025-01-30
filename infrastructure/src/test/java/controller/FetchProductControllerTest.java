package controller;

import com.rasrov.similarproducts.SimilarproductsApplication;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SimilarproductsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class FetchProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void handle_not_found_product() throws Exception {
        mockMvc.perform(get("/product/{productId}/similar", 6)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.errorMessage").value("404 Not Found from GET http://localhost:3001/product/6/similarids"));
    }

    @Test
    void handle_time_out_product_detail() throws Exception {
        mockMvc.perform(get("/product/{productId}/similar", 3)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.productDetail", hasSize(3)))
                .andExpect(jsonPath("$.productDetail[2].errorMessage", Matchers.containsString("io.netty.handler.timeout.ReadTimeoutException")));
    }

}
