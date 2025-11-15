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


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class TransaksiDAO {

     /**
     * Mengekspor semua data transaksi dari database ke file CSV.
     * Format: ID,JENIS,JUMLAH,KATEGORI,DESKRIPSI,TANGGAL
     */
    public boolean exportToCsv(String filePath) {
        List<Transaksi> daftar = ambilSemuaTransaksi();
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Tulis Header
            writer.write("ID,JENIS,JUMLAH,KATEGORI,DESKRIPSI,TANGGAL\n");
            
            // Tulis data
            for (Transaksi t : daftar) {
                String line = String.format("%d,%s,%.2f,%s,%s,%s\n",
                    t.getId(),
                    t.getJenis(),
                    t.getJumlah(),
                    // Pastikan kategori/deskripsi yang mungkin mengandung koma diapit kutip jika perlu
                    t.getKategori().replace(",", ";"), 
                    t.getDeskripsi().replace(",", ";"),
                    t.getTanggal().toString()
                );
                writer.write(line);
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error saat ekspor data: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Mengimpor data transaksi dari file CSV ke database.
     * Mengabaikan baris header dan ID (DB akan generate ID baru).
     */
    public int importFromCsv(String filePath) {
    int count = 0;
    
    // Perbaikan: Hapus 'new' ganda
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        boolean isHeader = true;
        
        while ((line = reader.readLine()) != null) {
            if (isHeader) {
                isHeader = false; // Lewati baris header
                continue;
            }
            
            // Masalah Potensial: Jika Deskripsi atau Kategori mengandung koma, split akan rusak.
            // Solusi CSV robust membutuhkan library khusus, namun kita perbaiki indeksnya.
            String[] parts = line.split(",");
            
            // Perbaikan: Asumsi CSV memiliki setidaknya 6 kolom
            if (parts.length >= 6) {
                try {
                    // Perbaikan Indeks Sesuai Urutan Transaksi (Jenis, Jumlah, Kategori, Deskripsi, Tanggal)
                    
                    // parts[0] adalah ID lama, diabaikan.
                    // parts[1] adalah Tanggal
                    // parts[2] adalah Jenis
                    // parts[3] adalah Kategori
                    // parts[4] adalah Jumlah
                    // parts[5] adalah Deskripsi

                    // 1. Ambil dan parse data:
                    LocalDate tanggal = LocalDate.parse(parts[1]);
                    String jenis = parts[2];
                    String kategori = parts[3].replace(";", ","); 
                    double jumlah = Double.parseDouble(parts[4]);
                    String deskripsi = parts[5].replace(";", ","); 
                    
                    // 2. Buat objek Transaksi baru
                    // Pastikan urutan parameter sesuai dengan constructor Transaksi: 
                    // Transaksi(String jenis, double jumlah, String kategori, String deskripsi, LocalDate tanggal)
                    Transaksi t = new Transaksi(jenis, jumlah, kategori, deskripsi, tanggal);
                    
                    // 3. Simpan ke Database
                    tambahTransaksi(t); // Diasumsikan ini memanggil DAO untuk insert ke DB
                    count++;
                } catch (NumberFormatException | java.time.format.DateTimeParseException ex) {
                    // Menangkap error jika Jumlah bukan angka atau Tanggal salah format
                    System.err.println("Melewati baris dengan format data tidak valid: " + line);
                }
            }
        }
    } catch (IOException e) {
        System.err.println("Error saat import data: " + e.getMessage());
        return -1; // Mengembalikan -1 untuk menunjukkan kegagalan I/O
    } catch (Exception e) {
        // Menangkap exception lain yang mungkin terjadi
        System.err.println("Error tak terduga saat import data: " + e.getMessage());
        return -1;
    }
    return count;
}
    // --- C (Create) ---
    public void tambahTransaksi(Transaksi t) {
        String sql = "INSERT INTO transaksi(jenis, jumlah, kategori, deskripsi, tanggal) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, t.getJenis());
            pstmt.setDouble(2, t.getJumlah());
            pstmt.setString(3, t.getKategori());
            pstmt.setString(4, t.getDeskripsi());
            pstmt.setString(5, t.getTanggal().toString()); // Simpan LocalDate sebagai String
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // --- R (Read All) ---
    public List<Transaksi> ambilSemuaTransaksi() {
        String sql = "SELECT id, jenis, jumlah, kategori, deskripsi, tanggal FROM transaksi ORDER BY tanggal DESC, id DESC";
        List<Transaksi> daftarTransaksi = new ArrayList<>();
        
        try (Connection conn = DatabaseManager.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                // Catatan: Konstruktor Transaksi harus diubah untuk menerima ID
                Transaksi t = new Transaksi(
                    rs.getInt("id"),
                    rs.getString("jenis"),
                    rs.getDouble("jumlah"),
                    rs.getString("kategori"),
                    rs.getString("deskripsi"),
                    LocalDate.parse(rs.getString("tanggal"))
                );
                daftarTransaksi.add(t);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return daftarTransaksi;
    }

    // --- U (Update) ---
    public boolean ubahTransaksi(int id, Transaksi tBaru) {
        String sql = "UPDATE transaksi SET jenis = ?, jumlah = ?, kategori = ?, deskripsi = ?, tanggal = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tBaru.getJenis());
            pstmt.setDouble(2, tBaru.getJumlah());
            pstmt.setString(3, tBaru.getKategori());
            pstmt.setString(4, tBaru.getDeskripsi());
            pstmt.setString(5, tBaru.getTanggal().toString());
            pstmt.setInt(6, id);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // --- D (Delete) ---
    public boolean hapusTransaksi(int id) {
        String sql = "DELETE FROM transaksi WHERE id = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    // --- Perhitungan Bisnis (Dilakukan pada data yang sudah diambil) ---
    // Di sini, kita hanya mengambil total pemasukan dan pengeluaran langsung dari DB
    public double hitungTotal(String jenis) {
        String sql = "SELECT SUM(jumlah) AS total FROM transaksi WHERE jenis = ?";
        double total = 0.0;

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, jenis);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return total;
    }

    public double hitungSaldoAkhir() {
        return hitungTotal("pemasukan") - hitungTotal("pengeluaran");
    }
}
