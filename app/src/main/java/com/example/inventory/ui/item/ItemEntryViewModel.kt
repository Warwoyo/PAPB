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

 package com.example.inventory.ui.item

 import androidx.compose.runtime.getValue
 import androidx.compose.runtime.mutableStateOf
 import androidx.compose.runtime.setValue
 import androidx.lifecycle.ViewModel
 import com.example.inventory.data.Item
 import java.text.NumberFormat
 import com.example.inventory.data.ItemsRepository

 /**
  * ViewModel to validate and insert items in the Room database.
  */
 class ItemEntryViewModel(private val itemsRepository: ItemsRepository) : ViewModel() {

     /**
      * Holds current item ui state
      */
     var itemUiState by mutableStateOf(ItemUiState())
         private set
     /*
      * `itemUiState` menyimpan data yang merepresentasikan tampilan UI saat ini.
      * Menggunakan `mutableStateOf` untuk memungkinkan UI secara otomatis
      * memperbarui saat nilai berubah.
      * `private set` artinya properti ini hanya bisa diubah dari dalam ViewModel.
      */

     /**
      * Updates the [itemUiState] with the value provided in the argument. This method also triggers
      * a validation for input values.
      */
     fun updateUiState(itemDetails: ItemDetails) {
         itemUiState = ItemUiState(itemDetails = itemDetails, isEntryValid = validateInput(itemDetails))
     }
     /*
      * Mengupdate `itemUiState` dengan nilai baru dari `itemDetails`.
      * Juga memeriksa validitas input menggunakan fungsi `validateInput`.
      * Jika input tidak valid, maka `isEntryValid` akan bernilai `false`.
      */

     private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
         return with(uiState) {
             name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank()
         }
     }
     /*
      * Memeriksa apakah input pengguna valid.
      * Input dianggap valid jika `name`, `price`, dan `quantity` tidak kosong.
      * Mengembalikan nilai `true` jika semua input terisi, dan `false` jika ada yang kosong.
      */

     suspend fun saveItem() {
         if (validateInput()) {
             itemsRepository.insertItem(itemUiState.itemDetails.toItem())
         }
     }
     /*
      * Menyimpan item ke dalam database jika input valid.
      * Fungsi ini dijalankan dalam coroutine (suspend) untuk mencegah blokir UI.
      * Mengambil data dari `itemUiState`, mengonversinya menjadi `Item`, lalu menyimpannya ke repository.
      */
 }

 /**
  * Represents Ui State for an Item.
  */
 data class ItemUiState(
     val itemDetails: ItemDetails = ItemDetails(),
     val isEntryValid: Boolean = false
 )
 /*
  * `ItemUiState` adalah kelas data yang menyimpan status UI saat ini.
  * `itemDetails` berisi detail item yang sedang diinput, dan `isEntryValid` menunjukkan apakah input valid.
  */

 data class ItemDetails(
     val id: Int = 0,
     val name: String = "",
     val price: String = "",
     val quantity: String = "",
 )
 /*
  * `ItemDetails` adalah kelas data yang menyimpan informasi input pengguna.
  * Semua properti diinisialisasi dengan nilai default kosong untuk menghindari error input awal.
  */

 /**
  * Extension function to convert [ItemDetails] to [Item]. If the value of [ItemDetails.price] is
  * not a valid [Double], then the price will be set to 0.0. Similarly if the value of
  * [ItemDetails.quantity] is not a valid [Int], then the quantity will be set to 0
  */
 fun ItemDetails.toItem(): Item = Item(
     id = id,
     name = name,
     price = price.toDoubleOrNull() ?: 0.0,
     quantity = quantity.toIntOrNull() ?: 0
 )
 /*
  * Fungsi ekstensi untuk mengonversi `ItemDetails` menjadi `Item`.
  * Jika `price` tidak dapat dikonversi menjadi `Double`, maka akan diisi dengan 0.0.
  * Jika `quantity` tidak dapat dikonversi menjadi `Int`, maka akan diisi dengan 0.
  */

 fun Item.formatedPrice(): String {
     return NumberFormat.getCurrencyInstance().format(price)
 }
 /*
  * Fungsi ekstensi untuk mengonversi harga item menjadi format mata uang lokal.
  * Menggunakan `NumberFormat.getCurrencyInstance()` untuk menampilkan harga dengan format yang sesuai.
  */

 /**
  * Extension function to convert [Item] to [ItemUiState]
  */
 fun Item.toItemUiState(isEntryValid: Boolean = false): ItemUiState = ItemUiState(
     itemDetails = this.toItemDetails(),
     isEntryValid = isEntryValid
 )
 /*
  * Fungsi ekstensi untuk mengonversi `Item` menjadi `ItemUiState`.
  * Digunakan untuk memperbarui status UI dengan data dari `Item`.
  * `isEntryValid` default-nya adalah `false`, tetapi bisa diatur sesuai kebutuhan.
  */

 /**
  * Extension function to convert [Item] to [ItemDetails]
  */
 fun Item.toItemDetails(): ItemDetails = ItemDetails(
     id = id,
     name = name,
     price = price.toString(),
     quantity = quantity.toString()
 )
 /*
  * Fungsi ekstensi untuk mengonversi `Item` menjadi `ItemDetails`.
  * Mengubah tipe data numerik (`Double` dan `Int`) menjadi `String` agar bisa ditampilkan di UI sebagai teks.
  */
