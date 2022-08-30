package com.cooper.concurrencystockexample.optimistic.facade;

import com.cooper.concurrencystockexample.domain.Stock;
import com.cooper.concurrencystockexample.optimistic.repository.StockRepositoryJpa;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OptimisticLockStockFacadeTest {

    @Autowired
    private OptimisticLockStockFacade optimisticLockStockFacade;

    @Autowired
    private StockRepositoryJpa stockRepositoryJpa;

    @BeforeEach
    public void insert() {
        Stock stock = new Stock(1L, 100L);
        stockRepositoryJpa.saveAndFlush(stock);
    }

    @AfterEach
    public void delete() {
        stockRepositoryJpa.deleteAll();
    }

    @Test
    public void 동시에_100개의요청_jpa() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    optimisticLockStockFacade.decreaseByJpa(1L, 1L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Stock stock = stockRepositoryJpa.findById(1L).orElseThrow();

        // 100 - (100 * 1) = 0
        assertEquals(0, stock.getQuantity());
    }

    @Test
    public void 동시에_100개의요청_dsl() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    optimisticLockStockFacade.decreaseByDsl(1L, 1L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Stock stock = stockRepositoryJpa.findById(1L).orElseThrow();

        // 100 - (100 * 1) = 0
        assertEquals(0, stock.getQuantity());
    }
}