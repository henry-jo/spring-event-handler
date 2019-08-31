package com.henry.springevent.controller

import com.henry.springevent.dto.ProductItem
import com.henry.springevent.repository.ProductItemRepository
import com.henry.springevent.service.ProductItemService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/event/handler")
class EventHandlerController(
    private val productItemService: ProductItemService,
    private val productItemRepository: ProductItemRepository
) {

    @PostMapping("/product")
    fun addProduct(
        @RequestParam name: String
    ): ProductItem {
        val newProduct = ProductItem(
            amount = 1
        ).apply {
            this.name = name
            this.madeBy = "itner"
        }

        return productItemRepository.save(newProduct)
    }

    @DeleteMapping("/product")
    fun deleteProduct(
        @RequestParam name: String
    ): Boolean {
        val product = productItemRepository.findByName(name)
            ?: return false

        productItemRepository.delete(product)

        return true
    }

    @PutMapping("/product")
    fun updateProduct(
        @RequestParam name: String,
        @RequestParam amount: Int
    ): Boolean {
        productItemService.update(name, amount)

        return true
    }
}