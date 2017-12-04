import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day04 {

    public static void main(String[] args) throws IOException {
        PassphraseValidator validator = new PassphraseValidator();
        long count = Files.lines(Paths.get("2017","input04.txt"))
                .filter(validator::isPhraseValidPt2)
                .count();
        System.out.println(count);
    }

}

class PassphraseValidator {

    boolean isPhraseValid(String input) {
        String[] words = input.split(" ");
        Set<String> set = new HashSet<>();
        for (String word : words) {
            boolean added = set.add(word);
            if (!added) {
                return false;
            }
        }

        return true;
    }

    boolean isPhraseValidPt2(String input) {
        String[] words = input.split(" ");
        Set<String> set = new HashSet<>();
        for (String word : words) {
            char[] chars = word.toCharArray();
            Arrays.sort(chars);
            boolean added = set.add(new String(chars));
            if (!added) {
                return false;
            }
        }

        return true;
    }

}
