import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;


public class Day08 {

    public static void main(String[] args) throws IOException {
        List<Instruction> instructions = Files.lines(Paths.get("2017", "input08.txt"))
                .map(s -> s.split(" "))
                .map(Instruction::new)
                .collect(Collectors.toList());

        Map<String, Integer> map = instructions.stream()
                .map(Instruction::getRegisterToChange)
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.toMap(r -> r, r -> 0));

        Set<Integer> allEverCreatedValues = new HashSet<>();
        for (Instruction instruction : instructions) {
            boolean isTrue = instruction.conditionTrue(map);
            if (isTrue) {
                instruction.exucute(map, allEverCreatedValues);
            }
        }

        int max = map.values()
                .stream()
                .mapToInt(x -> x)
                .max().getAsInt();

        int maxOfAllTimes = allEverCreatedValues.stream()
                .mapToInt(x -> x)
                .max().getAsInt();

        System.out.println(max);
        System.out.println(maxOfAllTimes);
    }



}

class Instruction {

    private final int conditionRight;
    private final String registerToChange;
    private final Operation operation;
    private final int operand;
    private final String conditionOperator;
    private final String registerForConditioning;

    public Instruction(String[] args) {
        registerToChange = args[0];
        operation = Operation.valueOf(args[1].toUpperCase());
        operand = Integer.valueOf(args[2]);
        registerForConditioning = args[4];
        conditionOperator = args[5];
        conditionRight = Integer.valueOf(args[6]);
    }

    public boolean conditionTrue(Map<String, Integer> registerMap) {
        Integer registerValue = registerMap.get(registerForConditioning);
        switch (conditionOperator) {
            case "==":
                return Objects.equals(registerValue, conditionRight);
            case "<" :
                return registerValue < conditionRight;
            case "<=" :
                return registerValue <= conditionRight;
            case ">" :
                return registerValue > conditionRight;
            case ">=" :
                return registerValue >= conditionRight;
            case "!=" :
                return !Objects.equals(registerValue, conditionRight);
            default: throw new RuntimeException("unknown operator: " + conditionOperator);
        }
    }

    public String getRegisterToChange() {
        return registerToChange;
    }

    public Operation getOperation() {
        return operation;
    }

    public int getOperand() {
        return operand;
    }

    public void exucute(Map<String, Integer> map, Set<Integer> allEverCreatedValues) {
        Integer input1 = map.get(registerToChange);
        int result = operation.apply(input1, operand);
        map.put(registerToChange, result);
        allEverCreatedValues.add(result);
    }

    enum Operation {
        INC((x, y) -> x + y),
        DEC((x, y )-> x - y);

        BiFunction<Integer, Integer, Integer> func;

        Operation(BiFunction<Integer, Integer, Integer> func) {
            this.func = func;
        }

        public int apply(int x, int y) {
            return func.apply(x, y);}
    }

}