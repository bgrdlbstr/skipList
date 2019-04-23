package uk.co.bigredlobster.test;

import java.util.List;

class Publisher implements Runnable, IPublisher {
    private final IPublisher delegate;

    Publisher(IPublisher publisher) {
        this.delegate = publisher;
    }

    public List<Integer> getDupes() {
        return delegate.getDupes();
    }

    @Override
    public void publish() {
        delegate.publish();
    }

    @Override
    public void run() {
        publish();
    }
}
