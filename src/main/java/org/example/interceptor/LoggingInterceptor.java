package org.example.interceptor;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.example.resource.ExampleResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Priority(2020) 
@Interceptor
@Logged
public class LoggingInterceptor {

   private static final Logger log = LoggerFactory.getLogger(ExampleResource. class);

   @AroundInvoke 
   Object logInvocation(InvocationContext context) throws Exception {
      // ...log before
      Object ret = context.proceed();
      log.info(ret.toString());
      // ...log after
      return ret;
   }

}