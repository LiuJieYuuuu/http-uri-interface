package com.httpuri.iagent.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Concurrent Lock Util
 */
public class LockUtil {

    /**
     * @see java.util.concurrent.locks.ReentrantLock
     */
    public final static Lock lock = new ReentrantLock();

    /**
     * @see java.util.concurrent.locks.ReentrantLock#lock
     */
    public static void getLock () {
        lock.lock();
    }

    /**
     * @see java.util.concurrent.locks.ReentrantLock#unlock
     */
    public static void unlock () {
        lock.unlock();
    }
}
