package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.ArrayList;

public class LabelCode extends ByteCode {

    private String name;
    private String label;

    public void init(ArrayList<String> code){
        this.name = code.get(0);//Save Name
        this.label = code.get(1);//Save Label
    }
    public void execute(VirtualMachine vm){}//LABEL performs no actions
    public String getByteCodeName(){
        return this.name;
    }
    public String getArgs(){
        return this.label;
    }
    public void setFuncMemoryAddress(int addr){}
    public String toString() {return this.name + " " + this.label;}
}
