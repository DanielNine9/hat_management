/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DuAn.dao;

import DuAn.entity.Non;
import DuAn.utils.XJDBC;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

/**
 *
 * @author tiend
 */
public class NonDAO extends CommonDao<Non, Integer> {

    @Override
    public void insert(Non model) {
        String sql = "INSERT INTO NON ( TenNon, MaThuongHieu, MaLoai, Gia, GiamGia, NgayTao, SoLuong, Hinh) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        XJDBC.update(sql,
                model.getTenNon(),
                model.getMaThuongHieu(),
                model.getMaLoai(),
                model.getGia(),
                model.getGiamGia(),
                model.getNgayTao(),
                model.getSoLuong(),
                model.getHinh()
                
        );
    }

    @Override
    public void update(Non model) {
        String sql = "UPDATE NON SET TenNon=?, MaThuongHieu= ?, MaLoai = ?, Gia = ?, GiamGia = ?, NgayTao = ?, SoLuong = ?, Hinh = ? WHERE MaNon=?";

        XJDBC.update(sql,
                model.getTenNon(),
                model.getMaThuongHieu(),
                model.getMaLoai(),
                model.getGia(),
                model.getGiamGia(),
                model.getNgayTao(),
                model.getSoLuong(),
                model.getHinh(),
                model.getMaNon()
        );
    }

    public Non getByName(String email) {
        String sql = "SELECT * FROM Non WHERE TenNon= ? AND DELETED = 0";
        List<Non> list = this.selectBySql(sql, email);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public void delete(Integer id, boolean want) {
        String sql = "UPDATE NON SET deleted = " + (want ? "1" : "0") + " WHERE MANON=?";
        XJDBC.update(sql, id);
    }

    @Override
    public List<Non> selectAll() {
        String sql = "Select * from NON";
        return this.selectBySql(sql);
    }

    @Override
    public Non selectById(int maNon) {
        String sql = "SELECT * FROM NON WHERE MANON=?";
        List<Non> list = selectBySql(sql, maNon);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    protected List<Non> selectBySql(String sql, Object... args) {
        List<Non> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = XJDBC.query(sql, args);
                while (rs.next()) {
                    Non entity = new Non();
                    entity.setMaNon(rs.getInt("MaNon"));
                    entity.setMaThuongHieu(rs.getInt("MATHUONGHIEU"));
                    entity.setMaLoai(rs.getInt("MALOAI"));
                    entity.setGia(rs.getInt("Gia"));
                    entity.setTenNon(rs.getString("TenNon"));
//                    // Retrieve Gia as BigDecimal
//                    BigDecimal giaBigDecimal = rs.getBigDecimal("Gia");
//                    // Convert Gia to integer (assuming you want to store it as int)
//                    int gia = giaBigDecimal != null ? giaBigDecimal.intValue() : 0;
//                    entity.setGia(gia);
                    entity.setGiamGia(rs.getInt("GiamGia"));
                    entity.setNgayTao(rs.getDate("NgayTao"));
                    entity.setSoLuong(rs.getInt("SoLuong"));
                    entity.setHinh(rs.getString("Hinh"));
                    entity.setDeleted(rs.getBoolean("Deleted"));
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

    public Non getByLoai(Integer idLoai) {
        String sql = "SELECT * FROM NON WHERE MaLoai=? AND DELETED = 0";
        List<Non> list = selectBySql(sql, idLoai);
        return list.size() > 0 ? list.get(0) : null;
    }

    public Non getByThuongHieu(Integer idThuongHieu) {
        String sql = "SELECT * FROM NON WHERE MaThuongHieu=? AND DELETED = 0";
        List<Non> list = selectBySql(sql, idThuongHieu);
        return list.size() > 0 ? list.get(0) : null;
    }

    
     public String generateNewId() throws SQLException {
        String newUserId = null;
        String sql = "select max(manon) from non";
        ResultSet resultSet = XJDBC.query(sql);

        if (resultSet.next()) {
            int currentMaxId = resultSet.getInt(1);
            if (currentMaxId == 0) {
                newUserId = "SP1";
            } else {
                Formatter formatter = new Formatter();
                newUserId = "SP" + (++currentMaxId);
            }
        } else {
            newUserId = "SP1";
        }

        return newUserId;
    }
}
