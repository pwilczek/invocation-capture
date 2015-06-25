package name.wilu.excel;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class MethodCall<T> {
    //
    private final String SET_PREFIX = "set";
    private final String GET_PREFIX = "get";
    //
    private final T t;
    //
    private Method set;
    private Method get;

    private MethodCall(T t) {
        this.t = t;
    }

    <T> T apply(T target, Object... values) {
        try {
            set.invoke(target, values);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException();
        }
        return target;
    }

    Object value(T source) {
        try {
            return get.invoke(source, null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException();
        }
    }

    T target() {
        return t;
    }

    /**
     * Factory method
     */
    static <T> MethodCall<T> on(final Class<T> clazz) {
        //
        ProxyFactory factory = new ProxyFactory();
        factory.setSuperclass(clazz);
        Class proxy = factory.createClass();
        //
        final MethodCall<T> result = createProxy(proxy);
        //
        ((ProxyObject) result.t).setHandler(new MethodHandler() {
            @Override
            public Object invoke(Object self, Method overridden, Method forward, Object[] args) throws Throwable {
                result.get = overridden;
                String setter = overridden.getName().replaceAll(result.GET_PREFIX, result.SET_PREFIX);
                for (Method method : clazz.getMethods()) {
                    if (method.getName().equals(setter)) {
                        result.set = method;
                        break;
                    }
                }
                if (result.set == null) throw new RuntimeException();
                return forward.invoke(self, args);
            }
        });
        return result;
    }

    private static <T> MethodCall createProxy(Class<T> clazz) {
        try {
            return new MethodCall(clazz.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException();
        }
    }
}
