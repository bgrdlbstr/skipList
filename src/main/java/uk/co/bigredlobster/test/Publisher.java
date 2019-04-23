package uk.co.bigredlobster.test;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

class Publisher implements Runnable {
    private final Data<Integer> data;
    private final long sleep;
    private final long howMany;
    private final List<Integer> dupes = new ArrayList<>();

    Publisher(Data<Integer> data) {
        this(data, 5, 1000);
    }

    Publisher(Data<Integer> data, long sleep, long howMany) {
        this.data = data;
        this.sleep = sleep;
        this.howMany = howMany;
    }

    List<Integer> getDupes() {
        return dupes;
    }

    @Override
    public void run() {
        final var ints = new SecureRandom().ints();
        AtomicInteger count = new AtomicInteger();

        try {
            ints.forEach(i -> {
                        try {
                            boolean add = data.add(i);
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
