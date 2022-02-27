package com.target.myRetail.integrationTest

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.WireMockServer
import com.target.myRetail.domain.Price
import com.target.myRetail.domain.Product
import com.target.myRetail.integrationTest.config.ClientServerMocks
import com.target.myRetail.integrationTest.config.WireMockConfig
import com.target.myRetail.repository.PricingRepository
import org.junit.Assert
import org.junit.ClassRule
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.testcontainers.containers.GenericContainer

import java.util.logging.Logger

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
@AutoConfigureMockMvc
@ContextConfiguration(classes = [ WireMockConfig.class], initializers = ProductManagementIntgTest.MongoDbInitializer.class)
class ProductManagementIntgTest {
  public static final int MONGODB_PORT = 27017;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private PricingRepository pricingRepository;

  @Autowired
  private WireMockServer mockClientServer;

  @ClassRule
  public static GenericContainer mongo = new GenericContainer("mongo:3.2.4")
      .withExposedPorts(27017);

  @BeforeAll
  public static void startContainerAndPublicPortIsAvailable() {
    mongo.start();
  }

  @Test
  public void testGetProductById() throws Exception {
    ClientServerMocks.setupMockProductSuccessResponse(mockClientServer);
    Price price = new Price();
    price.setId(13860428);
    price.setValue(Double.valueOf(100.0));
    price.setCurrencyCode("USD");
    pricingRepository.save(price);

    MvcResult result = mockMvc.perform(get("/products/13860428")
        .contentType("application/json"))
        .andExpect(status().isOk())
        .andReturn();

    Product response = objectMapper.readValue(result.getResponse().getContentAsString(), Product.class);
    Map<String, Object> responsePrice = response.getCurrent_price();
    Assert.assertEquals(13860428, response.getId());
    Assert.assertEquals("The Big Lebowski (Blu-ray)", response.getName());
  }

  @Test
  public void testGetProductByIdAndProductNotFound() throws Exception {
    ClientServerMocks.setupMockProductNotFoundResponse(mockClientServer);

    mockMvc.perform(get("/products/13860429")
        .contentType("application/json"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testGetProductByIdAndTooManyRequests() throws Exception {
    ClientServerMocks.setupMockProductTooManyRequestResponse(mockClientServer);
    Price price = new Price();
    price.setId(13860410);
    price.setValue(Double.valueOf(100.0));
    price.setCurrencyCode("USD");
    pricingRepository.save(price);

    mockMvc.perform(get("/products/13860410")
        .contentType("application/json"))
        .andExpect(status().isTooManyRequests());
  }

  @Test
  public void testGetProductByIdAndPriceDoesNotExists() throws Exception {
    mockMvc.perform(get("/products/13860430")
        .contentType("application/json"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testUpdateProductPrice() throws Exception {
    Price price = new Price();
    price.setValue(23.00);
    price.setId(1)
    price.setCurrencyCode("USD")
    mockMvc.perform(post("/products/1")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(price)))
        .andExpect(status().isCreated());
    Assert.assertNotNull(pricingRepository.findById("1"));
  }


  public static class MongoDbInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    Logger log = Logger.getLogger("ProductManagementIntgTest.MongoDbInitializer.class");

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      log.info("Overriding Spring Properties for mongodb !!!!!!!!!");

      TestPropertyValues values = TestPropertyValues.of(
          "spring.data.mongodb.host=" + mongo.getContainerIpAddress(),
          "spring.data.mongodb.port=" + mongo.getMappedPort(MONGODB_PORT),
          "product.key=3yUxt7WltYG7MFKPp7uyELi1K40ad2ys",
          "product.host=http://localhost:9090"

      );
      values.applyTo(configurableApplicationContext);
    }
  }
}

