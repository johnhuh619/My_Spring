package service;

import container.MyAutowired;
import container.MyComponent;
import repository.Repository;

@MyComponent
public class Service {
    @MyAutowired
    private Repository repository;

    public void test() {
        repository.save();
    }
}
