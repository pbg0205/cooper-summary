package com.cooper.concurrencystockexample.optimistic.repository;

import com.cooper.concurrencystockexample.domain.Stock;

public interface StockRepositoryDsl {

    Stock findByIdWithOptimisticLock(Long id);

}
