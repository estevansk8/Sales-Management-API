package com.api.sales_management

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SalesManagementApplication

fun main(args: Array<String>) {
	print("Vai Corinthians!!")
	runApplication<SalesManagementApplication>(*args)
}
