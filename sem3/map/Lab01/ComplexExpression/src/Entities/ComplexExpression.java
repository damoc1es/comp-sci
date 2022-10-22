package Entities;

import Enums.OperationType;

import java.util.ArrayList;
import java.util.List;

public class ComplexExpression {
    public List<ComplexNumber> args;
    public OperationType operation;

    public ComplexExpression(OperationType operation) {
        args = new ArrayList<>();
        this.operation = operation;
    }

    public ComplexExpression(OperationType operation, List<ComplexNumber> list) {
        args = new ArrayList<>();
        args.addAll(list);
        this.operation = operation;
    }

    public void add(ComplexNumber x) {
        args.add(x);
    }

    private ComplexNumber addEverything() {
        ComplexNumber res = new ComplexNumber(0, 0);
        for(ComplexNumber number : args) {
            res = ComplexNumber.add(res, number);
        }
        return res;
    }

    private ComplexNumber subtractEverything() {
        ComplexNumber res = new ComplexNumber(0, 0);
        for(ComplexNumber number : args) {
            res = ComplexNumber.subtract(res, number);
        }
        return res;
    }

    private ComplexNumber multiplyEverything() {
        ComplexNumber res = new ComplexNumber(1, 1);
        for(ComplexNumber number : args) {
            res = ComplexNumber.multiply(res, number);
        }
        return res;
    }

    private ComplexNumber divideEverything() {
        ComplexNumber a = args.get(0);
        ComplexNumber res = new ComplexNumber(a.getRe(), a.getIm());
        for(int i=1; i<args.size(); i++)
            res = ComplexNumber.divide(res, args.get(i));
        return res;
    }

    public ComplexNumber calculate() {
        return switch (operation) {
            case ADDITION -> addEverything();
            case SUBTRACT -> subtractEverything();
            case MULTIPLY -> multiplyEverything();
            case DIVIDE -> divideEverything();
        };
    }
}
