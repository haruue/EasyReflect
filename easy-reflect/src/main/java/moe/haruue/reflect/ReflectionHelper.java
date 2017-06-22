package moe.haruue.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Stardust on 2017/6/22.
 */

final class ReflectionHelper {

    static Field getAccessibleField(Class<?> objectClass, String fieldName) throws NoSuchFieldException, SecurityException {
        Field field;
        try {
            field = objectClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            if (null == objectClass.getSuperclass()) {
                throw new NoSuchFieldException();
            } else {
                field = getAccessibleField(objectClass.getSuperclass(), fieldName);
            }
        }
        field.setAccessible(true);
        return field;
    }


    static Method getMethod(Class<?> objectClass, String methodName, Class... paramTypes) throws NoSuchMethodException, SecurityException {
        Method method;
        try {
            method = objectClass.getDeclaredMethod(methodName, paramTypes);
        } catch (NoSuchMethodException e) {
            if (objectClass.getSuperclass() == null) {
                throw new NoSuchMethodException();
            } else {
                method = getMethod(objectClass.getSuperclass(), methodName, paramTypes);
            }
        }
        return method;
    }

    private ReflectionHelper() {

    }
}
