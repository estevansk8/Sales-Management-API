package com.api.sales_management.domain.shared

interface Identifier <T>{
    fun validate() : Notification
    fun value() : T
}