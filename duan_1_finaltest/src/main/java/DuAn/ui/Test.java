/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DuAn.ui;

/**
 *
 * @author dinhh
 */
import DuAn.dao.ThongKeDAO;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Calendar;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class Test {
    // chart doanh thu
//    public static void main(String[] args) {
//        // Connect to your database
//
//        try {
//            // Create a dataset
//            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//            ThongKeDAO tkdao = new ThongKeDAO();
//            List<Object[]> list = tkdao.sp_getDoanhThuTheoThang();
//
//            // Iterate over the list and add data to the dataset
//            for (Object[] row : list) {
//                if (row[0] == null || row[1] == null) {
//                    continue;
//                }
//                int year = row[0] != null ? (int) row[0] : 1;
//                int month = row[1] != null ? (int) row[1] : 1;
//                BigDecimal doanhThu = (BigDecimal) row[2];
//                dataset.addValue(doanhThu, "Doanh thu", year + "-" + month);
//            }
//
//            // Create the chart
//            // Get the current year
//            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
//
//// Create the chart with the concatenated title
//            JFreeChart chart = ChartFactory.createLineChart(
//                    "Doanh thu từng tháng của năm " + currentYear,
//                    "Tháng",
//                    "Doanh thu",
//                    dataset
//            );
//            // Display the chart in a panel
//            ChartPanel chartPanel = new ChartPanel(chart);
//            chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
//            javax.swing.JFrame frame = new javax.swing.JFrame("Revenue Plot");
//            frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
//            frame.getContentPane().add(chartPanel);
//            frame.pack();
//            frame.setVisible(true);
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }

    public static void main(String[] args) {
        try {
            // Create a dataset
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            ThongKeDAO tkdao = new ThongKeDAO();
            List<Object[]> list = tkdao.getTongTienKhachHang();

            // Iterate over the list and add data to the dataset
            for (Object[] row : list) {
                if (row[0] == null || row[1] == null) {
                    continue;
                }
                int maKH = (int) row[0];
                BigDecimal doanhThu = (BigDecimal) row[1];
                dataset.addValue(doanhThu.doubleValue(), "Tổng Tiền", "KH" + maKH); // Adding data to the dataset
            }

            // Create the chart
            JFreeChart chart = ChartFactory.createBarChart(
                    "Tổng Tiền Khách Hàng", // Chart title
                    "Mã Khách Hàng", // X-axis label
                    "Tổng Tiền", // Y-axis label
                    dataset // Dataset
            );

            // Display the chart in a panel
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
            javax.swing.JFrame frame = new javax.swing.JFrame("Tổng Tiền Khách Hàng");
            frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(chartPanel);
            frame.pack();
            frame.setVisible(true);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
