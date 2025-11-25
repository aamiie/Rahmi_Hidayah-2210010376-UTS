## Identitas
+ Rahmi Hidayah
+ 2210010376
+ 5B NR BJM

# 💰 Aplikasi Keuangan Pribadi (Java Swing & SQLite)

[](https://opensource.org/licenses/MIT)
[](https://www.java.com/)
[](https://docs.oracle.com/javase/tutorial/uiswing/)

Aplikasi desktop sederhana yang dirancang untuk membantu pengguna mengelola dan mencatat transaksi keuangan pribadi (Pemasukan dan Pengeluaran). Dibangun menggunakan Java Swing untuk antarmuka pengguna grafis (GUI) dan SQLite sebagai sistem manajemen database ringan.

## 🖼️ Tampilan Aplikasi (Screenshot)




## ✨ Fitur Utama

Aplikasi ini menyediakan fitur inti untuk manajemen keuangan harian:

  * **Laporan Saldo Otomatis:** Menampilkan total saldo akhir yang diperbarui secara *real-time* setelah setiap operasi transaksi.
  * **CRUD Transaksi:** Mendukung operasi penuh untuk **Mencatat (Tambah)**, **Mengubah**, dan **Menghapus** data transaksi.
  * **Kategorisasi Transaksi:** Memisahkan data berdasarkan `Pemasukan` dan `Pengeluaran` dengan kategori yang telah ditentukan (Gaji, Makanan, Transportasi, dll.).
  * **Manajemen Data:**
      * **Ekspor (CSV):** Menyimpan histori transaksi ke format CSV untuk analisis eksternal atau *backup*.
      * **Impor (CSV):** Memuat data transaksi dari file CSV, mempermudah migrasi data.
  * **Validasi Input:** Terdapat validasi *key listener* pada kolom Jumlah untuk memastikan input berupa angka.
  * **Penyimpanan Lokal:** Menggunakan database SQLite, memastikan data tersimpan aman secara lokal tanpa perlu koneksi internet atau server terpisah.

## 🛠️ Persyaratan dan Dependencies

Untuk menjalankan aplikasi ini dengan sukses, Anda memerlukan lingkungan dan *library* berikut:

### 1\. Lingkungan Pengembangan

  * **Java Development Kit (JDK) 8 atau yang lebih baru.**
  * **IDE Java** (Direkomendasikan NetBeans karena penggunaan GUI Builder, atau IntelliJ/Eclipse).

### 2\. Dependencies (Library Tambahan)

Anda perlu menambahkan file JAR untuk *dependencies* berikut ke dalam *classpath* proyek Anda:

| Dependency | Kegunaan |
| :--- | :--- |
| **SQLite JDBC Driver** | Menghubungkan aplikasi Java ke database SQLite (misalnya: `sqlite-jdbc-3.44.1.0.jar`). |
| **JCalendar** | Menyediakan komponen `JDateChooser` untuk pemilihan tanggal yang intuitif. |

## ⚙️ Struktur Proyek

Aplikasi ini mengadopsi pola arsitektur dasar, memisahkan tanggung jawab menjadi beberapa kelas utama:

| File / Kelas | Fungsi Utama |
| :--- | :--- |
| `AplikasiKeuangan.java` | Kelas utama (Frame/View) yang menangani desain GUI (Java Swing Builder) dan logika interaksi pengguna (Listeners). |
| `Transaksi.java` | **Model Data.** Mendefinisikan struktur objek transaksi (ID, Tanggal, Jenis, Jumlah, Kategori, Deskripsi). |
| `TransaksiDAO.java` | **Data Access Object (DAO).** Menangani semua logika bisnis: CRUD, perhitungan saldo, serta Impor/Ekspor CSV. |
| `DatabaseManager.java` | Mengelola koneksi ke database SQLite dan memastikan tabel `transaksi` dibuat. |
| `keuangan_pribadi.db` | File database SQLite yang dibuat secara otomatis saat aplikasi dijalankan. |

## 🚀 Panduan Penggunaan

### 1\. Instalasi dan Setup

1.  **Clone Repositori:**
    ```bash
    git clone https://www.andarepository.com/
    ```
2.  **Konfigurasi Dependencies:**
      * Buka proyek di IDE pilihan Anda.
      * Tambahkan file JAR **SQLite JDBC** dan **JCalendar** ke dalam Libraries/Dependencies proyek.
3.  **Jalankan:**
      * Jalankan file `AplikasiKeuangan.java` (melalui IDE atau sebagai JAR yang dapat dieksekusi).

### 2\. Operasi Dasar

1.  **Mencatat Transaksi Baru:**
      * Pilih **Jenis** (Pemasukan/Pengeluaran).
      * Pilih **Kategori**.
      * Masukkan **Jumlah** (Hanya angka).
      * Pilih **Tanggal** menggunakan JDateChooser.
      * Isi **Deskripsi** singkat.
      * Klik tombol **SIMPAN TRANSAKSI**.
2.  **Mengubah/Menghapus Transaksi:**
      * Klik satu baris pada **Tabel Histori Transaksi**. Data akan otomatis dimuat ke *form* input.
      * Setelah data dimuat, Anda dapat:
          * Klik tombol **Ubah** (ikon pensil) setelah memodifikasi data pada *form*.
          * Klik tombol **Hapus** (ikon tempat sampah) untuk menghapus data yang dipilih.
3.  **Mengelola Data (Impor/Ekspor):**
      * Gunakan tombol **EXPORT** untuk menyimpan seluruh histori transaksi ke file `.csv` di lokasi pilihan Anda.
      * Gunakan tombol **IMPORT** untuk memuat transaksi baru dari file `.csv` yang mengikuti format kolom yang benar.

## 📝 Kontribusi

Jika Anda menemukan bug atau memiliki saran untuk perbaikan fitur, silakan buka *issue* atau kirimkan *pull request*.

## 📄 Lisensi

Proyek ini dilisensikan di bawah Lisensi MIT. Lihat file `LICENSE.md` untuk detail lebih lanjut.

-----

*Dibuat dengan ❤️ oleh [Rahmi Hidayah]*
