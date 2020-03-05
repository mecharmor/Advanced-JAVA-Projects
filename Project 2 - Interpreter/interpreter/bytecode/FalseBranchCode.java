package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.ArrayList;

public class FalseBranchCode extends ByteCode {

    private String name;
    private String arg;
    private int functionNameReferenceAddress;//Label Address

    public void init(ArrayList<String> code){
        this.name = code.get(0); //Store Name
        this.arg = code.get(1); //Store Function Name
    }
    public void execute(VirtualMachine vm){

        int poppedValue = vm.popRunStack();

        if(poppedValue == 0){
            vm.setPC(functionNameReferenceAddress);//Jump to this label
        }
    }

    public String getArgs(){return this.arg;}
    public String getByteCodeName(){return this.name;}
    public void setFuncMemoryAddress(int addr){
        this.functionNameReferenceAddress = addr;
    }
    public String toString() {return this.name + " " + this.arg;}

}
