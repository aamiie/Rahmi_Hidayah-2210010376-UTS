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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:keuangan_pribadi.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            // Load driver SQLite
            Class.forName("org.sqlite.JDBC"); 
            conn = DriverManager.getConnection(URL);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error koneksi database: " + e.getMessage());
        }
        return conn;
    }

    public static void createNewTable() {
        // SQL statement untuk membuat tabel transaksi
        String sql = "CREATE TABLE IF NOT EXISTS transaksi (\n"
                   + " id integer PRIMARY KEY AUTOINCREMENT,\n"
                   + " jenis text NOT NULL,\n"
                   + " jumlah real NOT NULL,\n"
                   + " kategori text NOT NULL,\n"
                   + " deskripsi text,\n"
                   + " tanggal text NOT NULL\n"
                   + ");";
        
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabel Transaksi berhasil dibuat atau sudah ada.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
