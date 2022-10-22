import Entities.ComplexExpression;
import Entities.ComplexNumber;
import Logic.ExpressionParser;

import java.io.File;
import java.util.Objects;
import java.util.Scanner;

import static Enums.OperationType.*;

public class Main {
    public static void main(String[] args) {
        tests();

        try {
            File file = new File("input.in");
            Scanner sc = new Scanner(file);

            while(sc.hasNextLine()) {
                String line = sc.nextLine();

                try {
                    ComplexExpression expr = ExpressionParser.parseExpression(line);
                    System.out.println(expr.calculate());
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            sc.close();
        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    public static void tests() {
        ComplexExpression expression = new ComplexExpression(ADDITION);
        expression.add(new ComplexNumber(2,3));
        expression.add(new ComplexNumber(5,-6));
        expression.add(new ComplexNumber(-2,1));

        assert ExpressionParser.isValidNumber("5-6*i");
        assert ExpressionParser.isValidNumber("-2+i");
        assert ExpressionParser.isValidNumber("2+3*i");

        try {
            String validExpression = "2+3*i + 5-6*i + -2+i";
            assert Objects.equals(ExpressionParser.parseExpression(validExpression).calculate(), new ComplexNumber(5, -2));
        } catch (Exception e) {
            assert false;
        }
    }
}