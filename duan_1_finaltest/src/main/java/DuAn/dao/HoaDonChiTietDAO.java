/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DuAn.dao;

import DuAn.entity.HoaDon;
import DuAn.entity.HoaDonChiTiet;
import DuAn.utils.XJDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tiend
 */
public class HoaDonChiTietDAO extends CommonDao<HoaDonChiTiet, Integer> {

    @Override
    public void insert(HoaDonChiTiet model) {
        System.out.println("MaHoaDon " + model.getMaHoaDon());
        String sql = "INSERT INTO HOADONCHITIET ( SoLuong, GiaTien, MaHoaDon, MaNon) VALUES (?, ?, ?, ?)";
        XJDBC.update(sql,
                model.getSoLuong(),
                model.getGia(),
                model.getMaHoaDon(),
                model.getMaNon()
        );
    }

    @Override
    public void update(HoaDonChiTiet model) {
        return;
    }

    @Override
    public void delete(Integer id, boolean want) {
        String sql = "UPDATE HoaDonChiTiet SET deleted = " + (want ? "1" : "0") + " WHERE MaHoaDon=?";
        XJDBC.update(sql, id);
    }

    public void delete(Integer id) {
        String sql = "Delete HoaDonChiTiet WHERE MaHoaDonChiTiet=?";
        XJDBC.update(sql, id);
    }

    @Override
    public HoaDonChiTiet selectById(int id) {
        String sql = "SELECT * FROM HoaDonChiTiet WHERE MaHoaDonChiTiet=?";
        List<HoaDonChiTiet> list = selectBySql(sql, id);
        return list.size() > 0 ? list.get(0) : null;
    }

    public List<HoaDonChiTiet> selectByMaHD(int maHD) {
        String sql = "SELECT * FROM HoaDonChiTiet WHERE MaHoaDon=?";
        List<HoaDonChiTiet> list = selectBySql(sql, maHD);
        return list.size() > 0 ? list : null;
    }

    public HoaDonChiTiet selectByMaNon(int id) {
        String sql = "SELECT * FROM HoaDonChiTiet WHERE MaNon=?";
        List<HoaDonChiTiet> list = selectBySql(sql, id);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<HoaDonChiTiet> selectAll() {
        String sql = "Select * from HoaDonChiTiet";
        return this.selectBySql(sql);
    }

    @Override
    protected List<HoaDonChiTiet> selectBySql(String sql, Object... args) {
        List<HoaDonChiTiet> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = XJDBC.query(sql, args);
                while (rs.next()) {
                    HoaDonChiTiet entity = new HoaDonChiTiet();
                    entity.setMaHoaDon(rs.getInt("MaHoaDon"));
                    entity.setMaHoaDonChiTiet(rs.getInt("MaHoaDonChiTiet"));
                    entity.setSoLuong(rs.getInt("SoLuong"));
                    entity.setMaNon(rs.getInt("MaNon"));
                    entity.setGia(rs.getDouble("GiaTien"));
                    list.add(entity);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }
}
