package moe.haruue.reflect.test;

import moe.haruue.reflect.EasyReflect;
import moe.haruue.reflect.Getter;
import moe.haruue.reflect.InjectMethod;
import moe.haruue.reflect.Setter;

import static org.junit.Assert.assertEquals;

/**
 * @author Haruue Icymoon haruue@caoyue.com.cn
 */
public class Test {

    interface TargetInterface {
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

        @Getter(name = "noSuchField", searchSuper = true)
        void getInexistentField() throws NoSuchFieldException;

        @Getter(name = "superField", searchSuper = true)
        String getSuperField();

        @Setter(name = "superField", searchSuper = true)
        void setSuperField(String s);

        @Getter(name = "superStaticField", searchSuper = true)
        String getSuperStaticField();

        @Setter(name = "superStaticField", searchSuper = true)
        void setSuperStaticField(String s);

        @Getter(name = "superField")
        String getSuperFieldWithoutSearchingSuper() throws NoSuchFieldException;

        @InjectMethod(searchSuper = true)
        String superMethod(int start);

        @InjectMethod(searchSuper = true)
        String superStaticMethod(int start);

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

    @org.junit.Test(expected = NoSuchFieldException.class)
    public void fieldOfTargetClassNotFound() throws Throwable {
        TargetInterface proxy = EasyReflect.from(new Target(), TargetInterface.class);
        proxy.getInexistentField();
    }

    @org.junit.Test
    public void getFieldOfSuperClass() throws Throwable {
        TargetInterface proxy = EasyReflect.from(new Target(), TargetInterface.class);
        assertEquals("this is super's instance field", proxy.getSuperField());
    }

    @org.junit.Test
    public void setFieldOfSuperClass() throws Throwable {
        TargetInterface proxy = EasyReflect.from(new Target(), TargetInterface.class);
        proxy.setSuperField("reflected");
        assertEquals("reflected", proxy.getSuperField());
    }

    @org.junit.Test
    public void getStaticFieldOfSuperClass() throws Throwable {
        TargetInterface proxy = EasyReflect.from(null, Target.class, TargetInterface.class);
        assertEquals("this is super's static field", proxy.getSuperStaticField());
    }

    @org.junit.Test
    public void setStaticFieldOfSuperClass() throws Throwable {
        TargetInterface proxy = EasyReflect.from(null, Target.class, TargetInterface.class);
        String oldStaticField = proxy.getSuperStaticField();
        proxy.setSuperStaticField("reflected");
        assertEquals("reflected", proxy.getSuperStaticField());
        proxy.setSuperStaticField(oldStaticField);
        assertEquals(oldStaticField, proxy.getSuperStaticField());
    }

    @org.junit.Test(expected = NoSuchFieldException.class)
    public void getSuperFieldWithoutSearchingSuper() throws Throwable {
        TargetInterface proxy = EasyReflect.from(new Target(), TargetInterface.class);
        proxy.getSuperFieldWithoutSearchingSuper();
    }


    @org.junit.Test
    public void invokeMethodOfSuperClass() throws Throwable {
        TargetInterface proxy = EasyReflect.from(new Target(), TargetInterface.class);
        assertEquals("is super's instance field", proxy.superMethod(5));
    }


    @org.junit.Test
    public void invokeStaticMethodOfSuperClass() throws Throwable {
        TargetInterface proxy = EasyReflect.from(null, Target.class, TargetInterface.class);
        assertEquals("is super's static field", proxy.superStaticMethod(5));
    }


}
