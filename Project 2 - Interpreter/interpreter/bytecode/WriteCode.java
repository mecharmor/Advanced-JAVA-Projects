package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.ArrayList;

public class WriteCode extends ByteCode {

    private String name;

    public void init(ArrayList<String> code){
        this.name = code.get(0);//Save name
    }
    public void execute(VirtualMachine vm){
        System.out.println(vm.peekRunStack());
    }
    public String getArgs(){return "";}
    public String getByteCodeName(){return this.name;}
    public void setFuncMemoryAddress(int x){}
    public String toString() {return this.name;}
}
