package uk.co.bigredlobster.test;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SleepyPublisher implements IPublisher {
    private final Data<Integer> data;
    private final long sleep;
    private final long howMany;
    private final List<Integer> dupes = new ArrayList<>();

    SleepyPublisher(Data<Integer> data) {
        this(data, 5, 1000);
    }

    SleepyPublisher(Data<Integer> data, long sleep, long howMany) {
        this.data = data;
        this.sleep = sleep;
        this.howMany = howMany;
    }

    @Override
    public List<Integer> getDupes() {
        return dupes;
    }

    @Override
    public void publish() {
        final var ints = new SecureRandom().ints();
        final var count = new AtomicInteger();

        try {
            ints.forEach(i -> {
                        try {
                            var add = data.add(i);
                            if(!add)
                                dupes.add(i);
                            count.getAndIncrement();
                            if (count.get() >= howMany)
                                throw new RuntimeException();
                            if (sleep > 0)
                                Thread.sleep(sleep);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            );
        } finally {
            System.out.println("count = " + count.get());
        }
    }

}
