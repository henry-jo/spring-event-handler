package com.henry.springevent.listener

import com.henry.springevent.config.BeanConfig
import com.henry.springevent.dto.DeletedProductItem
import com.henry.springevent.dto.ProductItem
import com.henry.springevent.repository.DeletedProductItemRepository
import org.slf4j.LoggerFactory
import javax.persistence.PostUpdate
import javax.persistence.PreRemove
import javax.persistence.PreUpdate

class ProductItemListener {

    private val log = LoggerFactory.getLogger(ProductItemListener::class.java)

    @PreRemove
    fun onPreRemove(productItem: ProductItem) {
        val deletedProductItemRepository = BeanConfig.getBean(DeletedProductItemRepository::class.java)

        deletedProductItemRepository.save(
            DeletedProductItem(productItem)
        )
    }

    @PreUpdate
    fun onPreUpdate(productItem: ProductItem) {
        // do something
        log.info("pre update event listener")
    }

    @PostUpdate
    fun onPostUpdate(productItem: ProductItem) {
        // do something
        log.info("post update event listener")
    }
}