package edu.csc413.calculator.operators;



import edu.csc413.calculator.evaluator.Operand;

import java.util.HashMap;

public abstract class Operator {
    // The Operator class should contain an instance of a HashMap
    // This map will use keys as the tokens we're interested in,
    // and values will be instances of the Operators.
    // ALL subclasses of operator MUST be in their own file.
    // Example:
    // Where does this declaration go? What should its access level be?
    // Class or instance variable? Is this the right declaration?
    // HashMap operators = new HashMap();
    // operators.put( "+", new AdditionOperator() );
    // operators.put( "-", new SubtractionOperator() );
    private static HashMap<String, Operator> operators;
    static{
        //public HashMap<String, Operator> operators = new HashMap<>();
        operators = new HashMap<>();
        operators.put("+", new AddOperator());
        operators.put("-", new SubtractOperator());
        operators.put("*", new MultiplyOperator());
        operators.put("/", new DivideOperator());
        operators.put("^", new PowerOperator());
        operators.put("(", new parenthesesOperator());
        operators.put(")", new parenthesesOperator());
    }
    
    public abstract int priority();
    public abstract Operand execute(Operand op1, Operand op2 );


    public static boolean check( String token ) {
        //if any non Operators are found, return false
        return (operators.containsKey(token) && token.length() == 1);
    }

    public static Operator getOperator(String token){

        if(check(token)){
            return operators.get(token);
        }
        return null;
    }

}
