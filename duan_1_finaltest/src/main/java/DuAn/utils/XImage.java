/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DuAn.utils;

import java.awt.Image;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import javax.swing.ImageIcon;

/**
 *
 * @author dinhh
 */
public class XImage {

    /**
     * Ảnh biểu tượng của ứng dụng xuất hiện trên mọi cửa sổ
     *
     * @return
     */
    public static Image getAppIcon() {
        String file = "images\\fpt.png";
        return new ImageIcon(XImage.class.getResource(file)).getImage();
    }

    public static String save(File src) {//d:/hinh1.png
        String path = (UUID.randomUUID().toString() + UUID.randomUUID().toString()).substring(0, 46) + ".png";
        File dst = new File("images", path);
        System.out.println("dst");
        System.out.println(dst.getAbsolutePath());
        if (!dst.getParentFile().exists()) {
            dst.getParentFile().mkdirs();
        }
        try {
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(dst.getAbsolutePath());

            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
            return path;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    
}

/**
 * Đọc hình ảnh logo chuyên đề
 *
 * @param filename là tên file logo
 * @return ảnh đọc trước
 */
public static ImageIcon read(String fileName) {
        File path = new File("images", fileName);
        return new ImageIcon(path.getAbsolutePath());
    }

}
