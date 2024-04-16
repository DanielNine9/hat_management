/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DuAn.entity;

/**
 *
 * @author tiend
 */
public class HoaDonChiTiet {
    private int MaHoaDonChiTiet;
    private int soLuong;
    private Double gia;
    private int maHoaDon;
    private int maNon;
    private String tenNon;

    @Override
    public String toString() {
        return "HDCT" + MaHoaDonChiTiet;
    }

    
    public String getTenNon() {
        return tenNon;
    }

    public void setTenNon(String tenNon) {
        this.tenNon = tenNon;
    }

    
    

    public int getMaHoaDonChiTiet() {
        return MaHoaDonChiTiet;
    }

    public void setMaHoaDonChiTiet(int MaHoaDonChiTiet) {
        this.MaHoaDonChiTiet = MaHoaDonChiTiet;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public Double getGia() {
        return gia;
    }

    public void setGia(Double money) {
        this.gia = money;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getMaNon() {
        return maNon;
    }

    public void setMaNon(int maNon) {
        this.maNon = maNon;
    }
}
