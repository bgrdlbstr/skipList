package uk.co.bigredlobster.test;

import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicInteger;

class Publisher implements Runnable {
    private final Data<Integer> data;

    Publisher(Data<Integer> data) {
        this.data = data;
    }

    @Override
    public void run() {
        final var ints = new SecureRandom().ints();
        AtomicInteger count = new AtomicInteger();

        try {
            ints.forEach(i -> {
                        try {
                            data.add(i);
                            count.getAndIncrement();
                            if (count.get() > 1000)
                                throw new RuntimeException();
                            Thread.sleep(5);
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
