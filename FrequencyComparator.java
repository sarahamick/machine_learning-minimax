import java.util.Comparator;

public class FrequencyComparator implements Comparator<Double[]> {

    @Override
    public int compare(Double[] o1, Double[] o2) {
        if(o1[1] > o2[1]) return 1;
        if(o2[1] > o1[1]) return -1;
        return 0;
    }
}
