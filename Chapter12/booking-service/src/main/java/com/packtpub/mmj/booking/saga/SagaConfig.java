package com.packtpub.mmj.booking.saga;

import com.codebullets.sagalib.MessageStream;
import com.codebullets.sagalib.startup.EventStreamBuilder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SagaConfig {

  private static final Logger LOG = LoggerFactory.getLogger(SagaConfig.class);
  LogModule logModule;
  public static final Map<String, MessageStream> messageStreamMap = new ConcurrentHashMap<>();

  @Autowired
  public void setLogModule(LogModule logModule) {
    this.logModule = logModule;
  }

  public MessageStream getMessageStreamInstance() {
    SagaInterceptor interceptor = new SagaInterceptor();
    BookingSagaProviderFactory sagaProvider = new BookingSagaProviderFactory(interceptor);
    MessageStream msgStream = EventStreamBuilder.configure()
        .usingSagaProviderFactory(sagaProvider)
        .callingModule(logModule)
        .callingInterceptor(interceptor)
        // if needs execution pooling
        //.usingExecutor(Executors.newFixedThreadPool(5))
        .build();
    LOG.info(
        "\n\n\n\n   Init Saga: {}\n   sagaProvider: {}\n   logModule: {}\n   interceptor: {}\n\n\n",
        msgStream, sagaProvider, logModule, interceptor);
    return msgStream;
  }
}
