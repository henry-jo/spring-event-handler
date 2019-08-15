package com.henry.springevent.dto

import com.henry.springevent.listener.ProductItemListener
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "product_item")
@EntityListeners(ProductItemListener::class)
data class ProductItem(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "amount")
    var amount: Int = 0
): ProductItemCommon()