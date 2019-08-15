package com.henry.springevent.service

import com.henry.springevent.repository.ProductItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductItemService(
    @Autowired
    private val productItemRepository: ProductItemRepository
) {
}