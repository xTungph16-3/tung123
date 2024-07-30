package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *

 */
public class DB_Connect {
    // Phần Có thể sửa
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "123456789"; // Đổi mật khẩu theo SQL trên máy mỗi người
    private static final String SERVER = "localhost";
    private static final String PORT = "1433";
    private static final String DATABASE_NAME = "quanLyCuaHangGiay";
    private static final boolean USING_SSL = true; // Nếu máy báo lỗi SSL thì có thể đổi giá trị là true hoặc false
    private static String CONNECT_STRING;

    // Hết phần có thể sửa
    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            StringBuilder connectStringBuilder = new StringBuilder();
            connectStringBuilder.append("jdbc:sqlserver://")
                    .append(SERVER).append(":").append(PORT).append(";")
                    .append("databaseName=").append(DATABASE_NAME).append(";")
                    .append("user=").append(USERNAME).append(";")
                    .append("password=").append(PASSWORD).append(";");
            if (USING_SSL) {
                connectStringBuilder.append("encrypt=true;trustServerCertificate=true;");
                    }
            CONNECT_STRING = connectStringBuilder.toString();
            System.out.println("Connect String có dạng: " + CONNECT_STRING);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DB_Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(CONNECT_STRING);
        } catch (SQLException ex) {
            Logger.getLogger(DB_Connect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("Kết nối thành công!");

                // Lấy dữ liệu từ bảng nhân viên
                try (Statement statement = conn.createStatement()) {
                    String query = "SELECT * FROM nhanVien";
                    var resultSet = statement.executeQuery(query);

                    while (resultSet.next()) {
                        String nhanVienID = resultSet.getString("nhanVien_id");
                        String hoTen = resultSet.getString("hoTen");
                        // Thêm các trường dữ liệu khác theo cần lấy
                        System.out.println("NhanVienID: " + nhanVienID + ", HoTen: " + hoTen);
                        // Xuất thêm thông tin nếu cần
                    }
                }
            } else {
                System.out.println("Không thể kết nối đến cơ sở dữ liệu.");
            }
        }
    }
}
