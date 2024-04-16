/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DuAn.entity;

import java.util.Date;

/**
 *
 * @author tiend
 */
public class KhachHang implements Entity{

    private int maKhachHang;
    private String tenKhachHang;
    private Date ngaySinh;
    private String soDienThoai;
    private String email;
    private Date ngayGiaNhap;
    private boolean deleted;
    private boolean gioiTinh;
    private String hinh;

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getHinh() {
        return hinh;
    }
    
    
    

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }
    
    @Override
    public String toString() {
        return this.soDienThoai;
    }

    public int getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getNgayGiaNhap() {
        return ngayGiaNhap;
    }

    public void setNgayGiaNhap(Date ngayGiaNhap) {
        this.ngayGiaNhap = ngayGiaNhap;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
 
}
