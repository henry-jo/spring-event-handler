package com.henry.springevent.listener

import com.henry.springevent.config.BeanConfig
import com.henry.springevent.dto.ProductItem
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.Objects
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.PostUpdate
import javax.persistence.PreUpdate

class ProductItemListener {

    @PreUpdate
    fun onPreUpdate(productItem: ProductItem) {
        val publisher = BeanConfig.getBean(ProductItemEventPublisher::class.java)
        publisher.handlePreUpdateEvent(productItem)
    }

    @PostUpdate
    fun onPostUpdate(productItem: ProductItem) {
        val publisher = BeanConfig.getBean(ProductItemEventPublisher::class.java)
        publisher.publishPostUpdateEvent(productItem)
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

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    override fun findLast(ob: ProductItem): ProductItem? {
        if (Objects.isNull(ob.id)) return null

        return entityManager.find(ProductItem::class.java, ob.id)
    }
}

data class ProductItemUpdateEvent(val old: ProductItem, val new: ProductItem)

@Component
class ProductItemEventPublisher {
    private val log = LoggerFactory.getLogger(ProductItemEventPublisher::class.java)

    @Autowired
    private lateinit var publisher: ApplicationEventPublisher

    @Autowired
    private lateinit var finder: FindLatestManagerProductItem

    protected object ProductItemHolder {
        private val _holder = ThreadLocal<HashMap<Long, ProductItem>?>()

        fun get() = _holder.get()
        fun set(productItem: ProductItem) {
            val preHolder = get()
            val newHolder = if (preHolder != null) {
                preHolder[productItem.id] = productItem
                preHolder
            } else {
                hashMapOf(productItem.id to productItem)
            }
            _holder.set(newHolder)
        }

        fun clear(productItem: ProductItem) {
            val newHolder = get()?.let {
                it.remove(productItem.id)
                it
            }
            _holder.set(newHolder)
        }

        fun clearAll() = _holder.remove()
    }

    fun handlePreUpdateEvent(productItem: ProductItem) {
        log.info("Publish PRE_UPDATE productItem event: ProductItem(${productItem.id},${productItem.amount})")

        try {
            finder.findLast(productItem)?.let { ProductItemHolder.set(it) }
        } catch (e: RuntimeException) {
            log.error("ProductItemUpdateEventManager: $e\n{}", e)
        } catch (e: Exception) {
            log.error("ProductItemUpdateEventManager: $e\n{}", e)
        }
    }

    fun publishPostUpdateEvent(productItem: ProductItem) {
        log.info("Publish UPDATE productItem event: ProductItem(${productItem.id},${productItem.amount})")
        try {
            val old = ProductItemHolder.get()?.let { map ->
                map[productItem.id]
            } ?: return

            publisher.publishEvent(
                ProductItemUpdateEvent(old, productItem)
            )

            ProductItemHolder.clear(old)
        } catch (e: RuntimeException) {
            log.error("ProductItemUpdateEventManager: $e\n{}", e)
        } catch (e: Exception) {
            log.error("ProductItemUpdateEventManager: $e\n{}", e)
        }
    }
}
