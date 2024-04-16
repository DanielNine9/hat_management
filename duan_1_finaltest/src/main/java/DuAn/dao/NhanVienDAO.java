/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DuAn.dao;

import DuAn.entity.NhanVien;
import DuAn.utils.Auth;
import DuAn.utils.XJDBC;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Random;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author dinhh
 */
public class NhanVienDAO extends CommonDao<NhanVien, Integer> {

    @Override
    public void insert(NhanVien model) {
        String sql = "INSERT INTO NhanVien ( MatKhau, HoTen, VaiTro, Hinh, SoDienThoai,email) VALUES (?, ?, ?, ?, ?, ?)";
        String matKhau = BCrypt.hashpw(model.getMatKhau(), BCrypt.gensalt());
        XJDBC.update(sql,
                matKhau,
                model.getHoTen(),
                model.isVaiTro(),
                model.getHinh(),
                model.getSdt(),
                model.getEmail()
        );

    }

    @Override
    public void update(NhanVien model) {
        String sql = "UPDATE NhanVien SET MatKhau=?, HoTen=?, VaiTro=?, Hinh = ?, SoDienThoai= ?, Email = ? WHERE MaNV=?";
        String matKhau = BCrypt.hashpw(model.getMatKhau(), BCrypt.gensalt());
        System.out.println(model.getMatKhau());
        System.out.println("salt "  + matKhau);
        XJDBC.update(sql,
                matKhau,
                model.getHoTen(),
                model.isVaiTro(),
                model.getHinh(),
                model.getSdt(),
                model.getEmail(),
                model.getMaNV());
    }
    
    public void updateKhongDoiMatKhau(NhanVien model) {
        String sql = "UPDATE NhanVien SET HoTen=?, VaiTro=?, Hinh = ?, SoDienThoai= ?, Email = ? WHERE MaNV=?";
        XJDBC.update(sql,
                model.getHoTen(),
                model.isVaiTro(),
                model.getHinh(),
                model.getSdt(),
                model.getEmail(),
                model.getMaNV());
    }

    @Override
    public void delete(Integer id, boolean want) {
        String sql = "UPDATE NhanVien SET deleted = " + (want ? "1" : "0") + " WHERE MaNV=?";
        XJDBC.update(sql, id);
    }

    @Override
    public List<NhanVien> selectAll() {
        String sql = "Select * from NhanVien";
        return this.selectBySql(sql);
    }

    @Override
    public NhanVien selectById(int MaNV) {
        String sql = "SELECT * FROM NhanVien WHERE MaNV=? AND DELETED = 0";
        List<NhanVien> list = selectBySql(sql, MaNV);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    protected List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = XJDBC.query(sql, args);
                while (rs.next()) {
                    NhanVien entity = new NhanVien();
                    entity.setMaNV(rs.getInt("MaNV"));
                    entity.setMatKhau(rs.getString("MatKhau"));
                    entity.setHoTen(rs.getString("HoTen"));
                    entity.setVaiTro(rs.getBoolean("VaiTro"));
                    entity.setDeleted(rs.getBoolean("deleted"));
                    entity.setEmail(rs.getString("Email"));
                    entity.setHinh(rs.getString("Hinh"));
                    entity.setSdt(rs.getString("SoDienThoai"));
                    entity.setOtp(rs.getString("OTP"));
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

    public void quenMatKhau(NhanVien model) {
        String sql = "UPDATE NhanVien SET MatKhau=? WHERE MaNV=?";
        XJDBC.update(sql,
                model.getMatKhau(),
                model.getMaNV());
    }

    public String layMaQuenMatKhau(String email) {
        Random random = new Random();
        String randomNumber = String.valueOf(100000 + random.nextInt(900000));
        String sql = "UPDATE NHANVIEN SET OTP = ? WHERE EMAIL=?";
        XJDBC.update(sql, randomNumber, email);
        return randomNumber;
    }

    public NhanVien getByEmail(String email) {
        String sql = "SELECT * FROM NHANVIEN WHERE EMAIL = ? AND DELETED = 0";
        List<NhanVien> list = this.selectBySql(sql, email);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public String generateNewId() throws SQLException {
        String newUserId = null;
        String sql = "select max(manv) from nhanvien";
        ResultSet resultSet = XJDBC.query(sql);

        if (resultSet.next()) {
            int currentMaxId = resultSet.getInt(1);
            if (currentMaxId == 0) {
                newUserId = "NV1";
            } else {
                Formatter formatter = new Formatter();
                newUserId = "NV" + (++currentMaxId);
            }
        } else {
            newUserId = "NV1";
        }

        return newUserId;
    }
}
