package moe.haruue.reflect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark a method as a getter
 * @author Haruue Icymoon haruue@caoyue.com.cn
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Getter {

    /**
     * The name of the field you want to reflect
     * @return field name
     */
    String name();

    /**
     * Determine whether should search for the field in super class recursively.
     *
     * @return if returns false, proxy will search the field only in the target class.
     */
    boolean searchSuper() default false;

}
