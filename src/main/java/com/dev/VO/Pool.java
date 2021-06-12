package com.dev.VO;

import com.dev.VO.Coupang.CoupangBehavior;
import com.dev.VO.Gmarket.GmarketBehavior;
import com.dev.VO._11_st.StreetBehavior;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public final class Pool extends Thread {

    // +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    // --- com.dev.VO.Pool ---
    // ThreadPool 생성, Behavior 객체의 크롤링 실행 및 쓰레드 생성의 관제탑
    // ProductDAO 객체의 데이터 로드 쓰레드, ProductDAO 객체의 데이터 입력 쓰레드의 작업 실행 및 처리 요청을 받아옴
    //
    // com.dev.VO.Behavior::call -> coupangBehavior, gmarketBehavior, streetBehavior 크롤링 쓰레드
    // com.dev.DAO.ProductDAO::load, com.dev.DAO.ProductDAO::insert -> 데이터베이스 쓰레드
    //
    // --- Selenium 및 Jsoup 실행 Runnable Interface ---
    // COUPANG_RUNNABLE, GMARKET_RUNNABLE, STREET_RUNNABLE
    // --- Selenium 및 Jsoup 실행 Callable Interface ---
    // Runnable Interface로 대체됨
    // +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

    private final static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final static ThreadPoolExecutor THREAD_POOL_EXECUTOR = (ThreadPoolExecutor) EXECUTOR_SERVICE;
    private final static CoupangBehavior COUPANG_BEHAVIOR = new CoupangBehavior();
    private final static GmarketBehavior GMARKET_BEHAVIOR = new GmarketBehavior();
    private final static StreetBehavior STREET_BEHAVIOR = new StreetBehavior();
    private final static List<Behavior> BEHAVIORS = Arrays.asList(COUPANG_BEHAVIOR, GMARKET_BEHAVIOR, STREET_BEHAVIOR);
    private final static String[] SEARCH_SET = new String[2];
    private final static Pool POOL = new Pool();
    final static String LINE = "+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+";

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

    public static Pool getInstance() { return POOL; }

    public List<Behavior> getBehavior() {
        return BEHAVIORS;
    }

    public ExecutorService getExecutorService() {
        return EXECUTOR_SERVICE;
    }

    public ThreadPoolExecutor getThreadPoolExecutor() {
        return THREAD_POOL_EXECUTOR;
    }

    public String[] getsearchSet() {
        return new String[] {SEARCH_SET[0].replace("+", " "), SEARCH_SET[1]};
    }

    // 크롤링 실행
    public void pooling(String[] set) {
        SEARCH_SET[0] = set[0];
        SEARCH_SET[1] = set[1];

        System.out.println(LINE);

        System.out.printf("Search: %s, option: %s%n", SEARCH_SET[0].replace("+", " "), SEARCH_SET[1]);

        System.out.println(LINE + "\nData crawling multithread executed\n" + LINE);

        Future<?> GMARKET_RUNNABLE_future = EXECUTOR_SERVICE.submit(GMARKET_RUNNABLE);
        Future<?> ST_RUNNABLE_future = EXECUTOR_SERVICE.submit(STREET_RUNNABLE);
        Future<?> COUPANG_RUNNABLE_future = EXECUTOR_SERVICE.submit(COUPANG_RUNNABLE);

        while (true) {
            if (GMARKET_RUNNABLE_future.isDone() && ST_RUNNABLE_future.isDone() && COUPANG_RUNNABLE_future.isDone()) {
                // 콘솔에 call 경과 시간 및 결과물 출력
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
                + THREAD_POOL_EXECUTOR.getPoolSize() + "\n[ThreadName]: " + Thread.currentThread().getName()
                + "\n[ThreadPriority]: " + Thread.currentThread().getPriority() + "\n" + LINE);
    }
}
