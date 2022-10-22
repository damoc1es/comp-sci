package Entities;

import java.util.ArrayList;
import java.util.List;

public abstract class ComplexExpression {
    public List<ComplexNumber> args;

    public ComplexExpression() {
        args = new ArrayList<>();
    }

    public ComplexExpression(ComplexNumber[] arr) {
        args = new ArrayList<>();
        args.addAll(List.of(arr));
    }

    public void add(ComplexNumber x) {
        args.add(x);
    }

    public abstract ComplexNumber executeOneOperation();

    public ComplexNumber execute() {
        return executeOneOperation();
    }
}
