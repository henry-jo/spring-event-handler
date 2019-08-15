package com.henry.springevent.dto

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class ProductItemCommon(
    @Column(name = "name")
    var name: String = "",

    @Column(name = "made_by")
    var madeBy: String = "",

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()
)