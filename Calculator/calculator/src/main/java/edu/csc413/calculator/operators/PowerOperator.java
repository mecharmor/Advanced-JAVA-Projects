package edu.csc413.calculator.operators;

import edu.csc413.calculator.evaluator.Operand;

public class PowerOperator extends Operator{

    public int priority(){return 3;}

    //op1 to the power of op2
    public Operand execute(Operand op1, Operand op2 ){

        return (new Operand(calculatePowerRecursive(op1.getValue(), op2.getValue())));

    }

    private int calculatePowerRecursive(int x, int pow){
        //Recursive Method to calculate the power of a value
        if(pow == 0){
            return 1;
        }else{
            return x*calculatePowerRecursive(x, pow-1);
        }
    }

}
