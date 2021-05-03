import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.*;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class PdfCreator {
    private static void drawPieChartGeneralized(Document document,String tester , Map<String, TCInfo> countByStatus) throws IOException {
        PieChart chart = new PieChartBuilder().width(400).height(300).theme(Styler.ChartTheme.GGPlot2).build();
        chart.setTitle(tester);
        chart.getStyler().setLegendVisible(true);
        chart.getStyler().setDrawAllAnnotations(true);
        chart.getStyler().setAnnotationType(PieStyler.AnnotationType.Percentage);

        chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);
        chart.getStyler().setPlotContentSize(1);
        chart.getStyler().setStartAngleInDegrees(90);

        for (Map.Entry<String, TCInfo> entry : countByStatus.entrySet()) {
            chart.addSeries(entry.getKey() + "  " + entry.getValue().getCount()+ " "+ entry.getValue().getTcNames(), entry.getValue().getCount());
        }

        byte [] rawImage = BitmapEncoder.getBitmapBytes(chart, BitmapEncoder.BitmapFormat.GIF);
        Image pdfImage = Image.getInstance(rawImage);
        pdfImage.setAlignment(Element.ALIGN_CENTER);
        document.add(pdfImage);
    }
    private static void drawPieChartGeneralized(Document document, Map<String, TCInfo> countByStatus) throws IOException {
        PieChart chart = new PieChartBuilder().width(600).height(400).theme(Styler.ChartTheme.GGPlot2).build();
        chart.setTitle("Тест-план");
        // Customize Chart

        chart.getStyler().setLegendVisible(true);
        chart.getStyler().setLegendBackgroundColor(Color.GRAY);
        chart.getStyler().setDrawAllAnnotations(true);
        chart.getStyler().setAnnotationType(PieStyler.AnnotationType.Percentage);
//        chart.getStyler().setAnnotationType(PieStyler.AnnotationType.LabelAndPercentage);

        chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);
        chart.getStyler().setPlotContentSize(1);
        chart.getStyler().setStartAngleInDegrees(90);

        // Series
        for (Map.Entry<String, TCInfo> entry : countByStatus.entrySet()) {
            chart.addSeries(entry.getKey() + "  " + entry.getValue().getCount(), entry.getValue().getCount());
        }

        //new SwingWrapper(chart).displayChart();

        byte [] rawImage = BitmapEncoder.getBitmapBytes(chart, BitmapEncoder.BitmapFormat.GIF);
        Image pdfImage = Image.getInstance(rawImage);
        pdfImage.setAlignment(Element.ALIGN_CENTER);
        document.add(pdfImage);
    }
    public static void createPDF(Statistics statistics,String testDate){
        Document document = new Document(PageSize.A4);
        int defectCount = statistics.getCountByStatus().get("не тестировалось ").getCount()+statistics.getCountByStatus().get("не соответствует").getCount();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Hello.pdf"));

            document.open();

            Paragraph title = new Paragraph("Отчёт");
            title.setAlignment(Element.ALIGN_CENTER);
            Font myTitle=new Font();
            myTitle.setFamily("Courier");
            myTitle.setSize(50);

            title.setFont(myTitle);
            document.add(title);


            Paragraph text = new Paragraph();

            text.add("Дата проведенного теста " + testDate + "\n");
            text.add("Кол-во дефектов "
                    + defectCount
                    + "\n"); // WTF?!
            document.add(text);



            drawPieChartGeneralized(document, statistics.getCountByStatus());
            for (Map.Entry<String, Map<String,TCInfo>> byTester: statistics.getStatusesCountByTester().entrySet()) {
                System.out.println(byTester);
                drawPieChartGeneralized(document, byTester.getKey(), byTester.getValue());
            }



        }  catch(DocumentException | IOException de) {
            System.err.println(de.getMessage());
        }

        document.close();
    }
}
