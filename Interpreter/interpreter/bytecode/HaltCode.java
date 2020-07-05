package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.ArrayList;

public class HaltCode extends ByteCode {

    private String name;

    public void init(ArrayList<String> code){
        this.name = code.get(0);
    }
    public void execute(VirtualMachine vm){
        vm.setRunningStatus(false);//Stop Program
    }
    public String getArgs(){return "";}
    public String getByteCodeName(){return this.name;}
    public void setFuncMemoryAddress(int addr){}
    public String toString() {return this.getByteCodeName();}
}
