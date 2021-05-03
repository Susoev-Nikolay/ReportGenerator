public class RowInfo {
    String tester;
    String status;
    String tcName;

    public String getTcName() {
        return tcName;
    }

    public String getTester() {
        return tester;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "RowInfo{" +
                "tester='" + tester + '\'' +
                ", status='" + status + '\'' +
                ", tcName='" + tcName + '\'' +
                '}';
    }

    public RowInfo(String tester, String status, String tcName) {
        this.tester = tester;
        this.status = status;
        this.tcName = tcName;
    }
}
