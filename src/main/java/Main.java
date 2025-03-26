import java.lang.reflect.Field;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("1. 리플렉션으로 필드에 주입하기");
        Service service = new Service();
        Field f = Service.class.getDeclaredField("repository");
        f.setAccessible(true);
        f.set(service, new Repository());
        service.test();
    }
}
