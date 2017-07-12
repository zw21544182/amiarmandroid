package cn.ml_tech.mx.mlproj.util;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.ml_tech.mx.mlproj.R;

import static android.R.attr.path;

/**
 * 创建时间: 2017/7/12
 * 创建人: zhongwang
 * 功能描述:导出pdf工具类
 */

public class PdfUtil {
    private Activity activity;
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    private Document document;
    private Font yaHeiFont;
    private Paragraph paragraph;
    private Paragraph emptyParagraph;
    private PdfPCell pdfPCell;
    private Resources r;
    AssetManager assetManager;
    private String path = "test.pdf";

    public void createPDF() {
        try {
            // 上、下、左、右间距
            document = new Document(PageSize.A4, 20, 20, 20, 20);
            OutputStream outputStream = new FileOutputStream(new File(path));
            PdfWriter.getInstance(document, outputStream);
            r = activity.getResources();
            emptyParagraph = new Paragraph(" ");
            document.open();
            setFont();
            addImage();
            addHead();
            addInfo();
            addTable();
            document.newPage();
            addDetailTitle();
            addDetailInfo();
            document.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setFont() {
        String yaHeiFontName = activity.getResources().getString(R.raw.simsun);
        yaHeiFontName += ",1";
        try {
            yaHeiFont = new Font(BaseFont.createFont(yaHeiFontName, BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
            yaHeiFont.setSize(20);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 添加一行包含中文的信息到PDF测试

    }

    private void addImage() throws IOException, DocumentException {
        File file = new File(activity.getFilesDir().getAbsolutePath() + "/reporthead.jpg");
        if (file.exists()) {
            Image image = Image.getInstance(activity.getFilesDir().getAbsolutePath() + "/reporthead.jpg");
            image.scaleAbsolute(118, 35);
            image.scalePercent(50);
            document.add(image);
        } else {
            createFile(activity.getFilesDir().getAbsolutePath() + "/reporthead.jpg");
            Image image = Image.getInstance(activity.getFilesDir().getAbsolutePath() + "/reporthead.jpg");
            image.scaleAbsolute(118, 35);
            image.scalePercent(50);
            document.add(image);
        }
    }

    private void createFile(String s) {
        Log.d("zw", "start cREATEfILE");
        int bytesum = 0;
        int byteread = 0;
        try {
            InputStream inStream =
                    assetManager.open("reporthead.jpg");
            FileOutputStream fs = new FileOutputStream(s);
            byte[] buffer = new byte[1444];
            int length;
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread; //字节数 文件大小
                System.out.println(bytesum);
                fs.write(buffer, 0, byteread);
            }
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("zw", "false");
        }

    }

    private void addDetailInfo() throws DocumentException {
        yaHeiFont.setSize(10);
        for (int i = 0; i < 20; i++) {
            if ((i + 1) % 6 == 0) {
                document.newPage();
                document.add(emptyParagraph);
                document.add(emptyParagraph);
                document.add(emptyParagraph);
            }
            PdfPTable pdfPTable = new PdfPTable(5);
            pdfPTable.setWidthPercentage(70); // Width 100%
            float[] columnWidths = {2f, 1f, 1f, 1f, 0.8f};
            pdfPTable.setWidths(columnWidths);
            pdfPCell = new PdfPCell(new Paragraph("初检 1号样品", yaHeiFont));
            pdfPCell.setRowspan(1);
            pdfPCell.setColspan(5);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("漂浮物检出次数", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("%2", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("阴性", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("色差系数", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("%4", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("速降物检率(%)", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("%2", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("阴性", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setRowspan(2);
            pdfPCell.setColspan(1);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("旋瓶时间", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("%4", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("速降物检率(%)", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("%2", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("停瓶时间", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("%4", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("40-50um异物检出率(%)", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("18.78", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("阴性", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setRowspan(4);
            pdfPCell.setColspan(1);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("旋瓶状态", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("%4", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("50-60um异物检出率(%)", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("1.66", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("停瓶状态", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("%4", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("60-70um异物检出率(%)", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("0.55", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("综合结果", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setRowspan(5);
            pdfPCell.setColspan(1);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("阴性", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setRowspan(5);
            pdfPCell.setColspan(1);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("70um以上异物检出率(%)", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("0", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("40-50um异物检出数(颗)", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("-(0颗)", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("阴性", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setRowspan(2);
            pdfPCell.setColspan(1);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("50-70um异物检出数(颗)", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("-(0颗)", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("70um以上异物检出数(颗)", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("%2", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("阴性", yaHeiFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
            document.add(pdfPTable);
            document.add(emptyParagraph);
        }

    }

    private void addDetailTitle() throws DocumentException {
        yaHeiFont.setSize(50);
        paragraph = new Paragraph("详细结果", yaHeiFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        document.add(emptyParagraph);
        document.add(emptyParagraph);


    }

    private void addTable() throws DocumentException {
        PdfPTable table = new PdfPTable(4); // 3 columns.
        table.setWidthPercentage(80); // Width 100%
        table.setSpacingBefore(10f); // Space before table
        table.setSpacingAfter(10f); // Space after table
        // Set Column widths
        float[] columnWidths = {1f, 1f, 1f, 1f};
        table.setWidths(columnWidths);
        PdfPCell c1 = new PdfPCell(new Phrase("初检序号", yaHeiFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("综合结果", yaHeiFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("复检序号", yaHeiFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("综合结果", yaHeiFont));

        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);
        for (int i = 0; i < 20; i++) {
            c1 = new PdfPCell(new Phrase("%1", yaHeiFont));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("%2", yaHeiFont));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("%3", yaHeiFont));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("%4", yaHeiFont));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
        }
        document.add(table);
        document.add(emptyParagraph);
    }

    private void addInfo() {
        paragraph.add(emptyParagraph);
        yaHeiFont.setSize(10);
        List orderedList = new List(List.UNORDERED);
        for (int i = 0; i < 11; i++) {
            orderedList.add(new ListItem("仪器编号 %1仪器编号", yaHeiFont));
        }
        try {
            document.add(orderedList);
            document.add(emptyParagraph);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    private void addHead() {
        paragraph = new Paragraph("光散射法全自动可见异物检测仪(ML-AMIIXH-2)", yaHeiFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        try {
            document.add(paragraph);
            paragraph.add(emptyParagraph);
            yaHeiFont.setSize(50);
            paragraph = new Paragraph("检测报告", yaHeiFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.add(emptyParagraph);
            document.add(paragraph);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

}
