package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.ArrayList;

public class GotoCode extends ByteCode {

    private String name;
    private String label;
    private int functionNameReferenceAddress;//Label Address

    public void init(ArrayList<String> code){
        this.name = code.get(0);//Save Name
        this.label = code.get(1);//Save Label
    }
    public void execute(VirtualMachine vm){
        vm.setPC(this.functionNameReferenceAddress);//Jump to this label
    }

    public String getArgs(){return this.label;}
    public String getByteCodeName(){return this.name;}
    public void setFuncMemoryAddress(int addr){
        this.functionNameReferenceAddress = addr;
    }
    public String toString() {return this.name + " " + this.label;}

}
