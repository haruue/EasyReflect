package moe.haruue.reflect;

import java.lang.reflect.Proxy;

/**
 * Exported interface of library
 * @author Haruue Icymoon haruue@caoyue.com.cn
 */
public class EasyReflect {

    /**
     * Create reflect proxy for target as interfaceClazz,
     * if you don't have the instance of target, please use {@link #from(Object, Class, Class)}
     * @param target target you want to reflect, can't be {@link null}
     * @param interfaceClazz a interface class for describe things you want to reflect
     * @param <T> auto return type, will always be interfaceClass
     * @return a proxy object, you can call methods by it
     */
    @SuppressWarnings("unchecked")
    public static <T> T from(Object target, Class<T> interfaceClazz) {
        return from(target, target.getClass(), interfaceClazz);
    }

    /**
     * Create reflect proxy for target as interfaceClazz,
     * it provide a way to call static method without the instance of the targetClass
     * you can use <code>from(null, Target.class, TargetInterface.class)</code>
     * @param target target you want to reflect, can be {@link null} but instance methods will produce {@link NullPointerException}
     * @param targetClass the class of target
     * @param interfaceClazz a interface class for describe things you want to reflect
     * @param <T> auto return type, will always be interfaceClass
     * @return a proxy object, you can call method by it
     */
    @SuppressWarnings("unchecked")
    public static <T> T from(Object target, Class<?> targetClass, Class<T> interfaceClazz) {
        return from(target, targetClass, ClassLoader.getSystemClassLoader(), interfaceClazz);
    }

    /**
     * Create reflect proxy for target as interfaceClazz,
     * it provide a way to use a customized class loader
     * @param target target you want to reflect, can't be {@link null}
     * @param classLoader customized class loader
     * @param interfaceClazz a interface class for describe things you want to reflect
     * @param <T> auto return type, will always be interfaceClass
     * @return a proxy object, you can call method by it
     */
    @SuppressWarnings("unchecked")
    public static <T> T from(Object target, ClassLoader classLoader, Class<T> interfaceClazz) {
        return from(target, target.getClass(), classLoader, interfaceClazz);
    }

    /**
     * Create reflect proxy for target as interfaceClazz,
     * you can customize target and class loader
     * @param target target you want to reflect, can be {@link null} but instance methods will produce {@link NullPointerException}
     * @param targetClass the class of target
     * @param classLoader customized class loader
     * @param interfaceClazz a interface class for describe things you want to reflect
     * @param <T> auto return type, will always be interfaceClass
     * @return a proxy object, you can call method by it
     */
    @SuppressWarnings("unchecked")
    public static <T> T from(Object target, Class<?> targetClass, ClassLoader classLoader, Class<T> interfaceClazz) {
        return (T) Proxy.newProxyInstance(classLoader,
                new Class[]{interfaceClazz},
                new ReflectHandler(target, targetClass));
    }

}
