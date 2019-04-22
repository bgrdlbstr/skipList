package uk.co.bigredlobster.test;

import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListSet;

class Data<T extends Comparable> {
    private final ConcurrentSkipListSet<T> skipList;

    Data() {
        this.skipList = new ConcurrentSkipListSet<>();
    }

    boolean add(T t) {
        return skipList.add(t);
    }

    NavigableSet getData() {
        return skipList;
    }

    int getSize() {
        return skipList.size();
    }
}
