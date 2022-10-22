package Entities;

public class ComplexAddExpr extends ComplexExpression {
    @Override
    public ComplexNumber executeOneOperation() {
        ComplexNumber res = new ComplexNumber(0, 0);

        for(ComplexNumber number : args) {
            res = ComplexNumber.add(res, number);
        }

        return res;
    }
}
