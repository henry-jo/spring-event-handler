package com.henry.springevent.listener

import com.henry.springevent.dto.ProductItem
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import java.util.Objects
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Component
class ProductItemUpdateListener(
    private val finder: FindLatestManager<ProductItem>
) {
    val log = LoggerFactory.getLogger(ProductItemUpdateListener::class.java)!!

    protected object ProductItemHolder {

        private val holder = ThreadLocal<ProductItem?>()

        fun get() = holder.get()
        fun set(productItem: ProductItem?) = holder.set(productItem)

        fun clear() = holder.remove()
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun listenPreUpdateEvent(event: ProductItem) {
        try {
            val oldApp = finder.findLast(event)
            ProductItemHolder.set(oldApp)
        } catch (e: RuntimeException) {
            log.error("LoanAppUpdateEventManager: $e\n{}", e)
        } catch (e: Exception) {
            log.error("LoanAppUpdateEventManager: $e\n{}", e)
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun listenPostUpdateEvent(event: ProductItem) {
        try {
            val oldProductItem = ProductItemHolder.get()
            if (Objects.isNull(oldProductItem)) return

            log.info("old product amount : ${oldProductItem?.amount}")
            log.info("updated product amount : ${event.amount}")

            ProductItemHolder.clear()
        } catch (e: RuntimeException) {
            log.error("LoanAppUpdateEventManager: $e\n{}", e)
        } catch (e: Exception) {
            log.error("LoanAppUpdateEventManager: $e\n{}", e)
        }
    }
}

/**
 * 현재 영속성에 저장된 오브젝트의 이전 snapshot 을 리턴
 */
interface FindLatestManager<T> {
    fun findLast(ob: T): T?
}

@Component
class FindLatestManagerProductItem : FindLatestManager<ProductItem> {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    /**
     * transaction 을 사용하지 않으므로 hibernate session 과 persistence context 가 존재하지 않기 때문에
     * 실제 db 에서 직접 조회한다.
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    override fun findLast(ob: ProductItem): ProductItem? {
        if (Objects.isNull(ob.id)) return null

        return entityManager.find(ProductItem::class.java, ob.id)
    }
}