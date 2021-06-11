package com.example.controller.rest;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author caohaifengx@163.com 2021-01-04 21:52
 */
@RestController
@Slf4j
public class TestRedissonController {

    @Autowired
    private RedissonClient redisson;

    @RequestMapping("/get1")
    public String tet() throws InterruptedException {
        log.info("开始底肥地点f：{}", Thread.currentThread().getId());
        RLock lock = redisson.getLock("anyLock");

        // 业务逻辑 --- 双重判断
        // 1. 缓存业务数据 某定时任务是否已执行
        // 2. 未执行过则抢锁 获得锁的线程执行业务逻辑 并 更新业务缓存（更新前先判断是否已执行， 双重判断）
        // 3. 未抢到锁的线程直接下一步

        lock.lock(5, TimeUnit.SECONDS);

        boolean res = lock.tryLock(3, 60, TimeUnit.SECONDS);

        if (res) {
            try {
                log.info("================{} 获得锁", Thread.currentThread().getId());
                Thread.sleep(10000);
            } finally {
                log.info("================{} 释放锁", Thread.currentThread().getId());

                lock.unlock();
            }
        }else {
            log.info("================{} 未获得锁", Thread.currentThread().getId());

        }

        return "jjjjjjjj";
    }


    @RequestMapping("/get2")
    public String tet2() throws InterruptedException {
        log.info("开始底肥地点f2", Thread.currentThread().getId());
        RLock lock = redisson.getLock("anyLock");
        boolean res = lock.tryLock(20, 60, TimeUnit.SECONDS);

        if (res) {
            try {
                log.info("================{} 获得锁", Thread.currentThread().getId());
                Thread.sleep(10000);
            } finally {
                log.info("================{} 释放锁", Thread.currentThread().getId());
                lock.unlock();
            }
        }else {
            log.info("================{} 未获得锁", Thread.currentThread().getId());

        }

        return "jjjjjjjj";
    }

}
