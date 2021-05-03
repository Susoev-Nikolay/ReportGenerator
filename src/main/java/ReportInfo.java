import java.util.List;

public class ReportInfo {
    private String creationDate;
    private List<RowInfo> listRepInf;

    public ReportInfo(String creationDate, List<RowInfo> listRepInf) {
        this.creationDate = creationDate;
        this.listRepInf = listRepInf;
    }

    public List<RowInfo> getListRepInf() {
        return listRepInf;
    }

    public String getCreationDate() {
        return creationDate;
    }
}
