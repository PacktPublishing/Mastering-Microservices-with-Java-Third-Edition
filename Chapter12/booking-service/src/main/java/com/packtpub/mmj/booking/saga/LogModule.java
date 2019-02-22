package com.packtpub.mmj.booking.saga;

import com.codebullets.sagalib.ExecutionContext;
import com.codebullets.sagalib.SagaModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogModule implements SagaModule {
  private static final Logger LOG = LoggerFactory.getLogger(LogModule.class);

  @Override
  public void onStart(final ExecutionContext context) {
    LOG.debug("handle incoming message {}", context.message());
  }

  @Override
  public void onFinished(final ExecutionContext context) {
    LOG.trace("message handling finished {}", context.message());
  }

  @Override
  public void onError(final ExecutionContext context, final Object message, final Throwable error) {
    LOG.error("There was an error handling message {}", message, error);
  }
}