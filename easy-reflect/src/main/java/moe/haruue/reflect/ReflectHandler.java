package moe.haruue.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

/**
 * Real handle which does reflect behind proxy
 *
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
            throw new IllegalArgumentException("method " + method.toGenericString() + " can not be @Getter and @Setter at the same time");
        }
        if (getter != null) {
            return getValue(getter, method, args);
        }
        if (setter != null) {
            setValue(setter, method, args);
            return null;
        }
        return invokeMethod(method, args);
    }

    private Object getValue(Getter getter, Method method, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        requireArgumentCount(method, args, 0);
        Field targetField = ReflectionHelper.getAccessibleField(targetClazz, getter.name());
        return targetField.get(target);
    }

    private void setValue(Setter setter, Method method, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        requireArgumentCount(method, args, 1);
        Field targetField = ReflectionHelper.getAccessibleField(targetClazz, setter.name());
        targetField.setAccessible(true);
        targetField.set(target, args[0]);
    }

    private Object invokeMethod(Method method, Object[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method targetMethod = ReflectionHelper.getMethod(targetClazz, method.getName(), method.getParameterTypes());
        if (targetMethod == null) {
            throw new NoSuchMethodException();
        }
        targetMethod.setAccessible(true);
        return targetMethod.invoke(target, args);
    }

    private static void requireArgumentCount(Method method, Object[] args, int count) {
        if (count == 0) {
            if (args != null && args.length != count) {
                throw new IllegalArgumentException(String.format(Locale.getDefault(), "%s should have no argument", method.toGenericString()));
            }
            return;
        }
        if (args == null || args.length != count) {
            throw new IllegalArgumentException(String.format(Locale.getDefault(), "%s should have %d argument(s)", method.toGenericString(), count));
        }
    }


}
