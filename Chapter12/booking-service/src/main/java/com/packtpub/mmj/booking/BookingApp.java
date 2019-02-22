package com.packtpub.mmj.booking;

import com.codebullets.sagalib.MessageStream;
import com.codebullets.sagalib.startup.EventStreamBuilder;
import com.packtpub.mmj.booking.common.httpclient.UserRestClient;
import com.packtpub.mmj.booking.common.openfeign.UserFeignClient;
import com.packtpub.mmj.booking.common.resttemplate.UserRestTemplate;
import com.packtpub.mmj.booking.domain.message.BookingMessageChannels;
import com.packtpub.mmj.booking.domain.model.entity.Booking;
import com.packtpub.mmj.booking.domain.valueobject.UserVO;
import com.packtpub.mmj.booking.saga.BookingSagaProviderFactory;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.stereotype.Component;

/**
 * @author Sourabh Sharma
 */
@SpringBootApplication(scanBasePackages = {"com.packtpub.mmj.billing", "com.packtpub.mmj.booking"})
@EnableEurekaClient
@EnableFeignClients
@EnableBinding(BookingMessageChannels.class)
public class BookingApp {

  /**
   * @param args
   */
  public static void main(String[] args) {
    SpringApplication.run(BookingApp.class, args);
  }

}

@Component
@ConditionalOnProperty(prefix = "command.autorun", name = "enabled", havingValue = "true", matchIfMissing = false)
class DiscoveryClientSample implements CommandLineRunner {

  private static final Logger LOG = LoggerFactory.getLogger(DiscoveryClientSample.class);

  @Autowired
  private DiscoveryClient discoveryClient;

  @Override
  public void run(String... strings) throws Exception {
    final String serviceName = "restaurant-service";
    // print the Discovery Client Description
    LOG.info("\n{}",discoveryClient.description());
    // Get restaurant-service instances and prints its info
    discoveryClient.getInstances(serviceName).forEach(serviceInstance -> {
      LOG.info("\nInstance --> {}\nServer: {}\nPort: {}\nURI: {}\n\n", serviceInstance.getServiceId(),
          serviceInstance.getHost(), serviceInstance.getPort(), serviceInstance.getUri());
    });
  }
}

@Component
@ConditionalOnProperty(prefix = "command.autorun", name = "enabled", havingValue = "true", matchIfMissing = false)
class RestTemplateSample implements CommandLineRunner {

  private static final Logger LOG = LoggerFactory.getLogger(RestTemplateSample.class);

  @Autowired
  private UserRestTemplate userRestTemplate;

  @Override
  public void run(String... strings) throws Exception {
    LOG.info("Creating new user");
    userRestTemplate.postUser();
    LOG.info("\n\n\n\nUpdate newly created user");
    userRestTemplate.putUser();
    LOG.info("\n\nRetrieve users again to check if newly created object got updated");
    userRestTemplate.getUser();
    LOG.info("\n\n\n\nDelete newly created user");
    userRestTemplate.deleteUser();
    LOG.info("\n\nRetrieve users again to check if deleted user still exists");
    userRestTemplate.getUser();
  }
}

@Component
@ConditionalOnProperty(prefix = "command.autorun", name = "enabled", havingValue = "true", matchIfMissing = false)
class OpenfeignClientSample implements CommandLineRunner {

  private static final Logger LOG = LoggerFactory.getLogger(OpenfeignClientSample.class);

  @Autowired
  private UserFeignClient userFeignClient;

  @Override
  public void run(String... strings) throws Exception {
    LOG.info("Creating new user");
    UserVO userVO = new UserVO();
    userVO.setId("5");
    userVO.setName("Y User");
    userVO.setAddress("Y Address");
    userVO.setCity("Y City");
    userVO.setPhoneNo("1234567890");
    try {
      UserVO newUser = userFeignClient.postUser(userVO);
      assert newUser.getId() == "5";
    } catch (Exception e) {
      LOG.error(e.getMessage());
    }
    LOG.info("\n\n\n\nUpdate newly created user");
    userVO = new UserVO();
    userVO.setId("5");
    userVO.setName("Y User 1");
    userVO.setAddress("Y Address 1");
    userVO.setCity("Y City 1");
    userVO.setPhoneNo("1234567890");
    try {
      userFeignClient.putUser(5, userVO);
    } catch (Exception e) {
      LOG.error(e.getMessage());
    }
    LOG.info("\n\nRetrieve users again to check if newly created object got updated");
    try {
      userFeignClient.getUser("y").forEach((UserVO
          user) -> {
        LOG.info("GET /v1/user --> {}", user);
      });
    } catch (Exception e) {
      LOG.error(e.getMessage());
    }
    LOG.info("\n\n\n\nDelete newly created user");
    try {
      userFeignClient.deleteUser(5);
    } catch (Exception e) {
      LOG.error(e.getMessage());
    }
    LOG.info("\n\nRetrieve users again to check if deleted user still exists");
    try {
      userFeignClient.getUser("y").forEach((UserVO
          user) -> {
        LOG.info("GET /v1/user --> {}", user);
      });
    } catch (Exception e) {
      LOG.error(e.getMessage());
    }
  }
}

@Component
@ConditionalOnProperty(prefix = "command.autorun", name = "enabled", havingValue = "true", matchIfMissing = false)
class Java11HttpClientSample implements CommandLineRunner {

  private static final Logger LOG = LoggerFactory.getLogger(Java11HttpClientSample.class);

  // Java 11 HttpClient for calling User REST endpoints
  @Autowired
  private UserRestClient httpClient;

  @Override
  public void run(String... strings) throws Exception {
    LOG.info("Creating new user");
    httpClient.postUser();
    LOG.info("\n\n\n\nUpdate newly created user");
    httpClient.putUser();
    LOG.info("\n\nRetrieve users");
    httpClient.getUser();
    LOG.info("\n\n\n\nPatch updated user");
    httpClient.patchUser();
    LOG.info("\n\nRetrieve patched user");
    httpClient.getUser();
    LOG.info("\n\n\n\nDelete newly created users");
    httpClient.deleteUser();
    LOG.info("\n\nRetrieve user again to check if deleted user still exists");
    httpClient.getUser();
  }
}