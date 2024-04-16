/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DuAn.dao;

import DuAn.dao.HoaDonDao;
import DuAn.entity.HoaDon;
import DuAn.entity.ThuongHieu;
import DuAn.utils.XJDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

/**
 *
 * @author tiend
 */
public class HoaDonDao extends CommonDao<HoaDon, Integer> {

    @Override
    public void insert(HoaDon model) {
        String sql = "INSERT INTO HoaDon ( MaNhanVien, MaKhachHang, TongTien, TrangThai, NgayTao) VALUES (?, ?, ?, ?, ?)";
        XJDBC.update(sql,
                model.getMaNhanVien(),
                model.getMaKhachHang(),
                model.getTongTien(),
                model.getTrangThai(),
                model.getNgayTao()
        );
    }

    @Override
    public void update(HoaDon model) {
        String sql = "UPDATE HoaDon SET TongTien = ?, TrangThai = ? WHERE MaHoaDon=?";
        XJDBC.update(sql,
                model.getTongTien(),
                model.getTrangThai(),
                model.getMaHoaDon()
        );
    }

    @Override
    public void delete(Integer id, boolean want) {
        String sql = "UPDATE HoaDon SET deleted = " + (want ? "1" : "0") + " WHERE MaHoaDon=?";
        XJDBC.update(sql, id);
    }
    
    public void delete(Integer id) {
        String sql = "DELETE HoaDon WHERE MaHoaDon=?";
        XJDBC.update(sql, id);
    }

    @Override
    public List<HoaDon> selectAll() {
        String sql = "Select * from HoaDon";
        return this.selectBySql(sql);
    }

    @Override
    public HoaDon selectById(int MaNV) {
        String sql = "SELECT * FROM HoaDon WHERE MaHoaDon=?";
        List<HoaDon> list = selectBySql(sql, MaNV);
        return list.size() > 0 ? list.get(0) : null;
    }
    
     public HoaDon selectByMaKH(int maKH) {
        String sql = "SELECT * FROM HoaDon WHERE MaKhachHang=?";
        List<HoaDon> list = selectBySql(sql, maKH);
        return list.size() > 0 ? list.get(0) : null;
    }
     
     public List<HoaDon> selectByMaKHFull(int maKH) {
        String sql = "SELECT * FROM HoaDon WHERE MaKhachHang=?";
        List<HoaDon> list = selectBySql(sql, maKH);
        return list;
    }

    @Override
    protected List<HoaDon> selectBySql(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = XJDBC.query(sql, args);
                while (rs.next()) {
                    HoaDon entity = new HoaDon();
                    entity.setMaHoaDon(rs.getInt("MaHoaDon"));
                    entity.setMaKhachHang(rs.getInt("MaKhachHang"));
                    entity.setMaNhanVien(rs.getInt("MaNhanVien"));
                    entity.setTongTien(rs.getDouble("TongTien"));
                    entity.setTrangThai(rs.getString("TrangThai"));
                    entity.setNgayTao(rs.getDate("NgayTao"));
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
    
     public String generateNewId() throws SQLException {
        String newUserId = null;
        String sql = "select max(MaHoaDon) from hoadon";
        ResultSet resultSet = XJDBC.query(sql);

        if (resultSet.next()) {
            int currentMaxId = resultSet.getInt(1);
            if (currentMaxId == 0) {
                newUserId = "HD1";
            } else {
                Formatter formatter = new Formatter();
                newUserId = "HD" + (currentMaxId);
            }
        } else {
            newUserId = "HD1";
        }

        return newUserId;
    }
     
     
    
}
