package container;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

// DI
// 클래스를 등록하면
// 클래스의 객체를 만들고, 자동으로 주입해준다.
public class MyContainer {
    private final Map<Class<?>, Object> instances = new HashMap<>();

    // 클래스 등록
    public void register(Class<?>... classes) throws Exception {
        for(Class<?> clazz : classes) {
            // 리플렉션
            // new
            Object instance = clazz.getDeclaredConstructor().newInstance();
            instances.put(clazz, instance);
        }

        for (Object instance : instances.values()) {
            inject(instance);
        }
    }

    private void inject(Object instance) throws Exception {
        for(Field field : instance.getClass().getDeclaredFields()) {
            // @container.MyAutowired 가 붙은 필드를 골라 낸다.
            if(field.isAnnotationPresent(MyAutowired.class)) {
                // field 타입을 key 로 value 인 타겟 instance 를 뽑아서 dependency 에 넣어 준다.
                Object dependency = instances.get(field.getType());

                // private 무시하고 강제 접근
                field.setAccessible(true);

                // 필드에 [dependency = target 인스턴스(생성해 놓은 것)] 을 넣어 준다. == "주입"
                field.set(instance, dependency);
            }
        }
    }

    public <T> T get(Class<T> clazz) {
        return clazz.cast(instances.get(clazz));
    }


}
