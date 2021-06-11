package com.dev.Pool;

import com.dev.Pool.Coupang.CoupangBehavior;
import com.dev.Pool.Gmarket.GmarketBehavior;
import com.dev.Pool._11_st.StreetBehavior;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public final class Pool extends Thread {
    private final static ExecutorService POOL = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final static ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) POOL;
    private final static CoupangBehavior COUPANG_BEHAVIOR = new CoupangBehavior();
    private final static GmarketBehavior GMARKET_BEHAVIOR = new GmarketBehavior();
    private final static StreetBehavior STREET_BEHAVIOR = new StreetBehavior();
    private final static List<Behavior> BEHAVIORS = Arrays.asList(COUPANG_BEHAVIOR, GMARKET_BEHAVIOR, STREET_BEHAVIOR);
    private final static String[] SEARCH_SET = new String[2];
    private final static Pool POOL_OBJECT = new Pool();
    private final static String LINE = "+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+";

    private final static Runnable COUPANG_RUNNABLE = () -> {
        Thread.currentThread().setName("executable CoupangBehavior pipe");
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY - 6);
        threadPrint("COUPANG_RUNNABLE");

        COUPANG_BEHAVIOR.call(SEARCH_SET);
    };

    private final static Runnable GMARKET_RUNNABLE = () -> {
        Thread.currentThread().setName("executable GmarketBehavior pipe");
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        threadPrint("GMARKET_RUNNABLE");

        GMARKET_BEHAVIOR.call(SEARCH_SET);
    };

    private final static Runnable STREET_RUNNABLE = () -> {
        Thread.currentThread().setName("executable StreetBehavior pipe");
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        threadPrint("STREET_RUNNABLE");

        STREET_BEHAVIOR.call(SEARCH_SET);
    };

    private Pool() { }

    public static Pool getInstance() { return POOL_OBJECT; }

    public List<Behavior> getBehavior() {
        return BEHAVIORS;
    }

    public ExecutorService getExecutorService() {
        return POOL;
    }

    public ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }

    public String[] getsearchSet() {
        return new String[] {SEARCH_SET[0].replace("+", " "), SEARCH_SET[1]};
    }

    public void pooling(String[] set) {
        SEARCH_SET[0] = set[0];
        SEARCH_SET[1] = set[1];

        System.out.println(LINE);

        System.out.printf("Search: %s, option: %s%n", SEARCH_SET[0].replace("+", " "), SEARCH_SET[1]);

        System.out.println(LINE + "\nData crawling multithread executed\n" + LINE);

        Future<?> GMARKET_RUNNABLE_future = POOL.submit(GMARKET_RUNNABLE);
        Future<?> ST_RUNNABLE_future = POOL.submit(STREET_RUNNABLE);
        Future<?> COUPANG_RUNNABLE_future = POOL.submit(COUPANG_RUNNABLE);

        while (true) {
            if (GMARKET_RUNNABLE_future.isDone() && ST_RUNNABLE_future.isDone() && COUPANG_RUNNABLE_future.isDone()) {
                for (Behavior behavior: BEHAVIORS) {
                    System.out.println(LINE + LINE + LINE);
                    behavior.print();
                }

                System.out.println("Data crawling complete\n" + LINE);

                break;
            }
        }
    }

    public static void threadPrint(String runnableName) {
        System.out.println("ThreadPool activated -> " + runnableName + " executed INFO\n[ThreadExecutorService PoolSize]: "
                + threadPoolExecutor.getPoolSize() + "\n[ThreadName]: " + Thread.currentThread().getName()
                + "\n[ThreadPriority]: " + Thread.currentThread().getPriority() + "\n" + LINE);
    }
}
