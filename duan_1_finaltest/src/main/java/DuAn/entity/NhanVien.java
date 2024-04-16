package DuAn.entity;

/*
    Khai báo các thuộc tính của Nhân viên: Mã nhân viên, mật khẩu, họ tên, vai trò
    ( 0 = false là nhân viên)
    Thêm các getter setter
    Hàm tạo, contructor đầy đủ các tham số ở phần insert code
 */
/**
 *
 * @author dinhh
 */
public class NhanVien implements Entity{
	
    private int maNV;
    private String matKhau;
    private String hoTen;
    private boolean vaiTro = false; //mac dinh là nhan vien
    private String email;
    private String hinh;
    private boolean deleted = false;
    private String sdt;
    private String otp;

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    
    
    @Override
    public String toString() {
        return this.sdt;
    }

    public NhanVien() {
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public NhanVien(int maNV, String matKhau, String hoTen) {
        this.maNV = maNV;
        this.matKhau = matKhau;
        this.hoTen = hoTen;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public boolean isVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(boolean vaiTro) {
        this.vaiTro = vaiTro;
    }

}
