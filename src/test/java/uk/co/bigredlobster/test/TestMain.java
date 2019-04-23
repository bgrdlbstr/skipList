package uk.co.bigredlobster.test;

import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TestMain {
    @Test
    public void testIts2k() throws InterruptedException {
        var data = new Data<Integer>();
        var publisher1 = new Publisher(data, 20, 1000);
        var publisher2 = new Publisher(data, 20, 1000);

        var threadPoolExecutor = Executors.newFixedThreadPool(2);
        Future<?> submit = threadPoolExecutor.submit(publisher1);
        Future<?> submit1 = threadPoolExecutor.submit(publisher2);

        while (!submit.isDone() && !submit1.isDone()) {
            Thread.sleep(1000);
            System.out.println("dataSize = " + data.getSize());
        }

        System.out.println("END " + data.getData());

        int expected = 2_000 - publisher1.getDupes().size() - publisher2.getDupes().size();
        assertThat(data.getSize(), is(expected));
    }
}
