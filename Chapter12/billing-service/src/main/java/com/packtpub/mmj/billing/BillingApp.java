package com.packtpub.mmj.billing;

import com.packtpub.mmj.billing.domain.message.BillingMessageChannels;
import com.packtpub.mmj.billing.domain.message.EventListener;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamMessageConverter;
import org.springframework.cloud.stream.schema.avro.AvroSchemaMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.util.MimeType;

/**
 * @author Sourabh Sharma
 */
@SpringBootApplication(scanBasePackages = {"com.packtpub.mmj.billing", "com.packtpub.mmj.booking"})
@EnableBinding({BillingMessageChannels.class})
public class BillingApp {

  public static void main(String[] args) {
    SpringApplication.run(BillingApp.class, args);
  }

  @Autowired
  public EventListener eventListener;

  @Bean
  @StreamMessageConverter
  public MessageConverter bookingOrderMessageConverter() throws IOException {
    MessageConverter avroSchemaMessageConverter = new AvroSchemaMessageConverter(
        MimeType.valueOf("application/*+avro"));
    ((AvroSchemaMessageConverter) avroSchemaMessageConverter)
        .setSchemaLocation(new ClassPathResource("avro/billingOrder.avsc"));
    return avroSchemaMessageConverter;
  }
}
