package Model.Statements;

import Datastructures.MyIDictionary;
import Datastructures.MyIHeap;
import Exceptions.DictionaryException;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Types.StringType;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseReadFile implements Statement {
    private final Expression expression;

    public CloseReadFile(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        MyIHeap heap = state.getHeap();

        Value value;
        try {
            value = this.expression.eval(symbolTable, heap);
        } catch(ExpressionException e) {
            throw new StatementException(e.getMessage());
        }

        try {
            if(!value.getType().equals(new StringType())) {
                throw new StatementException("Expression " + this.expression.toString() + " is not of type string.");
            }
            StringValue stringValue = (StringValue) value;
            if(!fileTable.search(stringValue.getValue())) {
                throw new StatementException("File " + stringValue.getValue() + " is not open.");
            }

            BufferedReader bufferedReader = fileTable.get(stringValue.getValue());

            fileTable.remove(stringValue.getValue());
            bufferedReader.close();
        } catch(IOException | DictionaryException e) {
            throw new StatementException(e.getMessage());
        }
        return state;
    }

    @Override
    public Statement deepCopy() throws StatementException, ExpressionException {
        try {
            return new CloseReadFile(this.expression.deepCopy());
        } catch(ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "closeRFile()";
    }
}
