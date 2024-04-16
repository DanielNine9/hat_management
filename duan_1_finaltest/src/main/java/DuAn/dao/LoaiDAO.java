/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DuAn.dao;

import DuAn.entity.Loai;
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
public class LoaiDAO extends CommonDao<Loai, Integer> {

    @Override
    public void insert(Loai model) {
        String sql = "INSERT INTO Loai ( TenLoai ) VALUES (?)";
        XJDBC.update(sql,
                model.getTenLoai()
        );
    }

    @Override
    public void update(Loai model) {
        String sql = "UPDATE Loai SET TenLoai = ? WHERE MaLoai=?";
        XJDBC.update(sql,
                model.getTenLoai(),
                model.getMaLoai()
        );
    }

    @Override
    public void delete(Integer id, boolean want) {
        String sql = "UPDATE LOAI SET deleted = " + (want ? "1" : "0") + " WHERE MaLoai=?";
        XJDBC.update(sql, id);
    }

    @Override
    public List<Loai> selectAll() {
        String sql = "Select * from LOAI";
        return this.selectBySql(sql);
    }

    @Override
    public Loai selectById(int MaNV) {
        String sql = "SELECT * FROM LOAI WHERE MALOAI=?";
        List<Loai> list = selectBySql(sql, MaNV);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    protected List<Loai> selectBySql(String sql, Object... args) {
        List<Loai> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = XJDBC.query(sql, args);
                while (rs.next()) {
                    Loai entity = new Loai();
                    entity.setMaLoai(rs.getInt("MALOAI"));
                    entity.setTenLoai(rs.getString("TENLOAI"));
                    entity.setDeleted(rs.getBoolean("DELETED"));
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

    public Loai getByName(String tenLoai) {
        String sql = "SELECT * FROM Loai WHERE TenLoai = ?AND DELETED = 0";
        List<Loai> list = this.selectBySql(sql, tenLoai);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public Loai getByLoai(String idLoai) {
        String sql = "SELECT * FROM Loai WHERE TenLoai = ?";
        List<Loai> list = this.selectBySql(sql, idLoai);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
     public String generateNewId() throws SQLException {
        String newUserId = null;
        String sql = "select max(maloai) from loai";
        ResultSet resultSet = XJDBC.query(sql);

        if (resultSet.next()) {
            int currentMaxId = resultSet.getInt(1);
            if (currentMaxId == 0) {
                newUserId = "LO1";
            } else {
                Formatter formatter = new Formatter();
                newUserId = "LO" + (++currentMaxId);
            }
        } else {
            newUserId = "LO1";
        }

        return newUserId;
    }
}
