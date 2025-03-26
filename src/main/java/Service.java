public class Service {
    @MyAutowired
    private Repository repository;

    public void test() {
        repository.save();
    }
}
