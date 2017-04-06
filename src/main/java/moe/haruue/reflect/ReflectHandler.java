package moe.haruue.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Real handle which does reflect behind proxy
 * @author Haruue Icymoon haruue@caoyue.com.cn
 */
class ReflectHandler implements InvocationHandler {

    Object target;
    Class<?> targetClazz;

    ReflectHandler(Object target, Class<?> targetClazz) {
        this.target = target;
        this.targetClazz = targetClazz;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Getter getter = method.getAnnotation(Getter.class);
        Setter setter = method.getAnnotation(Setter.class);
        if (getter != null && setter != null) {
            throw new IllegalArgumentException("method " + method.toGenericString() + " can not be @Getter and @Setter in the same time");
        }
        if (getter != null) {   // as getter
            if (args != null && args.length != 0) {
                throw new IllegalArgumentException("getter " + method.toGenericString() + " can not have arguments");
            }
            Field targetField = getField(targetClazz, getter.name());
            targetField.setAccessible(true);
            return targetField.get(target);
        }
        if (setter != null) {   // as setter
            if (args == null || args.length != 1) {
                throw new IllegalArgumentException("setter " + method.toGenericString() + " must have 1 argument");
            }
            Field targetField = getField(targetClazz, setter.name());
            targetField.setAccessible(true);
            targetField.set(target, args[0]);
            return null;
        }
        // as method
        Method targetMethod = getMethod(targetClazz, method.getName(), method.getParameterTypes());
        if (targetMethod == null) {
            throw new NoSuchMethodException();
        }
        targetMethod.setAccessible(true);
        return targetMethod.invoke(target, args);
    }

    private static Method getMethod(Class<?> objectClass, String methodName, Class... paramTypes) throws NoSuchMethodException, SecurityException {
        Method method;
        try {
            method = objectClass.getDeclaredMethod(methodName, paramTypes);
        } catch (NoSuchMethodException e) {
            if (null == objectClass.getSuperclass()) {
                throw new NoSuchMethodException();
            } else {
                method = getMethod(objectClass.getSuperclass(), methodName, paramTypes);
            }
        }
        return method;
    }

    private static Field getField(Class<?> objectClass, String fieldName) throws NoSuchFieldException, SecurityException {
        Field field;
        try {
            field = objectClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            if (null == objectClass.getSuperclass()) {
                throw new NoSuchFieldException();
            } else {
                field = getField(objectClass.getSuperclass(), fieldName);
            }
        }
        return field;
    }

}
