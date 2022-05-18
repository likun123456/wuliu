package test;

import java.lang.annotation.*;

/**
 * Created by nidaye on 2017/11/19.
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SunDeZeng {
    String name() default "孙";
}
