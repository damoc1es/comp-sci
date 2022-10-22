package Entities;

public class ComplexSubtractExpr extends ComplexExpression {
    @Override
    public ComplexNumber executeOneOperation() {
        ComplexNumber res = new ComplexNumber(0, 0);

        for(ComplexNumber number : args) {
            res = ComplexNumber.subtract(res, number);
        }

        return res;
    }
}
