package DuAn;

/**
 *
 * @author dinhh
 */
import java.sql.*;

/*
     Kiểm tra có kết nối được với DATABASE không?
 */
public class TestConnect {

    public static void main(String[] agrs) throws SQLException, ClassNotFoundException {
        Connection con; //Tạo connection
        String user = "sa";
        String password = "1234";

        
        String database = "DuAnMau_SQL_PC07859";//Tên CSDL
        //Port 1433 vô CSDL tùy chỉnh nhiều cỏng lắm
        String url = "jdbc:sqlserver://localhost:1433;databaseName=" + database + ";encrypt=true;trustServerCertificate=true;";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        //Thư viện mà SQL công bố nó là cái container gôm câu lệnh
        con = DriverManager.getConnection(url, user, password);
        
        
        //Kết nối với CSQL
        System.out.println("Ket noi thanh cong voi Database["+database+"]");
    }
}
