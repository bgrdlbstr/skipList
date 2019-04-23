package uk.co.bigredlobster.test;

import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TestThroughput {
    @Test
    public void testIts200k() throws InterruptedException {
        var data = new Data<Integer>();
        var publisher1 = new Publisher(data, 0, 100_000);
        var publisher2 = new Publisher(data, 0, 100_000);

        var threadPoolExecutor = Executors.newFixedThreadPool(2);
        Future<?> submit = threadPoolExecutor.submit(publisher1);
        Future<?> submit1 = threadPoolExecutor.submit(publisher2);

        while (!submit.isDone() && !submit1.isDone()) {
            Thread.sleep(1000);
            System.out.println("dataSize = " + data.getSize());
        }

        int expected = 200_000 - publisher1.getDupes().size() - publisher2.getDupes().size();
        assertThat(data.getSize(), is(expected));
    }
}
