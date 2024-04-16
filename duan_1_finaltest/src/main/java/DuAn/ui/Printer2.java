/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DuAn.ui;

/**
 *
 * @author dinhh
 */
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrintQuality;
import javax.print.attribute.standard.PrinterResolution;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Printer2 implements Printable {

    private Image img;

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());
        
        if (pageIndex == 0) {
            double pageWidth = pageFormat.getImageableWidth();
            double pageHeight = pageFormat.getImageableHeight();
            double imageWidth = img.getWidth(null);
            double imageHeight = img.getHeight(null);
            
            double scaleX = pageWidth / imageWidth;
            double scaleY = pageHeight / imageHeight;
            g2d.scale(scaleX, scaleY);
            g2d.drawImage(img, 0, 0, null);
            return Printable.PAGE_EXISTS;
        }
        return Printable.NO_SUCH_PAGE;
    }

    public void printPage(String file) {
        try {
            // Read the image from file
            img = ImageIO.read(new File(file));

            // Set up the print job
            PrinterJob pj = PrinterJob.getPrinterJob();
            PageFormat pageFormat = pj.defaultPage();
            pj.setPrintable(this, pageFormat);

            // Show the print dialog and print the image
            if (pj.printDialog()) {
                pj.print();
            }
        } catch (PrinterException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Printer2().printPage("C:\\Users\\dinhh\\Documents\\duan_1_finaltest\\images\\hoadon\\2024-04-08_HD5.png");
    }
}

