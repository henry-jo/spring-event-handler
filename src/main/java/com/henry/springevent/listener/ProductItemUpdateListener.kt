package com.henry.springevent.listener

import com.henry.springevent.dto.ProductItem
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
class ProductItemUpdateListener(
    private val finder: FindLatestManager<ProductItem>
) {
    val log = LoggerFactory.getLogger(ProductItemUpdateListener::class.java)!!

    @TransactionalEventListener(fallbackExecution = true)
    fun listenTranAfterUpdateEvent(event: ProductItemUpdateEvent) {
        val old = event.old
        val new = event.new

        if (old.amount != new.amount) {
            log.info("old amount(${old.amount}) != new amount(${new.amount})")
        }
    }
}