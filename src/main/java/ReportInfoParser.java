import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class ReportInfoParser {

    public static ReportInfo getReportInfo(String filePath) throws IOException {
        Path path = Path.of(filePath);
        String date = getFileCreationDate(path);
        List<RowInfo> rowsInfo = parseExcel(path);
        System.out.println(rowsInfo);
        return new ReportInfo(date,rowsInfo);

    }

    private static String getFileCreationDate(Path path) throws IOException {

        BasicFileAttributes attr = null;

        attr = Files.readAttributes(path, BasicFileAttributes.class);

        String creationDate = LocalDate.ofInstant(attr.creationTime().toInstant(), ZoneId.systemDefault()).toString();
        return creationDate;
    }
    public static Map<ExcelHeaders,Integer>  parseHeader(Row row){
        Iterator<Cell> cellIterator = row.cellIterator();
        Map<ExcelHeaders,Integer> headerIndex = new HashMap<>();
        int index=0;
        while (cellIterator.hasNext()){
            Cell currentCell = cellIterator.next();
            ExcelHeaders header = ExcelHeaders.getByString(currentCell.getStringCellValue());
            headerIndex.put(header,index);
            //System.out.println(currentCell+" "+index+" "+header);
            index++;
        }
        return headerIndex;
    }
    public static List<RowInfo> parseExcel(Path path) {
        List<RowInfo> result = new ArrayList<>();
        try {
            byte[] rawData = Files.readAllBytes(path);
            InputStream data = new ByteArrayInputStream(rawData);
            Workbook workbook = new XSSFWorkbook(data);
            Iterator<Sheet> schetchikSheets = workbook.sheetIterator();
            while (schetchikSheets.hasNext()){
                Sheet currentSheet = schetchikSheets.next();
                Iterator<Row> rowIterator=currentSheet.rowIterator();
                rowIterator.next();
                Map<ExcelHeaders,Integer> indexes = parseHeader(rowIterator.next());
                //System.out.println(indexes);
                while (rowIterator.hasNext()){
                    String tc = rowIterator.next().getCell(0).getStringCellValue();//тк
                    Row curRow = rowIterator.next();
                    //System.out.println(curRow.getCell(indexes.get(ExcelHeaders.STATUS)));
                    //System.out.println(curRow.getCell(indexes.get(ExcelHeaders.TESTER)));

                    result.add(new RowInfo(curRow.getCell(indexes.get(ExcelHeaders.TESTER)).getStringCellValue(),curRow.getCell(indexes.get(ExcelHeaders.STATUS)).getStringCellValue(),tc));

                    //Iterator<Cell> cell = curRow.cellIterator();
                    //while (cell.hasNext()){
                        //System.out.println(cell.next());
                    //}
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
