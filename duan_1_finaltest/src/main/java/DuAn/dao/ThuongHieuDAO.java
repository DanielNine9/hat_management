/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DuAn.dao;

import DuAn.entity.NhanVien;
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
public class ThuongHieuDAO extends CommonDao<ThuongHieu, Integer> {

    @Override
    public void insert(ThuongHieu model) {
        String sql = "INSERT INTO ThuongHieu ( TenThuongHieu, Logo ) VALUES (?, ?)";
        XJDBC.update(sql,
                model.getTenThuongHieu(),
                model.getLogo()
        );
    }

    @Override
    public void update(ThuongHieu model) {
        String sql = "UPDATE THUONGHIEU SET TenThuongHieu=?, Logo= ? WHERE MaThuongHieu=?";
        XJDBC.update(sql,
                model.getTenThuongHieu(),
                model.getLogo(),
                model.getMaThuongHieu()
        );
    }

    @Override
    public void delete(Integer id, boolean want) {
        String sql = "UPDATE THUONGHIEU SET deleted = " + (want ? "1" : "0") + " WHERE MaThuongHieu=?";
        XJDBC.update(sql, id);
    }

    @Override
    public List<ThuongHieu> selectAll() {
        String sql = "Select * from THUONGHIEU";
        return this.selectBySql(sql);
    }

    @Override
    public ThuongHieu selectById(int MaNV) {
        String sql = "SELECT * FROM THUONGHIEU WHERE MATHUONGHIEU=?";
        List<ThuongHieu> list = selectBySql(sql, MaNV);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    protected List<ThuongHieu> selectBySql(String sql, Object... args) {
        List<ThuongHieu> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = XJDBC.query(sql, args);
                while (rs.next()) {
                    ThuongHieu entity = new ThuongHieu();
                    entity.setMaThuongHieu(rs.getInt("MATHUONGHIEU"));
                    entity.setTenThuongHieu(rs.getString("TENTHUONGHIEU"));
                    entity.setLogo(rs.getString("LOGO"));
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

    public ThuongHieu getByName(String tenLoai) {
        String sql = "SELECT * FROM ThuongHieu WHERE TenThuongHieu = ? AND DELETED = 0";
        List<ThuongHieu> list = this.selectBySql(sql, tenLoai);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public String generateNewId() throws SQLException {
        String newUserId = null;
        String sql = "select max(mathuonghieu) from thuonghieu";
        ResultSet resultSet = XJDBC.query(sql);

        if (resultSet.next()) {
            int currentMaxId = resultSet.getInt(1);
            if (currentMaxId == 0) {
                newUserId = "TH1";
            } else {
                Formatter formatter = new Formatter();
                newUserId = "TH" + (++currentMaxId);
            }
        } else {
            newUserId = "TH1";
        }

        return newUserId;
    }

}
