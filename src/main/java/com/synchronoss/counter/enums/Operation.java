package com.synchronoss.counter.enums;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Operations supported by both server and client.
 */
public enum Operation {
    /**
     * Increment
     */
    I {
        public AtomicInteger apply(AtomicInteger counter) {
            counter.incrementAndGet();
            return counter;
        }
    },
    /**
     * Decrement
     */
    D {
        public AtomicInteger apply(AtomicInteger counter) {
            counter.decrementAndGet();
            return counter;
        }
    },
    /**
     * Evaluate
     */
    E {
        public AtomicInteger apply(AtomicInteger counter) {
            return counter;
        }
    },
    /**
     * Reset
     */
    R {
        public AtomicInteger apply(AtomicInteger counter) {
            counter.set(0);
            return counter;
        }
    };

    abstract public AtomicInteger apply(AtomicInteger counter);
}
