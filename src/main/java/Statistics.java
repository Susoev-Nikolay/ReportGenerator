import java.util.Map;

public class Statistics {
    private final Map<String, TCInfo> countByStatus;
    private final Map<String, Map<String, TCInfo>> statusesCountByTester;

    @Override
    public String toString() {
        return "Statistics{" +
                "countByStatus=" + countByStatus +
                ", statusesCountByTester=" + statusesCountByTester +
                '}';
    }

    public Statistics(Map<String, TCInfo> countByStatus,
                      Map<String, Map<String, TCInfo>> statusesCountByTester) {
        this.countByStatus = countByStatus;
        this.statusesCountByTester = statusesCountByTester;
    }

    public Map<String, TCInfo> getCountByStatus() {
        return countByStatus;
    }

    public Map<String, Map<String, TCInfo>> getStatusesCountByTester() {
        return statusesCountByTester;
    }
}
