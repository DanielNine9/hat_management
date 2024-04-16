package DuAn.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Component;
import java.io.File;

import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author dinhh
 */
public class XPDF {

    public static <T> void generatePdf(Component component, String title, List<T> entities, String filePath) {
        Document document = new Document(PageSize.A4);

        try {
            String home = System.getProperty("user.home");
            File file = new File(home + "/Downloads/");
              LocalDateTime currentDateTime = LocalDateTime.now();

            int day = currentDateTime.getDayOfMonth();
            int month = currentDateTime.getMonthValue();
            int year = currentDateTime.getYear();
            int hour = currentDateTime.getHour();
            int minute = currentDateTime.getMinute();
            int second = currentDateTime.getSecond();

            String ngày = String.valueOf(day);
            String tháng = String.valueOf(month);
            String năm = String.valueOf(year);
            String giờ = String.valueOf(hour);
            String phút = String.valueOf(minute);
            String giây = String.valueOf(second);

            if (day < 10) {
                ngày = "0" + ngày;
            }
            if (month < 10) {
                tháng = "0" + tháng;
            }
            if (hour < 10) {
                giờ = "0" + giờ;
            }
            if (minute < 10) {
                phút = "0" + phút;
            }
            if (second < 10) {
                giây = "0" + giây;
            }

            String dateTimeString = ngày + "_" + tháng + "_" + năm + "_" + giờ + "_" + phút + "_" + giây;

            String downloadPath = file + "\\" + filePath + "_" + dateTimeString + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(downloadPath));
            document.open();
            BaseFont bf = BaseFont.createFont("Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD, BaseColor.BLUE);
            Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD, BaseColor.DARK_GRAY);
            Font cellFont = new Font(bf, 12, Font.NORMAL);

            // Title
            Paragraph titleParagraph = new Paragraph(title, titleFont);
            titleParagraph.setAlignment(Element.ALIGN_CENTER);
            titleParagraph.setSpacingAfter(20f); // Add some space after the title
            document.add(titleParagraph);

            // Table
            PdfPTable table = new PdfPTable(getMaxFieldCount(entities));
            table.setWidthPercentage(100); // Set table width to 100% of page width

            // Add table headers
            addTableHeader(table, getHeaders(entities), headerFont);

            // Add data rows
            for (T entity : entities) {
                addDataRow(table, cellFont, getValues(entity));
            }

            document.add(table);

            document.close();
            System.out.println("PDF created successfully at: " + filePath);
            MsgBox.showMessageDialog(component, !Auth.tiengViet ? "The file has been saved to the download folder" : "File đã được lưu vào thư mục download");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static <T> int getMaxFieldCount(List<T> entities) {
        int maxFieldCount = 0;
        for (T entity : entities) {
            int fieldCount = entity.getClass().getDeclaredFields().length;
            if (fieldCount > maxFieldCount) {
                maxFieldCount = fieldCount;
            }
        }
        return maxFieldCount;
    }

    private static <T> String[] getHeaders(List<T> entities) {
        if (!entities.isEmpty()) {
            T entity = entities.get(0);
            Field[] fields = entity.getClass().getDeclaredFields();
            String[] headers = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                headers[i] = fields[i].getName().toUpperCase();
            }
            return headers;
        }
        return new String[0];
    }

    private static <T> String[] getValues(T entity) {
        Field[] fields = entity.getClass().getDeclaredFields();
        String[] values = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            try {
                fields[i].setAccessible(true);
                Object fieldValue = fields[i].get(entity);
                values[i] = String.valueOf(fieldValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return values;
    }

    private static void addTableHeader(PdfPTable table, String[] headers, Font font) {
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, font));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);
        }
    }

    private static void addDataRow(PdfPTable table, Font font, String[] values) {
        for (String value : values) {
            PdfPCell cell = new PdfPCell(new Phrase(value, font));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
    }
}
