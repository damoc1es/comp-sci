package Logic;

import Entities.*;
import Enums.OperationType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static Enums.OperationType.*;
import static Enums.OperationType.DIVIDE;

public class ExpressionParser {
    public static boolean isValidNumber(String string) {
        return string.matches("-?[0-9]+[+-]([0-9]+\\*)?i");
    }

    public static ComplexNumber parseNumber(String validNumber) throws Exception {
        if(!isValidNumber(validNumber))
            throw new Exception("Invalid number.");

        int sign_re = 1, sign_im = 1;
        int re, im, k;

        if(validNumber.charAt(0) == '-') {
            sign_re = -1;
            validNumber = validNumber.substring(1);
        }

        if(validNumber.indexOf('-') != -1) {
            sign_im = -1;
            k = validNumber.indexOf('-')+1;
        } else {
            k = validNumber.indexOf('+')+1;
        }

        re = Integer.parseInt(validNumber.substring(0, k-1));
        validNumber = validNumber.substring(k);

        if(validNumber.equals("i")) {
            im = 1;
        } else {
            k = validNumber.indexOf('*');
            im = Integer.parseInt(validNumber.substring(0, k));
        }

        return new ComplexNumber(re*sign_re, im*sign_im);
    }

    public static ComplexExpression parseExpression(String validExpression) throws Exception {
        String[] strings = validExpression.split(" ");
        OperationType operation;
        switch (strings[1]) {
            case "+" -> operation = ADDITION;
            case "*" -> operation = MULTIPLY;
            case "-" -> operation = SUBTRACT;
            case "/" -> operation = DIVIDE;
            default -> throw new Exception("Not valid");
        }

        for(int i=3; i<strings.length; i+=2) {
            if(!Objects.equals(strings[1], strings[i]))
                throw new Exception("Not valid.");
        }

        List<ComplexNumber> args = new ArrayList<>();
        for(int i=0; i<strings.length; i+=2) {
            args.add(parseNumber(strings[i]));
        }
        return ExpressionFactory.getInstance().createExpression(operation, args);
    }
}
