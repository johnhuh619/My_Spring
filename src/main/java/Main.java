import container.ComponentContainer;
import container.MyContainer;
import repository.Repository;
import service.Service;

import java.lang.reflect.Field;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("1. 리플렉션으로 필드에 주입하기");
        Service service = new Service();
        Field f = Service.class.getDeclaredField("repository");
        f.setAccessible(true);
        f.set(service, new Repository());
        service.test();

        System.out.println("\n2. @MyAutoWired + DI: 수동");
        MyContainer container = new MyContainer();
        container.register(Repository.class, Service.class);
        Service service2 = container.get(Service.class);
        service2.test();

        System.out.println("\n3. @container.MyComponent scan");
        ComponentContainer container1 = new ComponentContainer();
        container1.scan(Arrays.asList(Repository.class, Service.class));
        Service service3 = container1.get(Service.class);
        service3.test();
    }
}
