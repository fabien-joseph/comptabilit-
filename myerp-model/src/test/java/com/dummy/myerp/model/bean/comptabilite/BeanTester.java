package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Assert;
import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

class BeanTester {
    private static final Map<Class, Object> TEST_VALUES = new LinkedHashMap<>();

    static {
        TEST_VALUES.put(int.class, 123456);
        TEST_VALUES.put(Integer.class, 123456);
        TEST_VALUES.put(BigDecimal.class, BigDecimal.valueOf(51515));
        TEST_VALUES.put(String.class, "Content");
        TEST_VALUES.put(Date.class, new Date(555555555555L));
        TEST_VALUES.put(JournalComptable.class, new JournalComptable("pCode", "Libelle"));
        TEST_VALUES.put(CompteComptable.class, new CompteComptable(5555, "Libelle"));
    }

    static void testBean(Class beanClass) {
        try {
            testBean(beanClass, beanClass.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> void testBean(Class<T> beanClass, T beanInstance) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(beanClass, Object.class);
            for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
                Method setMethod = pd.getWriteMethod();
                Method getMethod = pd.getReadMethod();
                if (setMethod == null || getMethod == null) {
                    continue;
                }
                setMethod.setAccessible(true);
                getMethod.setAccessible(true);

                Object expectedValue = getTestValue(pd.getPropertyType(), getMethod.invoke(beanInstance));

                setMethod.invoke(beanInstance, expectedValue);
                Object value = getMethod.invoke(beanInstance);
                Assert.assertEquals(beanClass.getName() + "." + pd.getName() + " get/set failed", expectedValue,
                        value);
            }

            Method toStringMethod = beanClass.getDeclaredMethod("toString");
            String toString = (String) toStringMethod.invoke(beanInstance);
            Assert.assertNotNull(toString);
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object getTestValue(Class type, Object currentValue) {
        if (type.isAssignableFrom(boolean.class) || type.isAssignableFrom(Boolean.class)) {
            return !Objects.equals(currentValue, true);
        }

        for (Map.Entry<Class, Object> e : TEST_VALUES.entrySet()) {
            if (type.isAssignableFrom(e.getKey())) {
                Object value = e.getValue();
                Assert.assertNotEquals(value, currentValue);
                return value;
            }
        }
        throw new UnsupportedOperationException("No test value for: " + type);
    }
}