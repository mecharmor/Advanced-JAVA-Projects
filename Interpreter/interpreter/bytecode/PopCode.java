package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.ArrayList;

public class PopCode extends ByteCode {

    private String name;
    private int arg = 0;

    public void init(ArrayList<String> code){
        this.name = code.get(0);//Save name
        this.arg = Integer.parseInt(code.get(1));//Save integer value
    }
    public void execute(VirtualMachine vm){
        //Note: if arg = 0 then for Loop will not pop
        for(int i = 0; i < this.arg; i++){
            vm.popRunStack();
        }
    }
    public String getArgs(){return this.arg + "";}
    public String getByteCodeName(){return this.name;}
    public void setFuncMemoryAddress(int x){}
    public String toString() {return this.name + " " + this.arg;}
}
