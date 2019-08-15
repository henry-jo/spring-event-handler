package com.henry.springevent.dto

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "deleted_product_item")
data class DeletedProductItem(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "made_at")
    @field:NotNull
    var madeAt: LocalDateTime? = null
) : ProductItemCommon() {

    constructor(productItem: ProductItem): this() {
        name = productItem.name
        madeBy = productItem.madeBy
        madeAt = productItem.createdAt
    }
}