/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.inventory.data

import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [Item] from a given data source.
 */
interface ItemsRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllItemsStream(): Flow<List<Item>>
    /*
     * Mengambil semua item dari data source dalam bentuk Flow.
     * Flow membuat data untuk dipantau secara real-time,
     * jadi setiap perubahan pada data akan langsung diperbarui kepada observer.
     */

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getItemStream(id: Int): Flow<Item?>
    /*
     * Mengambil data item berdasarkan ID yang diberikan dalam bentuk Flow.
     * Jika item ditemukan, maka akan dikirim melalui Flow, jika tidak, akan mengirimkan nilai null.
     */

    /**
     * Insert item in the data source
     */
    suspend fun insertItem(item: Item)
    /*
     * Menyimpan item baru ke dalam data source.
     * Fungsi ini menggunakan suspend, artinya bisa dipanggil dari coroutine
     * dan dijalankan di thread background tanpa mengganggu UI.
     */

    /**
     * Delete item from the data source
     */
    suspend fun deleteItem(item: Item)
    /*
     * Menghapus item dari data source.
     * Menggunakan suspend agar dapat dijalankan di thread background,
     * sehingga operasi penghapusan tidak akan mengganggu UI.
     */

    /**
     * Update item in the data source
     */
    suspend fun updateItem(item: Item)
    /*
     * Memperbarui data item yang sudah ada dalam data source.
     * Menggunakan suspend untuk menjalankan proses pembaruan di thread background,
     * memungkinkan UI tetap responsif saat pembaruan dilakukan.
     */
}

