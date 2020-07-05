package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.ArrayList;

public class LitCode extends ByteCode {

    private String name;
    private int arg;
    private char variable = ' ';

    public void init(ArrayList<String> code){
        this.name = code.get(0);//Save name
        this.arg = Integer.parseInt(code.get(1));//Save integer value
        if(code.size() > 2){
            this.variable = code.get(2).charAt(0); //Store variable name if declared
        }
    }
    public void execute(VirtualMachine vm){
        vm.pushRunStack(arg);
    }
    public String getArgs(){return this.arg + "";}
    public String getByteCodeName(){return this.name;}
    public String toString() {
        return (this.name + " " + this.arg + " " + this.variable + "    int " + this.variable);
    }
    public void setFuncMemoryAddress(int x){}

}
