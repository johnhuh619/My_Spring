import java.lang.reflect.Field;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("1. 리플렉션으로 필드에 주입하기");
        Service service = new Service();
        Field f = Service.class.getDeclaredField("repository");
        f.setAccessible(true);
        f.set(service, new Repository());
        service.test();

        System.out.println("\n2. @MyAutoWired + DI");
        MyContainer container = new MyContainer();
        container.register(Repository.class, Service.class);
        Service service2 = container.get(Service.class);
        service2.test();

        System.out.println("\nservice 와 service2 는 같은 인스턴스인가요? " + (service == service2)); // false

    }
}
