package moe.haruue.reflect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark a method as a setter
 * @author Haruue Icymoon haruue@caoyue.com.cn
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Setter {

    /**
     * The name of the field you want to reflect
     * @return field name
     */
    String name();

}
