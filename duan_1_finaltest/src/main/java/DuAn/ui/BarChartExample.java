/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DuAn.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author dinhh
 */
public class BarChartExample extends JFrame {

    public BarChartExample(String title) {
        super(title);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        //Set dữ liệu
        dataset.addValue(5, "Category 1", "Java");
        dataset.addValue(2.0, "Category 1", "C#");
        dataset.addValue(4, "Category 1", "Python");
        dataset.addValue(3, "Category 1", "Wed Development");
        dataset.addValue(5.0, "Category 1", "SQL Database");
        JFreeChart chart = ChartFactory.createBarChart3D(
                "Số lượng học viên theo chuyên đề",
                "Tên chuyên đề",
                "Số lượng học viên",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );
        BarRenderer renderer = (BarRenderer) chart.getCategoryPlot().getRenderer();
        //Hiển thị giá trị bên trên cột
        CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
        renderer.setBaseItemLabelGenerator(generator);
        renderer.setBaseItemLabelsVisible(true);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1200, 700)); // Đặt kích thước biểu đồ
        setContentPane(chartPanel);
    }
    
    public BarChartExample(String title, List<Object[]> list) {
        super(title);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Object[] item : list) {
            dataset.addValue((Integer)item[0], (Comparable) item[1], item[0].toString());
        }

        JFreeChart chart = ChartFactory.createBarChart3D(
                "Số lượng sản phẩm của loại",
                "Tên loại",
                "Số lượng sản phẩm",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        BarRenderer renderer = (BarRenderer) chart.getCategoryPlot().getRenderer();
        CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
        renderer.setBaseItemLabelGenerator(generator);
        renderer.setBaseItemLabelsVisible(true);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1200, 700)); // Set chart size
        setContentPane(chartPanel);


    }

    public static void main(String[] args) {
        List<String> chuyende = List.of("Java", "C#", "Python", "Web Development", "SQL Database");

//        BarChartExample example = new BarChartExample("Bar Chart Example", chuyende);
//
//        example.pack();
//        example.setVisible(true);
    }
}
