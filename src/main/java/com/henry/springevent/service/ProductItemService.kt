package com.henry.springevent.service

import com.henry.springevent.repository.ProductItemRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProductItemService(
    private val productItemRepository: ProductItemRepository
) {

    private val log = LoggerFactory.getLogger(ProductItemService::class.java)

    fun update(name: String, amount: Int) {
        val product = productItemRepository.findByName(name) ?: return

        product.amount = amount
        productItemRepository.save(product)

        log.info("1차 업데이트")
        val product2 = productItemRepository.findByName("apple2")

        product.amount = 999999
        productItemRepository.save(product)

        log.info("service complete")
    }
}