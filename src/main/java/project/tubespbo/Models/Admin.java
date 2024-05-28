package project.tubespbo.Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import project.tubespbo.Util.DatabaseConnection;

public class Admin extends Entity {
    public Admin(String username, String password, String namaLengkap, String alamat) {
        super(username, password, "admin", namaLengkap, alamat);
    }

    @Override
    public boolean authenticate() {
        String query = "SELECT nama_lengkap AS namaLengkap, alamat FROM users WHERE username = ? AND password = ? AND role = 'admin'";

        DatabaseConnection dbConnection = new DatabaseConnection(); // Create an instance
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, this.username);
            pstmt.setString(2, this.password);
            ResultSet rs = pstmt.executeQuery();


            if (rs.next()) {
                this.namaLengkap = rs.getString("namaLengkap");
                this.alamat = rs.getString("alamat");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
