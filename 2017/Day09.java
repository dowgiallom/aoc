import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day09 {
    public static void main(String[] args) throws IOException {
        final String input = Files.lines(Paths.get("2017", "input09.txt"))
                .findFirst().orElseThrow(() -> new RuntimeException("Line expected"));

        int total = 0;
        int garbageCount = 0;

        boolean trashIsOpened = input.charAt(0) == '<';
        boolean shouldIgnore = input.charAt(0) == '!';
        int groupLevel = input.charAt(0) == '{' ? 1 : 0;

        for (int i = 1; i < input.toCharArray().length; i++) {
            char current = input.charAt(i);

            if (shouldIgnore) {
                shouldIgnore = false;
                continue;
            }

            if (current == '!') {
                shouldIgnore = true;
                continue;
            }

            if (trashIsOpened) {
                if (current == '>') {
                    trashIsOpened = false;
                    continue;
                }
                garbageCount++;
                continue;
            }

            if (current == '<') {
                trashIsOpened = true;
                continue;
            }

            if (current == '{') {
                groupLevel++;
                continue;
            }

            if (current == '}' && groupLevel > 0) {
                total += groupLevel;
                groupLevel--;
            }
        }

        System.out.println("Total groups value: " + total);
        System.out.println("Garbage count: " + garbageCount);
    }
}

