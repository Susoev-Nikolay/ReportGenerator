import java.util.HashMap;

public enum ExcelHeaders {
    STATUS("статус"),
    TESTER("Тестировщик");

    private static HashMap<String,ExcelHeaders> stringToEnum=new HashMap<>();
    static {
        for (ExcelHeaders header:ExcelHeaders.values()){
            stringToEnum.put(header.getExcelValue(),header);
        }
    }
    public static ExcelHeaders getByString(String excelValue){
        return stringToEnum.get(excelValue);
    }
    public String getExcelValue() {
        return excelValue;
    }

    ExcelHeaders(String excelValue) {
        this.excelValue = excelValue;
    }

    private String excelValue;
}
