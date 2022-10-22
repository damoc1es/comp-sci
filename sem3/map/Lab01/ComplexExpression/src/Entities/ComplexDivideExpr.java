package Entities;

public class ComplexDivideExpr extends ComplexExpression {
    @Override
    public ComplexNumber executeOneOperation() {
        ComplexNumber a = args.get(0);
        ComplexNumber res = new ComplexNumber(a.getRe(), a.getIm());

        for(int i=1; i<args.size(); i++)
            res = ComplexNumber.divide(res, args.get(i));

        return res;
    }
}
