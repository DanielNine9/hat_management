package DuAn.utils;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author dinhh
 */
public class MsgBox {

    public static void showMessageDialog(Component parent, String content) {
        JOptionPane.showMessageDialog(parent, content, "Hệ thống quản lý EduSys", JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean showConfirmDialog(Component parent, String content) {
        int choose = JOptionPane.showConfirmDialog(parent, content, "Hệ thống quản lý EduSys",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return choose == JOptionPane.YES_OPTION;
    }

    public static void showErrorDialog(Component parent, String content, String title) {
        JOptionPane.showMessageDialog(parent, content, title, JOptionPane.ERROR_MESSAGE);
    }
}
