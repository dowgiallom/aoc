import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

class Runner7 {

    public static void main(String[] args) throws IOException {
        String root = part1();
        Integer result = part2(root);
        System.out.println("Proper weight is: " + result);
    }

    static String part1() throws IOException {
        Map<String, Program> carriers = Files.lines(Paths.get("2017", "input07.txt"))
                .map(line -> line.split(" "))
                .map(Program::new)
                .filter(Program::carries)
                .collect(Collectors.toMap(Program::getName, p -> p));

        List<String> carrierNames = carriers.values().stream()
                .map(Program::getName)
                .collect(Collectors.toList());

        while(carriers.size() > 1) {
            for (String name : carrierNames) {
                Program program = carriers.get(name);
                if (program == null) {
                    continue;
                }
                Set<String> above = program.getProgramsAbove();
                boolean carriesOnlyLeaves = true;
                for (String nameAbove : above) {
                    if (carriers.containsKey(nameAbove)) {
                        carriesOnlyLeaves = false;
                    }
                }
                if (carriesOnlyLeaves) {
                    carriers.remove(name);
                }
            }
        }

        String root = carriers.keySet().stream().findFirst().get();
        System.out.println("Root is: " + root);

        return root;
    }

    static Integer part2(String root) throws IOException {
        Map<String, Program> programs = Files.lines(Paths.get("2017", "input07.txt"))
                .map(line -> line.split(" "))
                .map(Program::new)
                .collect(Collectors.toMap(Program::getName, p -> p));

        Program rootProgram = programs.get(root);
        return findProperWeight(programs, rootProgram);
    }

    static Integer findProperWeight(Map<String, Program> programs, Program rootProgram) {
            Map<Integer, Boolean> weights = new HashMap<>();
            for (String pName : rootProgram.getProgramsAbove()) {
                int cumulativeWeight = cumulativeWeight(programs, pName);
                weights.merge(cumulativeWeight, false, (a, b) -> true);
            }
            if (weights.size() == 2) {
                Integer balancedWeight = weights.entrySet().stream()
                        .filter(Map.Entry::getValue)
                        .findFirst()
                        .get()
                        .getKey();

                for (String pName : rootProgram.getProgramsAbove()) {
                    int cumulativeWeight = cumulativeWeight(programs, pName);
                    if (cumulativeWeight != balancedWeight) {
                        Program program = programs.get(pName);
                        boolean balanced = areProgramsBalanced(program.getProgramsAbove(), programs);
                        if (balanced) {
                            return balancedWeight - cumulativeWeight + program.getWeight();
                        } else {
                            return findProperWeight(programs, program);
                        }

                    }
                }
                //
            } else if (weights.size() == 1) {
                for (String pName : rootProgram.getProgramsAbove()) {
                    Program program = programs.get(pName);
                    return findProperWeight(programs, program);
                }
            }
                throw new RuntimeException("Something is not right");
    }

    private static boolean areProgramsBalanced(Collection<String> programsAbove, Map<String, Program> programs) {
        Set<Integer> weights = new HashSet<>();
        for (String p : programsAbove) {
            weights.add(cumulativeWeight(programs, p));
        }
        return weights.size() == 1;
    }

    static int cumulativeWeight(Map<String, Program> programs, String name) {
        Program root = programs.get(name);
        Set<String> programsAbove = root.getProgramsAbove();
        int sum = root.getWeight();
        if (programsAbove.isEmpty()) {
            return sum;
        }
        for (String pName : programsAbove) {
            int aboveWeight = cumulativeWeight(programs, pName);
            sum += aboveWeight;
        }
        return sum;
    }

}

class Program {
    private String name;
    private int weight;
    private Set<String> programsAbove = new HashSet<>();

    Program(String[] attributes) {
        name = attributes[0];
        String w = attributes[1];
        weight = Integer.valueOf(w.substring(1, w.length() - 1));
        for (int i = 3; i < attributes.length; i++) {
            String name = attributes[i];
            if (name.contains(",")) {
                name = name.substring(0, name.length() - 1);
            }
            programsAbove.add(name);
        }
    }

    boolean carries() {
        return !programsAbove.isEmpty();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Set<String> getProgramsAbove() {
        return programsAbove;
    }

    public void setProgramsAbove(Set<String> programsAbove) {
        this.programsAbove = programsAbove;
    }
}
