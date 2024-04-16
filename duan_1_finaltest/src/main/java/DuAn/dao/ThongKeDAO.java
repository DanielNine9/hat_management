/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DuAn.dao;

import DuAn.utils.XJDBC;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dinhh
 */
public class ThongKeDAO {

    private List<Object[]> getListOfArray(String sql, String[] cols, Object... arg) {
        try {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = XJDBC.query(sql, arg);
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list; //danh sách mảng ->> filltable
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    public List<Object[]> getSoLuongSanPhamLoai() {
        String sql = "{CALL sp_soluongnoncualoai}";

        String[] cols = {"tenloai", "soluong"};// chứa tên cột khi đổ vào table
        return this.getListOfArray(sql, cols);//chứa kết quả câu lệnh
    }

    public List<Object[]> getSoLuongSanPhamThuongHieu() {
        String sql = "{CALL sp_soluongnoncuathuonghieu}";

        String[] cols = {"tenthuonghieu", "soluong"};// chứa tên cột khi đổ vào table
        return this.getListOfArray(sql, cols);//chứa kết quả câu lệnh
    }

    public List<Object[]> getDoanhThuHomNay() {
        String sql = "{CALL sp_getdoanhthuhomnay}";
        String[] cols = {"doanhthuhomnay"};
        return this.getListOfArray(sql, cols);
    }

    public List<Object[]> getDoanhThu() {
        String sql = "{CALL sp_getdoanhthu}";
        String[] cols = {"doanhthu"};
        return this.getListOfArray(sql, cols);
    }

    public List<Object[]> sp_getDoanhThuTheoThang() {
        String sql = "{CALL sp_getDoanhThuTheoThang}";
        String[] cols = {"nam", "thang", "doanhthu"};
        return this.getListOfArray(sql, cols);
    }

    public List<Object[]> sp_getDoanhThuTheoNam() {
        String sql = "{CALL sp_getDoanhThuTheoNam}";
        String[] cols = {"nam", "doanhthu"};
        return this.getListOfArray(sql, cols);
    }

    public List<Object[]> getKhachHangMuaNhieuNhat() {
        String sql = "{CALL sp_getKhachHangMuaNhieuNhat}";
        String[] cols = {"makhachhang", "ten", "sodienthoai"};
        return this.getListOfArray(sql, cols);
    }

    public List<Object[]> getTongTienKhachHang() {
        String sql = "{CALL sp_getTongTienKhachHang}";
        String[] cols = {"makhachhang", "SUMTONGTIEN"};
        return this.getListOfArray(sql, cols);
    }

    public List<Object[]> getTrangThaiHoaDon() {
        String sql = "{CALL sp_getTrangThaiHoaDon}";
        String[] cols = {"trangthai", "soluong"};
        return this.getListOfArray(sql, cols);
    }

    public List<Object[]> getDoanhThu(String thangTruoc, String thangSau) {
      
        String sql = "{CALL GetHoaDonByMonthYearRange(?, ?)}";
        String[] cols = {"MaHoaDon", "MaNhanVien", "MaKhachHang", "TongTien", "TrangThai", "NgayTao"};
        return this.getListOfArray(sql, cols, thangTruoc, thangSau);
    }

//    public List<Object[]> getDoanhThu(int nam){
//        String sql = "{CALL sp_DoanhThu(?)}";
//        String[] cols = {"ChuyenDe","SoKH","SoHV","DoanhThu","ThapNhat","CaoNhat","TrungBinh"};
//        return this.getListOfArray(sql, cols, nam);
//            
//        }
}
