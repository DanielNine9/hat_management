package DuAn.utils;

import DuAn.entity.NhanVien;

/**
 *
 * @author dinhh
 */
public class Auth {

    //Đối tượng này chứa thông tin người sử dụng sau khi đăng nhập
    public static NhanVien user = null; //người dùng chưa login
    public static boolean vietNam = true;
    public static boolean tiengViet = true;
    public static boolean first = true;

    //Xóa thông tin của người dùng khi có yêu cầu đăng xuất
    public static void clear() {//log out
        Auth.user = null;
    }

    //Kiểm tra đăng nhập hay chưa
    public static boolean isLogin() {
        return Auth.user != null;
    }

    //Kiểm tra có phải trường phòng hay không
    public static boolean isManager() {
        return Auth.isLogin() && user.isVaiTro();
    }
}
