package com.google.code.inject.jaxrs.util;

import com.google.inject.matcher.Matcher;
import org.junit.Test;

import javax.ws.rs.Path;
import java.lang.reflect.Method;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MatchersTest {

    @Test
    public void testWithMethodWithNoAnnotation() throws Exception {
        assertFalse(matches("methodA"));
    }

    @Test
    public void testWithMethodWithPathAnnotation() throws Exception {
        assertTrue(matches("methodB"));
    }

    @Test
    public void testWithPrivateMethodWithPathAnnotation() throws Exception {
        assertFalse(matches("methodC"));
    }

    @Test
    public void testWithStaticMethodWithPathAnnotation() throws Exception {
        assertFalse(matches("methodD"));
    }

    @Test
    public void testWithInheritedMethodWithPathAnnotation() throws Exception {
        assertTrue(matches("methodE"));
    }

    private boolean matches(String methodName) throws Exception {
        Matcher<Method> matcher = Matchers.resourceMethod();
        Method method = TestClass.class.getDeclaredMethod(methodName);
        return matcher.matches(method);
    }

    private static class TestClass implements TestService {
        public void methodA() {
        }

        @Path("/some-service")
        public void methodB() {
        }

        @Path("/some-service")
        private void methodC() {
        }

        @Path("/some-service")
        public static void methodD() {
        }

        @Override
        public void methodE() {
        }
    }

    private interface TestService {
        @Path("/some-service")
        void methodE();
    }

}
