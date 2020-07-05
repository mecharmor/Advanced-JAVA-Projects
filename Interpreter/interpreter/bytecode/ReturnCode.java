package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.ArrayList;

public class ReturnCode extends ByteCode {

    private String name;
    private String arg = " ";
    private String argBase = " ";
    private int topOfStack;

    public void init(ArrayList<String> code){

        this.name = code.get(0);//Save name
        if(code.size() > 1){//Label given?
            this.arg = code.get(1).trim();
            if(this.arg.contains("<<")){
                this.argBase = this.arg.substring(0, this.arg.indexOf('<'));//Grab base case for given label
            }
        }
    }
    public void execute(VirtualMachine vm){

        vm.popFrameRunStack();//pop the current frame and frame pointer, Note: This method saves the return value

        this.topOfStack = vm.popRunStack();//Save return value

        vm.pushRunStack(this.topOfStack);//push return value back on stack

        vm.setPC(vm.popReturnAddrs());//return to original function call
    }

    public String getArgs(){return this.arg + "";}
    public String getByteCodeName(){return this.name;}
    public String toString() {
        return (this.name + " " + this.arg + "  exit " + this.argBase + ":" + this.topOfStack);
    }

    public void setFuncMemoryAddress(int x){}//Don't Need for this ByteCode

}
