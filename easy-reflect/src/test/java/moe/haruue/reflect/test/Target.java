package moe.haruue.reflect.test;

/**
 * Reflect target
 * @author Haruue Icymoon haruue@caoyue.com.cn
 */
public class Target {

    private String field = "this is a instance field";

    private static String staticField = "this is a static field";

    private String method(int length) {
        return field.substring(0, length);
    }

    private static String staticMethod(int length) {
        return staticField.substring(0, length);
    }

}
