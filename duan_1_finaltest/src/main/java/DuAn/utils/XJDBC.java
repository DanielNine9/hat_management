package DuAn.utils;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dinhh
 */
public class XJDBC {

    private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String database = "NonSys";
    private static String dburl = "jdbc:sqlserver://localhost:1433;databaseName=" + database + ";encrypt=true;trustServerCertificate=true;";
    private static String username = "sa";
    private static String password = "1234";
    private static Connection con;

    static {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(dburl, username, password);
            System.out.println("Ket noi thanh cong voi Database[" + database + "]");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (SQLException ex) {
            Logger.getLogger(XJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dburl, username, password);

            return connection;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /*
    * Xây dựng PreparedStatement
    * @param sql là câu lệnh SQL chứa có thể chứa tham số. Nó có thể là một lời gọi thủ tục lưu
    * @param args là danh sách các giá trị được cung cấp cho các tham số trong câu lệnh sql
    * @return PreparedStatement tạo được
    * @throws java.sql.SQLException lỗi sai cú pháp
     */
    //obj.getStmt("insert into HocVien values (?,?,?,?)","hv01","java","kh01",8.0)
    public static PreparedStatement getStmt(String sql, Object... args) throws SQLException {

        Connection connection = DriverManager.getConnection(dburl, username, password);
        PreparedStatement pstmt = null;
        if (sql.trim().startsWith("{")) {           //goi thủ tục lưu trữ
            pstmt = connection.prepareCall(sql);  //(store procedure)
        } else {
            pstmt = connection.prepareStatement(sql);
        }
        for (int i = 0; i < args.length; i++) {
            pstmt.setObject(i + 1, args[i]); // ps.setString(1, hv.gẹtHoTen());
        }
        return pstmt;
    }

    /*
    * Thực hiện câu lệnh SQL thao tác (INSERT, UPDATE, DELETE) hoặc thủ tục lưu thao tác dữ liệu
    * @param sql là câu lệnh SQL chứa có thể chứa tham số. Nó có thể là một lời gọi thủ tục lưu
    * @param args là danh sách các giá trị được cung cấp cho các tham số trong câu lệnh sql * 
     */
    public static void update(String sql, Object... args) { //insert, update, delete
        try {
            PreparedStatement stmt = XJDBC.getStmt(sql, args);
            try {
                stmt.executeUpdate();
            } finally {
                stmt.getConnection().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    * Thực hiện câu lệnh SQL truy vấn (SELECT) hoặc thủ tục lưu truy vấn dữ liệu
    * @param sql là câu lệnh SQL chứa có thể chứa tham số. Nó có thể là một lời gọi thủ tục lưu
    * @param args là danh sách các giá trị được cung cấp cho các tham số trong câu lệnh sql
     */
    public static ResultSet query(String sql, Object... args) {
        try {
            PreparedStatement stmt = XJDBC.getStmt(sql, args);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Object value(String sql, Object... args) {
        try {
            ResultSet rs = XJDBC.query(sql, args);
            if (rs.next()) {
                return rs.getObject(0);
            }
            rs.getStatement().getConnection().close();
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
