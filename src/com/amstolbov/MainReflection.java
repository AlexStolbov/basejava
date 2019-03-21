package com.amstolbov;

import com.amstolbov.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException,
                                                    NoSuchMethodException,
                                                    InvocationTargetException {
        Resume r = new Resume("Tom");
        Class classResume = r.getClass();
        Field field = classResume.getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");
        System.out.println(r.getUuid());

        // TODO : invoke r.toString via reflection
        Method method = classResume.getMethod("toString");
        String rToString = (String) method.invoke(r);
        System.out.println(rToString);
    }
}
