/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jvidia.reactcloan.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    private static final Logger log = LoggerFactory.getLogger(TestService.class);
    
    public static void main(String[] args) {
        TestService main = new TestService();
        try{
            System.out.println("1 main.test1");
            main.test1();
            System.out.println("done main.test1");

            System.out.println("2 main.test2");
            main.test2();
            System.out.println("Done main.tes2");       


            System.out.println("3 main.run");
            main.run();
            System.out.println("done main.run");

            TimeUnit.SECONDS.sleep(2);
            System.out.println("4 main.run2");
            main.run2();
            System.out.println("Done main.run2");
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void test1() {
        /*
        CompletableFuture.supplyAsync(() -> fetchDataFromAPI())
        .thenApply(data -> process(data))
        .thenAccept(result -> saveToDB(result));   
        // */
        CompletableFuture.supplyAsync(() -> "TestService")
            .thenApply(String::length)
            .thenAccept(result -> log.info(String.valueOf(result)));  
    }
    
    private void test2() {
        List<String> dataList = List.of("adam", "john", "alan", "jim");
        ForkJoinPool pool = new ForkJoinPool(4);
        pool.submit(() -> dataList.parallelStream().forEach(item -> process(item)));        
    }
    
    private void fetchDataFromAPI() {
        
    }
    
    private void process(String data) {
        log.info("process {}", data);
    }
    
    private void saveToDB(String result) {
        
    }
    
    public class LazyLoader {
        private volatile ExpensiveObject instance;
        public ExpensiveObject getInstance() {
            if (instance == null) {
                synchronized (this) {
                    if (instance == null) {
                        instance = new ExpensiveObject();
                    }
                }
            }
            return instance;
        }
    }    
    
    public static class ExpensiveObject {
        
    }
    
    private final int MAX_TOKENS = 100;
    private final ConcurrentHashMap<String, Semaphore> userLimits = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final RateLimiter limiter = new RateLimiter();

    public class RateLimiter {
        public RateLimiter() {
            scheduler.scheduleAtFixedRate(this::refillTokens, 1, 1, TimeUnit.SECONDS);
        }

        /*
        public V computeIfAbsent(K key, Function<? super K,? extends V> mappingFunction)
        If the specified key is not already associated with a value, attempts to compute its value using the given mapping function and 
            enters it into this map unless null. The entire method invocation is performed atomically, so the function is applied at most 
            once per key. Some attempted update operations on this map by other threads may be blocked while computation is in progress,
            so the computation should be short and simple, and must not attempt to update any other mappings of this map.       
        */
        public boolean allowRequest(String userId) {
            Semaphore semaphore = userLimits.computeIfAbsent(userId, k -> new Semaphore(MAX_TOKENS));
            return semaphore.tryAcquire();
        }

        /*
        public void replaceAll(BiFunction<? super K,? super V,? extends V> function)
        Description copied from interface: ConcurrentMap
        Replaces each entry's value with the result of invoking the given function on that entry until all entries have been 
            processed or the function throws an exception. Exceptions thrown by the function are relayed to the caller.
        */
        public void refillTokens() {
            userLimits.replaceAll((k, v) -> new Semaphore(MAX_TOKENS));
        }        
    }
    

    private void runCleanup() {
        scheduler.shutdown();
    }
    
    private void run() throws InterruptedException {        
        String userId = "user1";
        
        // First 100 requests should pass
        //*
        for (int i = 0; i < MAX_TOKENS; i++) {
            System.out.println("run successful=" + limiter.allowRequest(userId));
        }
        // */
        System.out.println("run 100 userId=" + userId + "-successful=" + runAllowRequest(userId, MAX_TOKENS));
        
        // 101st request should fail
        //System.out.println("run failed=" + limiter.allowRequest(userId));
        System.out.println("run 1 userId=" + userId + "-failed is successful?=" + runAllowRequest(userId, 1));

        // Wait for refill (1 second)
        TimeUnit.SECONDS.sleep(1);
        
        // Next request after refill should pass
        //System.out.println("run successful=" + limiter.allowRequest(userId));      
        System.out.println("run 1 after refill userId=" + userId + "-successful=" + runAllowRequest(userId, 1));
        
        runCleanup();
    }
    
    private void run2() throws InterruptedException {
        List<String> twoUserList = List.of("user1", "user2");
        // First 100 requests should pass for each user
        for (String userId: twoUserList) {
            System.out.println("run2 100 for each userId=" + userId + "-successful=" + runAllowRequest(userId, MAX_TOKENS));
        }
                
        // 101st request should fail for each user
        for (String userId: twoUserList) {
            System.out.println("run 101 for each userId=" + userId + "-failed is successful?=" + runAllowRequest(userId, 1));
        }

        // Wait for refill (1 second)
        TimeUnit.SECONDS.sleep(1);
        
        // Next request after refill should pass
        for (String userId: twoUserList) {
            System.out.println("run 101 after refill for each userId=" + userId + "-successful=" + runAllowRequest(userId, 1));
        }
        
        runCleanup();
    }    
    
    private boolean runAllowRequest(String userId, int maxToken) {
        boolean returnValue = true;
        
        for (int i = 0; i < maxToken; i++) {
            returnValue  = returnValue && limiter.allowRequest(userId);
        }
        
        return returnValue;
    }    
}
