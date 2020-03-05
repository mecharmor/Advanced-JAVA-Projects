package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.ArrayList;

public class ArgsCode extends ByteCode {
    /**
     * Used proir to calling a function, n = # of arguments
     * immediately followed by the CALL instruction
     * to set up a new frame n down from the top of the runtime stack. (VM.newFrame(int);
     */
    private String name;
    private int data;

    public void init(ArrayList<String> code){
        this.name = code.get(0);// Get Name
        this.data = Integer.parseInt(code.get(1));//Store integer value
    }
    public void execute(VirtualMachine vm){
        vm.newFrameAtRunStack(this.data);
    }
    public String getArgs(){return this.data + "";}
    public String getByteCodeName(){return this.name;}
    public void setFuncMemoryAddress(int addr){}

    public String toString() {
        return (this.name + " "+ this.data);
    }
}
