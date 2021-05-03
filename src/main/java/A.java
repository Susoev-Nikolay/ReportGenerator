import javax.swing.*;
import java.io.IOException;
import java.util.*;

public class A {
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        //createGui();
        //Report rep =new Report();

        //rep.setVisible(true);
        JFrame jFrame = new JFrame();
        jFrame.setContentPane(new Report().jopa);
        jFrame.pack();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);



    }
    public static void createReport(String path){
        //String path="F:\\ReportGenerator.2.0\\src\\main\\resources\\Otchet.xlsm";
        try {
            ReportInfo r = ReportInfoParser.getReportInfo(path);
            Statistics mainStatistic = analyze(r.getListRepInf());
            //System.out.println(analyze(r.getListRepInf()));
            //System.out.println(r.getCreationDate());
            PdfCreator.createPDF(mainStatistic,r.getCreationDate());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Statistics analyze(List<RowInfo> rawStatistic) {
        Map<String, TCInfo> countByStatus = new TreeMap<>(Comparator.naturalOrder());
        Map<String, Map<String, TCInfo>> countsByStatusForTester = new TreeMap<>(Comparator.naturalOrder());

        for (RowInfo rowInfo : rawStatistic) {
            addTC(countByStatus, rowInfo.getTcName(), rowInfo.getStatus());
            Map<String, TCInfo> forTester = countsByStatusForTester.getOrDefault(
                    rowInfo.getTester(),
                    new HashMap<>()
            );
            addTC(forTester, rowInfo.getTcName(), rowInfo.getStatus());
            countsByStatusForTester.put(rowInfo.getTester(), forTester);
        }

        return new Statistics(
                countByStatus,
                countsByStatusForTester
        );
    }
    private static void addTC(Map<String,TCInfo> map,String tcName,String status){
        TCInfo countByStatus = map.getOrDefault(status, new TCInfo(0,new ArrayList<>()));
        countByStatus.addTC(tcName);
        map.put(status,countByStatus);

    }
}
