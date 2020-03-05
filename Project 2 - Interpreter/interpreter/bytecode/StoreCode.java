package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.ArrayList;

public class StoreCode extends ByteCode {

    private String name;
    private int arg;
    private char variable = ' ';
    private int topOfStack;

    public void init(ArrayList<String> code){

        this.name = code.get(0);//Get Code Name
        this.arg = Integer.parseInt(code.get(1));//Get integer value
        if(code.size() > 2){
            this.variable = code.get(2).charAt(0); //Store variable name if declared
        }
    }
    public void execute(VirtualMachine vm){
        this.topOfStack = vm.storeRunStack(this.arg);
    }
    public String getArgs(){return this.arg + "";}
    public String getByteCodeName(){return this.name;}
    public String toString() {
        return (this.name + " " + this.arg + "  " + this.variable + " = " + this.topOfStack);//Expected output: LOAD # char   <load char>
    }
    public void setFuncMemoryAddress(int x){}

}
