package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.ArrayList;

public class BopCode extends ByteCode {

    private String name;
    private String operator;

    public void init(ArrayList<String> code){
        this.name = code.get(0).trim();//Store Name
        this.operator = code.get(1).trim();//Store given operation
    }
    public void execute(VirtualMachine vm){

        int secondOperand = vm.popRunStack();//Pop Operand 2
        int firstOperand = vm.popRunStack();//Pop Operand 1
        int result = 0;//Store Result Here

        switch(this.operator){
            case "+":
                result = firstOperand + secondOperand;
                break;
            case "-":
                result = firstOperand - secondOperand;
                break;
            case "/":
                result = firstOperand / secondOperand;
                break;
            case "*":
                result = firstOperand * secondOperand;
                break;
            case "==":
                result = (firstOperand == secondOperand) ? 1 : 0;
                break;
            case "!=":
                result = (firstOperand != secondOperand) ? 1 : 0;
                break;
            case "<=":
                result = (firstOperand <= secondOperand) ? 1 : 0;
                break;
            case ">":
                result = (firstOperand > secondOperand) ? 1 : 0;
                break;
            case ">=":
                result = (firstOperand >= secondOperand) ? 1 : 0;
                break;
            case "<":
                result = (firstOperand < secondOperand) ? 1 : 0;
                break;
            case "|":
                result = (this.getTrueFalse(firstOperand) || this.getTrueFalse(secondOperand)) ? 1 : 0;
                break;
            case "&":
                result = (this.getTrueFalse(firstOperand) && this.getTrueFalse(secondOperand)) ? 1 : 0;
                break;
        }
        vm.pushRunStack(result);
    }
    public String getArgs(){return this.operator + "";}
    public String getByteCodeName(){return this.name;}
    public void setFuncMemoryAddress(int addr){}

    public String toString() {
        return (this.name + " "+ this.operator);
    }

    private boolean getTrueFalse(int operand){//Helper method for execute method
        try{
            if(operand == 1){
                return true;
            }else if(operand == 0){
                return false;
            }else{
                throw new RuntimeException();
            }
        }catch(RuntimeException e){
            System.out.println("[BopCode.java->getTrueFalse] tried to determine if # was true or false but was neither\n" + e.getMessage());
        }
        return false;
    }

}
