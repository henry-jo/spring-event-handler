package com.henry.springevent.repository

import com.henry.springevent.dto.ProductItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductItemRepository : JpaRepository<ProductItem, Long> {
    fun findByName(name: String): ProductItem?
}