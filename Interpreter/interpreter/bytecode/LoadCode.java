package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.ArrayList;

public class LoadCode extends ByteCode {

    private String name;
    private int arg;
    private String variable = "";

    public void init(ArrayList<String> code){
        this.name = code.get(0);//Save name
        this.arg = Integer.parseInt(code.get(1));//Save integer value
        if(code.size() > 2){
            this.variable = code.get(2); //Save variable name if given
        }
    }
    public void execute(VirtualMachine vm){
        vm.loadRunStack(this.arg);//push value above frameStack onto the runTimeStack
    }
    public String getArgs(){return this.arg + "";}
    public String getByteCodeName(){return this.name;}
    public String toString() {
        return (this.name + " " + this.arg + " " + this.variable + "<load " + this.variable + ">");//Expected output: LOAD # char   <load char>
    }
    public void setFuncMemoryAddress(int x){}//Don't Need for this ByteCode

}
