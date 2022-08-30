package com.cooper.concurrencystockexample.optimistic.facade;

import com.cooper.concurrencystockexample.optimistic.service.OptimisticLockStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptimisticLockStockFacade {

    private final OptimisticLockStockService optimisticLockStockService;

    public void decreaseByJpa(Long id, Long quantity) throws InterruptedException {
        while (true) {
            try {
                optimisticLockStockService.decreaseByJpa(id, quantity);

                break;
            } catch (Exception e) {
                Thread.sleep(50);
            }
        }
    }

    public void decreaseByDsl(Long id, Long quantity) throws InterruptedException {
        while (true) {
            try {
                optimisticLockStockService.decreaseByDsl(id, quantity);
                break;
            } catch (Exception e) {
                Thread.sleep(50);
            }
        }
    }

}
