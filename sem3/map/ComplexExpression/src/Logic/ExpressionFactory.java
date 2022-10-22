package Logic;

import Entities.*;
import Enums.OperationType;

import java.util.List;

public class ExpressionFactory {
    private static ExpressionFactory instance = null;

    private ExpressionFactory() {}

    public ComplexExpression createExpression(OperationType operation, List<ComplexNumber> args) {
        ComplexExpression expr = null;

        switch (operation) {
            case ADDITION -> expr = new ComplexAddExpr();
            case MULTIPLY -> expr = new ComplexMultiplyExpr();
            case SUBTRACT -> expr = new ComplexSubtractExpr();
            case DIVIDE -> expr = new ComplexDivideExpr();
        }

        for (ComplexNumber arg : args) {
            expr.add(arg);
        }
        return expr;
    }

    public static ExpressionFactory getInstance() {
        if(instance == null) {
            instance = new ExpressionFactory();
        }
        return instance;
    }
}
