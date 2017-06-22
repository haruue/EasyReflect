package moe.haruue.reflect;

import com.sun.istack.internal.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Stardust on 2017/6/22.
 */

final class ReflectionHelper {

    static Field getAccessibleField(Class<?> objectClass, String fieldName, boolean searchSuperClass) throws NoSuchFieldException, SecurityException {
        Field field;
        try {
            field = objectClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            if (!searchSuperClass || objectClass.getSuperclass() == null) {
                throw e;
            } else {
                field = getAccessibleField(objectClass.getSuperclass(), fieldName, true);
            }
        }
        field.setAccessible(true);
        return field;
    }


    static Method getMethod(Class<?> objectClass, String methodName, Class[] paramTypes, boolean searchSuperClass) throws NoSuchMethodException, SecurityException {
        Method method;
        try {
            method = objectClass.getDeclaredMethod(methodName, paramTypes);
        } catch (NoSuchMethodException e) {
            if (!searchSuperClass || objectClass.getSuperclass() == null) {
                throw e;
            } else {
                method = getMethod(objectClass.getSuperclass(), methodName, paramTypes, true);
            }
        }
        return method;
    }

    private ReflectionHelper() {

    }
}
