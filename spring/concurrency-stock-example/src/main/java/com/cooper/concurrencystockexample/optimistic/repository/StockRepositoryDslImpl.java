package com.cooper.concurrencystockexample.optimistic.repository;

import com.cooper.concurrencystockexample.domain.Stock;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

import static com.cooper.concurrencystockexample.domain.QStock.stock;

@Repository
@RequiredArgsConstructor
public class StockRepositoryDslImpl implements StockRepositoryDsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public Stock findByIdWithOptimisticLock(Long id) {
        return queryFactory.selectFrom(stock)
                .setLockMode(LockModeType.OPTIMISTIC)
                .where(stock.id.eq(id))
                .fetchFirst();
    }

}
