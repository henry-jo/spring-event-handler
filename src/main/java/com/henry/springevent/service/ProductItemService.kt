package com.henry.springevent.service

import com.henry.springevent.publisher.ProductItemEventPublisher
import com.henry.springevent.repository.ProductItemRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProductItemService(
    private val productItemRepository: ProductItemRepository,
    private val publisher: ProductItemEventPublisher
) {

    private val log = LoggerFactory.getLogger(ProductItemService::class.java)

    fun update(name: String, amount: Int) {

        val product = productItemRepository.findByName(name)
            ?: return

        product.amount = amount

        productItemRepository.save(product)

        publisher.publishEvent(product)

        log.info("service complete")
    }
}