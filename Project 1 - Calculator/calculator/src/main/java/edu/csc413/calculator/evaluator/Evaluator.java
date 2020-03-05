package edu.csc413.calculator.evaluator;



import edu.csc413.calculator.operators.*;

import java.lang.annotation.Documented;
import java.util.Stack;
import java.util.StringTokenizer;

public class Evaluator {
  private Stack<Operand> operandStack;//numbers
  private Stack<Operator> operatorStack;//addition, subtraction, etc...
  private StringTokenizer tokenizer;
  private static final String DELIMITERS = "+-*^/()";

  public Evaluator() {
    operandStack = new Stack<>();
    operatorStack = new Stack<>();
  }

  public int eval( String expression ) {
    String token;

    // The 3rd argument is true to indicate that the delimiters should be used
    // as tokens, too. But, we'll need to remember to filter out spaces.
    //expression = expression.replaceAll("\\s","");
    this.tokenizer = new StringTokenizer( expression, DELIMITERS, true );

    // initialize operator stack - necessary with operator priority schema
    // the priority of any operator in the operator stack other than
    // the usual mathematical operators - "+-*/" - should be less than the priority
    // of the usual operators

    while ( this.tokenizer.hasMoreTokens() ) {
      // filter out spaces
      if ( !( token = this.tokenizer.nextToken() ).equals( " " )) {
        // check if token is an operand
        if ( Operand.check( token )) {
          operandStack.push( new Operand( token ));
        } else {
          if ( ! Operator.check( token )) {
            System.out.println( "*****invalid token******" );
            throw new RuntimeException("*****invalid token******");
          }

          Operator newOperator = Operator.getOperator(token);// Use HashMap for update

          if(operatorStack.isEmpty()){
            //If the character is an operator, and the operator stack is empty then push it onto the operator stack.
            operatorStack.push(Operator.getOperator(token));
          }else if(token.charAt(0) == '('){
          //If the character is "(", then push it onto operator stack.
          operatorStack.push(newOperator);
          }else if(token.charAt(0) == ')'){
            // If the character is ")", then "process" as explained above until the corresponding "(" is encountered in operator stack.  At this stage POP the operator stack and ignore "(."
            while((operatorStack.peek()).priority() < 4){//when priority is 4 that means the "(" was found. so stop looping
              evaluateOnce();
            }
            operatorStack.pop(); // remove '(' from the operatorStack
          }else if((newOperator.priority() > operatorStack.peek().priority())) {
            //If the character is an operator and the operator stack is not empty, and the character's precedence is greater than the precedence of the stack top of operator stack, then push the character onto the operator stack.
            operatorStack.push(newOperator);
          }else{
            //Processing Operator
            while (operatorStack.peek().priority() >= newOperator.priority() && (operatorStack.peek()).priority() != 4){
              evaluateOnce();//perform computation
              if(operatorStack.isEmpty()){
                break;
              }
            }
            operatorStack.push( newOperator );
          }
        }
      }
    }
    while(!operatorStack.isEmpty()){
      evaluateOnce();//perform computation
    }
    return (operandStack.pop()).getValue();
  }

  private void evaluateOnce(){
    Operator oldOpr = operatorStack.pop();
    Operand op2 = operandStack.pop();
    Operand op1 = operandStack.pop();
    operandStack.push( oldOpr.execute( op1, op2 ));
  }

}
