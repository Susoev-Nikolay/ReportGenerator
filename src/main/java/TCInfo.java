import java.util.List;

public class TCInfo {
    int count;
    List<String> tcNames;

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "TCInfo{" +
                "count=" + count +
                ", tcNames=" + tcNames +
                '}';
    }

    public List<String> getTcNames() {
        return tcNames;
    }

    public TCInfo(int count, List<String> tcNames) {
        this.count = count;
        this.tcNames = tcNames;
    }

    public void addTC(String tcName){
        tcNames.add(tcName);
        count++;
    }
}
