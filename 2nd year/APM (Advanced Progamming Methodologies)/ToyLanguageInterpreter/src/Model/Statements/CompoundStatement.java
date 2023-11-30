package Model.Statements;

import Datastructures.MyIStack;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.ProgramState.ProgramState;

public class CompoundStatement implements Statement {
    private final Statement first;
    private final Statement second;

    public CompoundStatement(Statement first, Statement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        MyIStack<Statement> stack = state.getExecutionStack();
        stack.push(this.second);
        stack.push(this.first);
        return state;
    }

    @Override
    public Statement deepCopy() throws StatementException {
        try {
            return new CompoundStatement(this.first.deepCopy(), this.second.deepCopy());
        } catch(ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "(" + this.first.toString() + ";" + this.second.toString() + ")";
    }
}
