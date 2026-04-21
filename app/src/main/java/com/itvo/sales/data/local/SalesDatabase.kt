package com.itvo.sales.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.itvo.sales.data.local.dao.CustomerDao
import com.itvo.sales.data.local.dao.ProductDao
import com.itvo.sales.data.local.entity.ProductEntity
import com.itvo.sales.data.local.entity.CustomerEntity

@Database(
    entities = [ProductEntity::class, CustomerEntity::class],
    version = 3,
    exportSchema = false
)
abstract class SalesDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun customerDao(): CustomerDao

}