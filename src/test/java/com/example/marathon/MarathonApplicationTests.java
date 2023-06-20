//package com.example.marathon;
//
//import com.example.marathon.dataobject.Events;
//import com.example.marathon.mapper.EventsMapper;
//import com.example.marathon.model.RedisData;
//import com.example.marathon.util.IdGenerator;
//import com.example.marathon.util.RedisUtil;
//import jakarta.annotation.Resource;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDateTime;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.atomic.AtomicInteger;
//
//@SpringBootTest
//class MarathonApplicationTests {
//
////    @Resource
////    private RedisUtil redisUtil;
//
////    @Resource
////    private EventsMapper eventsMapper;
//
//    @Resource
//    private IdGenerator idGenerator;
//
//    private ExecutorService es = Executors.newFixedThreadPool(100);
//
//    @Test
//    void testIdWorker() throws InterruptedException {
//        CountDownLatch latch = new CountDownLatch(300);
//        AtomicInteger integer = new AtomicInteger(0);
//        Runnable r = ()->{
//            for (int i = 0; i < 100; i++) {
//                long id = idGenerator.nextId("test");
//                System.out.println("id = " + id);
//                integer.getAndIncrement();
//            }
//            latch.countDown();
//        };
//        long begin = System.currentTimeMillis();
//        for (int i = 0; i < 300; i++) {
//            es.execute(r);
//        }
//
//        latch.await();
//        long end = System.currentTimeMillis();
//        System.out.println("=====cost:" + (end - begin));
//        System.out.println("++++++" + integer);
//    }
//
//
////    @Test
////    void contextLoads() {
////        Events event = eventsMapper.getSearchedEvent("ExampleEvent");
////        RedisData<Events> data = new RedisData<>();
////        data.setData(event);
////        data.setExpireTime(LocalDateTime.now().plusSeconds(30));
////        redisUtil.setToCache("hot:ExampleEvent", data);
////    }
//}
