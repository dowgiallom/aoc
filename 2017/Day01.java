class CaptchaSolver {

    int solve(String input) {
        int result = 0;
        int length = input.length();
        int[] nums = new int[length];

        if (length == 0) {
            return result;
        }

        for (int i = 0; i < input.length(); i++){
            nums[i] = input.charAt(i) - '0';
        }

        for (int i = 0; i < length - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                result += nums[i];
            }
        }

        if (nums[length - 1] == nums[0]) {
            result += nums[length - 1];
        }

        return result;
    }

    int solvePart2(String input) {
        int result = 0;
        int length = input.length();
        if (length % 2 != 0) {
            throw new RuntimeException("List with odd number of elements not legal.");
        }
        int halfway = length / 2;
        int[] nums = new int[length];

        if (length == 0) {
            return result;
        }

        for (int i = 0; i < length; i++){
            nums[i] = input.charAt(i) - '0';
        }

        for (int i = 0; i < length; i++) {
            int shifted;
            if (i + halfway < length) {
                shifted = i + halfway;
            } else {
                break;
            }

            if (nums[i] == nums[shifted]) {
                result += nums[i];
            }
        }

        return result * 2;
    }
}

class Examples {
    private static final String INPUT =
            "68376334795224855827459835293967497295464175589881588256882344699473595413912688278647235862566123233983921662578792917453912795352746426512649965615919588512125567186837411371179875287621488759761429629174886972298349197722423458299323141529413191327622485249495864168181327197661454464926326248274999448373741839963155646828842752761293142356422964355349521987483211496361289666375779728345952231649453711684539164893151811849653331845998998597991146881361717234517911759893792348815818755262456378627116779495435596139617246571678531183335956244163871445674244765586446362529159854137535962117184875192273872222899887357292312978286182636232921252574738118347521187637829623831872437381979223955675634257889137823684924127338433248519515211796732599314921611399736571277222546332369461136277417419794865524123989722492356536832313937597437717873787593849468836733642529378547151146397532997237439387663769334722979172954835154486382983716698212694357398153392926255272961384626131829678171219569288685597141132355322788254163923888378155573948753185423158997877718687642446457446643422536541238979761725496426292359382168535641216124211741896552562128941824172241913873537828976172738276983915232241451589421911121567228899853934667954786256223614621554618294467191255153395256524786159758429643756586457639177183891162214163549688595416893383194995824534247841414247526268212761954913719452114876764745799982792594753759626334319631191917894368116738893548797661111899664138398354818931135486984944719992393148681724116616741428937687985152658296679845474766477741553632712968679175356452987459761126437216758171182395219393289199148996813762849991484678429793578629331215796996751484375784895561682156658579887518746862371751372692472765217374791324656745291574784495299477362964676351148183676897122366838656342745944945275263617729359831466565694983217252594237828187612857523344265418227883219383138893873384775659548637662867572687198263688597865118173921615178165442133987362382721444844952715592955744739873677838847693982379696776";

    public static void main(String[]args){
        CaptchaSolver solver = new CaptchaSolver();

        String ex = "222";
        System.out.println("Result for '" + ex + "' is: '" + solver.solve(ex) + "'");
        ex = "00";
        System.out.println("Result for '" + ex + "' is: '" + solver.solve(ex) + "'");
        ex = "1234";
        System.out.println("Result for '" + ex + "' is: '" + solver.solve(ex) + "'");
        ex = "";
        System.out.println("Result for '" + ex + "' is: '" + solver.solve(ex) + "'");
        ex = "4545454";
        System.out.println("Result for '" + ex + "' is: '" + solver.solve(ex) + "'");

        System.out.println("Result for puzzle is:");
        System.out.println(solver.solve(INPUT));
        System.out.println("Result for puzzle part 2 is:");
        System.out.println(solver.solvePart2(INPUT));
    }

}

