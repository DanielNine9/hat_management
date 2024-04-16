/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DuAn.dao;

import DuAn.entity.KhachHang;
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
public class KhachHangDAO extends CommonDao<KhachHang, Integer> {

    @Override
    public void insert(KhachHang model) {
        String sql = "INSERT INTO KhachHang ( Ten, NgaySinh, SoDienThoai, Email, NgayGiaNhap, GioiTinh, Hinh ) VALUES (?, ?, ?, ?, ?, ?, ?)";
        XJDBC.update(sql,
                model.getTenKhachHang(),
                model.getNgaySinh(),
                model.getSoDienThoai(),
                model.getEmail(),
                model.getNgayGiaNhap(),
                model.isGioiTinh(),
                model.getHinh()
        );
    }

    @Override
    public void update(KhachHang model) {
        String sql = "UPDATE KhachHang SET Ten=?, NgaySinh= ?, SoDienThoai =?, Email = ?, NgayGiaNhap = ?, GioiTinh = ?, Hinh = ? WHERE MaKhachHang=?";
        XJDBC.update(sql,
                model.getTenKhachHang(),
                model.getNgaySinh(),
                model.getSoDienThoai(),
                model.getEmail(),
                model.getNgayGiaNhap(),
                model.isGioiTinh() ? 1 : 0,
                model.getHinh(),
                model.getMaKhachHang()
        );
    }

    @Override
    public void delete(Integer id, boolean want) {
        String sql = "UPDATE KhachHang SET deleted = " + (want ? "1" : "0") + " WHERE MaKhachHang=?";
        XJDBC.update(sql, id);
    }

    @Override
    public List<KhachHang> selectAll() {
        String sql = "Select * from KhachHang";
        return this.selectBySql(sql);
    }

    @Override
    public KhachHang selectById(int MaNV) {
        String sql = "SELECT * FROM KhachHang WHERE MaKhachHang=?";
        List<KhachHang> list = selectBySql(sql, MaNV);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    protected List<KhachHang> selectBySql(String sql, Object... args) {
        List<KhachHang> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = XJDBC.query(sql, args);
                while (rs.next()) {
                    KhachHang entity = new KhachHang();
                    entity.setMaKhachHang(rs.getInt("MAKHACHHANG"));
                    entity.setTenKhachHang(rs.getString("Ten"));
                    entity.setNgaySinh(rs.getDate("NgaySinh"));
                    entity.setSoDienThoai(rs.getString("SoDienThoai"));
                    entity.setEmail(rs.getString("Email"));
                    entity.setNgayGiaNhap(rs.getDate("NgayGiaNhap"));
                    entity.setDeleted(rs.getBoolean("DELETED"));
                    entity.setHinh(rs.getString("Hinh"));
                    entity.setGioiTinh(rs.getBoolean("GioiTinh"));
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

    public KhachHang getBySDT(String sdt) {
        System.out.println(sdt);
        String sql = "SELECT * FROM KhachHang WHERE SoDienThoai = ? AND DELETED = 0";
        List<KhachHang> list = this.selectBySql(sql, sdt);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
     public String generateNewId() throws SQLException {
        String newUserId = null;
        String sql = "select max(makhachhang) from khachhang";
        ResultSet resultSet = XJDBC.query(sql);

        if (resultSet.next()) {
            int currentMaxId = resultSet.getInt(1);
            if (currentMaxId == 0) {
                newUserId = "KH1";
            } else {
                Formatter formatter = new Formatter();
                newUserId = "KH" + (++currentMaxId);
            }
        } else {
            newUserId = "KH1";
        }

        return newUserId;
    }

}
