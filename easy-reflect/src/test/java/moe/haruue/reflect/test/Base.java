package moe.haruue.reflect.test;

/**
 * Created by Stardust on 2017/6/22.
 */

public class Base {

    private String superField = "this is super's instance field";

    private static String superStaticField = "this is super's static field";

    private String superMethod(int start) {
        return superField.substring(start);
    }

    private static String superStaticMethod(int start) {
        return superStaticField.substring(start);
    }

}
