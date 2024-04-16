/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DuAn.entity;

import DuAn.utils.Common;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dinhh
 */
public class KhachHangThongKe {

    private String maKhachHang;
    private String tenKhachHang;
    private String gioiTinh;
    private String soDienThoai;
    private Double tongTien;

    public KhachHangThongKe(Object[] obj) {
        this.maKhachHang = String.valueOf(obj[0]);
        this.tenKhachHang = (String) obj[1];
        this.gioiTinh =String.valueOf(obj[2]);
        this.soDienThoai = (String) obj[3];
        try {
            this.tongTien = Common.parseVietnameseMoney(String.valueOf(obj[4]));
        } catch (ParseException ex) {
            Logger.getLogger(KhachHangThongKe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public KhachHangThongKe() {
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }
    
    

   

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public Double getTongTien() {
        return tongTien;
    }

    public void setTongTien(Double tongTien) {
        this.tongTien = tongTien;
    }



}
