package com.henry.springevent.repository

import com.henry.springevent.dto.DeletedProductItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeletedProductItemRepository : JpaRepository<DeletedProductItem, Long>