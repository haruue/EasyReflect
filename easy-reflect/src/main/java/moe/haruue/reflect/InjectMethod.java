package moe.haruue.reflect;

/**
 * Created by Stardust on 2017/6/22.
 */

import com.sun.istack.internal.NotNull;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark a method to reflect
 *
 * @author Stardust hybbbb1996@gmail.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface InjectMethod {

    /**
     * The name of method to reflect of target class.
     * For default, it will take the name of marked method to reflect.
     *
     * @return the method name
     */
    String name() default "";

    /**
     * Determine whether should search for the method in super class recursively.
     *
     * @return if returns false, proxy will search the method only in the target class.
     */
    boolean searchSuper() default false;

}
