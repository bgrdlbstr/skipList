package uk.co.bigredlobster.test;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final var m = new Main();
        m.doIt();
    }

    private void doIt() throws InterruptedException {
        var data = new Data<Integer>();
        var publisher1 = new Publisher(data);
        var publisher2 = new Publisher(data);

        var threadPoolExecutor = Executors.newFixedThreadPool(2);
        Future<?> submit = threadPoolExecutor.submit(publisher1);
        Future<?> submit1 = threadPoolExecutor.submit(publisher2);

        while (!submit.isDone() && !submit1.isDone()) {
            Thread.sleep(1000);
            System.out.println("dataSize = " + data.getSize());
        }

        System.out.println("END " + data.getData());
    }
}
