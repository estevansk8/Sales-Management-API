package com.api.sales_management.domain.shared

abstract class ValueObject {
    protected abstract fun validate() : Notification
}