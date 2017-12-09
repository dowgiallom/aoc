import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

class Day05 {

    public static void main(String[] args) throws IOException {
        List<Integer> numbers = Files.lines(Paths.get("2017", "input05.txt"))
                .mapToInt(Integer::valueOf)
                .boxed()
                .collect(Collectors.toList());
        Integer[] input = numbers.toArray(new Integer[numbers.size()]);

        int position = 0;
        int jumps = 0;
        while (true) {
            if (position >= input.length || position < 0) {
                break;
            }

            int offset = input[position];
            int newPosition = position + offset;
            if (offset >= 3) {
                input[position] = --input[position];
            } else {
                input[position] = ++input[position];
            }
            position = newPosition;
            jumps++;
        }

        System.out.println(jumps);

    }

}
