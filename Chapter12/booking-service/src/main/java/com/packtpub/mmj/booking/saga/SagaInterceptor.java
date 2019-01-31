package com.packtpub.mmj.booking.saga;

import com.codebullets.sagalib.ExecutionContext;
import com.codebullets.sagalib.HeaderName;
import com.codebullets.sagalib.Headers;
import com.codebullets.sagalib.Saga;
import com.codebullets.sagalib.SagaLifetimeInterceptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//@Component
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SagaInterceptor implements SagaLifetimeInterceptor {

  private static final Logger LOG = LoggerFactory.getLogger(SagaInterceptor.class);
  private Collection<Saga> startedSagas = new ArrayList<>();
  private Map<HeaderName<?>, Object> foundExecutionHeaders;

  Collection<Saga> getStartedSagas() {
    return startedSagas;
  }

  Map<HeaderName<?>, Object> getFoundExecutionHeaders() {
    return foundExecutionHeaders;
  }

  @Override
  public void onStarting(final Saga<?> saga, final ExecutionContext context,
      final Object message) {
    LOG.info(
        "\n\n\n   intercepter: {}\n   onStarting saga: {}\n   state: {}\n   context: {}\n   message: {}\n   foundExecutionHeaders: {}\n\n",
        this, saga.state().getSagaId(), saga.state().instanceKeys(), context, message, foundExecutionHeaders);
  }

  @Override
  public void onHandlerExecuting(final Saga<?> saga, final ExecutionContext context,
      final Object message) {
    foundExecutionHeaders = Headers.copyFromStream(context.getAllHeaders());
    LOG.info(
        "\n\n\n   intercepter: {}\n   onHandlerExecuting saga: {}\n   state: {}\n   context: {}\n   message: {}\n   foundExecutionHeaders: {}\n\n",
        this, saga.state().getSagaId(), saga.state(), context, message, foundExecutionHeaders);
  }

  @Override
  public void onHandlerExecuted(final Saga<?> saga, final ExecutionContext context,
      final Object message) {
    LOG.debug("\n\nonHandlerExecuted saga -> {}\ncontext -z> {}\nmessage -> {}", saga, context,
        message);
  }

  @Override
  public void onFinished(final Saga<?> saga, final ExecutionContext context) {
    LOG.debug("\n\nonFinished saga -> {}\ncontext -z> {}\nmessage -> {}", saga, context);
  }
}