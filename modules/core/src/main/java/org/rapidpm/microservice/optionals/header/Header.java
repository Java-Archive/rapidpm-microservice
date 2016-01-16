package org.rapidpm.microservice.optionals.header;

import java.lang.annotation.*;

/**
 * Created by Sven Ruppert on 04.11.15.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Header {
  int order() default Integer.MAX_VALUE;
}
