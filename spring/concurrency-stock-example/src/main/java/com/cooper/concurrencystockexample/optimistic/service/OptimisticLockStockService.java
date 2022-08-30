package com.cooper.concurrencystockexample.optimistic.service;

import com.cooper.concurrencystockexample.domain.Stock;
import com.cooper.concurrencystockexample.optimistic.repository.StockRepositoryDsl;
import com.cooper.concurrencystockexample.optimistic.repository.StockRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class OptimisticLockStockService {

    private final StockRepositoryJpa stockRepositoryJpa;

    private final StockRepositoryDsl stockRepositoryDsl;

    public void decreaseByJpa(Long id, Long quantity) {
        Stock stock = stockRepositoryJpa.findByIdWithOptimisticLock(id);
        stock.decrease(quantity);
        stockRepositoryJpa.saveAndFlush(stock);
    }

    public void decreaseByDsl(Long id, Long quantity) {
        Stock stock = stockRepositoryDsl.findByIdWithOptimisticLock(id);
        stock.decrease(quantity);
    }

}
