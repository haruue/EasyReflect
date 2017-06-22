package moe.haruue.reflect.test;

import moe.haruue.reflect.EasyReflect;
import moe.haruue.reflect.Getter;
import moe.haruue.reflect.Setter;

import static org.junit.Assert.assertEquals;

/**
 * @author Haruue Icymoon haruue@caoyue.com.cn
 */
public class Test {

    public interface TargetInterface {
        String method(int length);

        String staticMethod(int length);

        @Getter(name = "field")
        String getField();

        @Setter(name = "field")
        void setField(String s);

        @Getter(name = "staticField")
        String getStaticField();

        @Setter(name = "staticField")
        void setStaticField(String s);
    }

    @org.junit.Test
    public void invokeWithInstance() throws Throwable {
        Target target = new Target();
        TargetInterface proxy = EasyReflect.from(target, TargetInterface.class);
        assertEquals("this is a instance field", proxy.getField());
        proxy.setField("reflected");
        assertEquals("reflected", proxy.getField());
        assertEquals("reflected".substring(0, 3), proxy.method(3));
    }

    @org.junit.Test
    public void invokeWithoutInstance() throws Throwable {
        TargetInterface proxy = EasyReflect.from(null, Class.forName("moe.haruue.reflect.test.Target"), TargetInterface.class);
        assertEquals("this is a static field", proxy.getStaticField());
        proxy.setStaticField("reflected");
        assertEquals("reflected", proxy.getStaticField());
        assertEquals("reflected".substring(0, 3), proxy.staticMethod(3));
    }

}
