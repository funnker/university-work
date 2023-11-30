package Model.Expressions;

import Datastructures.MyIDictionary;
import Datastructures.MyIHeap;
import Exceptions.ExpressionException;
import Model.Types.IntType;
import Model.Values.IntValue;
import Model.Values.Value;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.BiPredicate;

public class RelationalExpression implements Expression {
    private final Expression e1;
    private final Expression e2;
    private static final HashMap<Integer, BiPredicate<Integer, Integer>> operators = new HashMap<>();
    private static final HashMap<Integer, String> operatorsString = new HashMap<>();
    private final int operator;

    public RelationalExpression(Expression e1, Expression e2, int operator) {
        operators.put(1, (a, b) -> a < b);
        operatorsString.put(1, "<");
        operators.put(2, (a, b) -> a <= b);
        operatorsString.put(2, "<=");
        operators.put(3, (a, b) -> a > b);
        operatorsString.put(3, ">");
        operators.put(4, (a, b) -> a >= b);
        operatorsString.put(4, ">=");
        operators.put(5, Integer::equals);
        operatorsString.put(5, "==");
        operators.put(6, (a, b) -> !Objects.equals(a, b));
        operatorsString.put(6, "!=");
        this.e1 = e1;
        this.e2 = e2;
        this.operator = operator;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap heap) throws ExpressionException {
        Value v1, v2;
        v1 = e1.eval(table, heap);
        if(v1.getType().equals(new IntType())) {
            v2 = e2.eval(table, heap);
            if(v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getValue();
                n2 = i2.getValue();
                return new IntValue(RelationalExpression.operators.get(this.operator).test(n1, n2) ? 1 : 0);
            } else {
                throw new ExpressionException("Second operand is not an integer.");
            }
        } else {
            throw new ExpressionException("First operand is not an integer.");
        }
    }

    @Override
    public Expression deepCopy() throws ExpressionException {
        return new RelationalExpression(this.e1.deepCopy(), this.e2.deepCopy(), this.operator);
    }

    public String toString() {
        return this.e1.toString() + " " + RelationalExpression.operatorsString.get(this.operator) + " " + this.e2.toString();
    }
}
