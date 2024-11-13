package com.example.inventory.data
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    /**
     * Insert
     * menginput item ke dalam database
     * Jika item sudah ada, maka akan tidak dilakukan inputasi ke database.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    /**
     * Update
     * Memperbarui item yang sudah ada di dalam database.
     */
    @Update
    suspend fun update(item: Item)

    /**
     * Delete
     * Menghapus item dari database.
     */
    @Delete
    suspend fun delete(item: Item)

    /**
     * Query("SELECT * from items WHERE id = :id")
     * Mengambil item berdasarkan ID dari database.
     * @param id ID dari item yang akan diambil.
     * Flow akan mengembalikan daftar item.
     */
    @Query("SELECT * from items WHERE id = :id")
    fun getItem(id: Int): Flow<Item>

    /**\
     * Query("SELECT * from items ORDER BY name ASC")
     * Mengambil semua item dari database dan mengurutkannya berdasarkan nama secara ascending.
     * Flow akan mengembalikan daftar item.
     */
    @Query("SELECT * from items ORDER BY name ASC")
    fun getAllItems(): Flow<List<Item>>
}