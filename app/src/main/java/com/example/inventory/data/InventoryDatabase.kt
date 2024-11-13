package com.example.inventory.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Database class with a singleton Instance object.
 */
@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {
    /**
     * Mengambil DAO untuk mengelola data item di database.
     */
    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var Instance: InventoryDatabase? = null

        /**
         * Mengambil instance database.
         * Jika belum ada, akan dibuat instance baru.
         * @param context Context aplikasi.
         * @return Instance dari InventoryDatabase.
         */
        fun getDatabase(context: Context): InventoryDatabase {
            // Jika instance sudah ada, kembalikan; kalau belum, buat yang baru.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, InventoryDatabase::class.java, "item_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
