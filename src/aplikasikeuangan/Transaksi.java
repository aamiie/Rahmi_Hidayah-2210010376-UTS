/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasikeuangan;

/**
 *
 * @author Lenovo
 */
import java.time.LocalDate;

public class Transaksi {
    private int id; // ID harus tetap ada
    private String jenis; 
    private double jumlah;
    private String kategori;
    private String deskripsi;
    private LocalDate tanggal;

    // KONSTRUKTOR BARU: Untuk transaksi baru (ID akan dibuat oleh DB)
    public Transaksi(String jenis, double jumlah, String kategori, String deskripsi, LocalDate tanggal) {
        // ID di set ke 0 atau -1, menandakan ini entri baru
        this.id = 0; 
        this.jenis = jenis;
        this.jumlah = jumlah;
        this.kategori = kategori;
        this.deskripsi = deskripsi;
        this.tanggal = tanggal;
    }

    // KONSTRUKTOR BARU: Untuk transaksi yang diambil dari DB
    public Transaksi(int id, String jenis, double jumlah, String kategori, String deskripsi, LocalDate tanggal) {
        this.id = id;
        this.jenis = jenis;
        this.jumlah = jumlah;
        this.kategori = kategori;
        this.deskripsi = deskripsi;
        this.tanggal = tanggal;
    }

    // --- Getter dan Setter ---

    public int getId() { return id; }
    // Setter untuk ID tidak diperlukan karena ditetapkan saat pembuatan

    public String getJenis() { return jenis; }
    public void setJenis(String jenis) { this.jenis = jenis; }

    public double getJumlah() { return jumlah; }
    public void setJumlah(double jumlah) { this.jumlah = jumlah; }

    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }

    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }

    public LocalDate getTanggal() { return tanggal; }
    public void setTanggal(LocalDate tanggal) { this.tanggal = tanggal; }

    // Metode untuk representasi tabel atau debugging
    @Override
    public String toString() {
        return id + " | " + jenis + " | " + jumlah + " | " + kategori;
    }
}