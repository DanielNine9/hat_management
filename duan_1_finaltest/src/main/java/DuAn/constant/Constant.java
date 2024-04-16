package DuAn.constant;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.Color;

/**
 *
 * @author dinhh
 */
public class Constant {

    // Red border
    public static final Border RED_BORDER = BorderFactory.createLineBorder(Color.RED);

    // Gray border
    public static final Border GRAY_BORDER = BorderFactory.createLineBorder(Color.GRAY);

    public static Border getGrayBorder(String title) {
        return BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), title);

    }
    
    
    public static Border getRedBorder(String title) {
        return BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.RED), title);

    }
}
