package com.henry.springevent.publisher

import com.henry.springevent.dto.ProductItem
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class ProductItemEventPublisher(
    private val publisher: ApplicationEventPublisher
) {
    fun publishEvent(productItem: ProductItem) {
        publisher.publishEvent(productItem)
    }
}