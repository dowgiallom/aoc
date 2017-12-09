import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day06 {

    public static void main(String[] args) {
        boolean virgin = true;
        int loopSize = 0;
        boolean countLoopSize = false;
        List<Integer> golden = null;

        List<Integer> banks = List.of(4,1,15,12,0,9,9,5,5,8,7,3,14,5,12,3);
        Set<List<Integer>> banksRepresentations = new HashSet<>();
        banksRepresentations.add(banks);

        while (true) {
            if (countLoopSize) {
                loopSize++;
            }

            int max = banks.stream().mapToInt(x -> x).max().getAsInt();
            int indexOfMax = banks.indexOf(max);
            int cursor = indexOfMax + 1;

            while (max > 0) {

                if (cursor >= banks.size()) {
                    cursor = 0;
                }

                int value = banks.get(cursor);
                value++;
                max--;

                banks = new ArrayList<>(banks);
                banks.set(cursor, value);
                banks.set(indexOfMax, max);
                cursor++;
            }

            if (banks.equals(golden)) {
                break;
            }

            boolean added = banksRepresentations.add(banks);
            if (!added && virgin) {
                golden = new ArrayList<>(banks);
                countLoopSize = true;
                virgin = false;
            }
        }

        System.out.println(loopSize);
    }
}
