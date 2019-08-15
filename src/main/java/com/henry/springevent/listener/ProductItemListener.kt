package com.henry.springevent.listener

import com.henry.springevent.config.BeanConfig
import com.henry.springevent.dto.DeletedProductItem
import com.henry.springevent.dto.ProductItem
import com.henry.springevent.repository.DeletedProductItemRepository
import javax.persistence.PreRemove
import javax.persistence.PreUpdate

class ProductItemListener {

    @PreUpdate
    fun onPreUpdate(productItem: ProductItem) {
        // do something
    }

    @PreRemove
    fun onPreRemove(productItem: ProductItem) {
        val deletedProductItemRepository = BeanConfig.getBean(DeletedProductItemRepository::class.java)

        deletedProductItemRepository.save(
            DeletedProductItem(productItem)
        )
    }
}