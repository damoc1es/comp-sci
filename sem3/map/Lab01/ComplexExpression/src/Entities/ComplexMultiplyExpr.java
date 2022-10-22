package Entities;

public class ComplexMultiplyExpr extends ComplexExpression {
    @Override
    public ComplexNumber executeOneOperation() {
        ComplexNumber res = new ComplexNumber(1, 1);

        for(ComplexNumber number : args) {
            res = ComplexNumber.multiply(res, number);
        }

        return res;
    }
}
