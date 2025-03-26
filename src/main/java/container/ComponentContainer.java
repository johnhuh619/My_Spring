package container;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponentContainer {
    private final Map<Class<?>, Object> beans = new HashMap<Class<?>, Object>();

    public void scan(List<Class<?>> classes) throws Exception{
        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(MyComponent.class)) {
                Object instance = clazz.getDeclaredConstructor().newInstance();
                beans.put(clazz, instance);
            }
        }
        for (Object instance : beans.values()) {
            inject(instance);
        }
    }

    private void inject(Object bean) throws Exception{
        for(Field field : bean.getClass().getDeclaredFields()) {
            if(field.isAnnotationPresent(MyAutowired.class)) {
                Object dependency = beans.get(field.getType());
                field.setAccessible(true);
                field.set(bean, dependency);
            }
        }
    }
    public <T> T get(Class<T> clazz) {
        return clazz.cast(beans.get(clazz));
    }
}
