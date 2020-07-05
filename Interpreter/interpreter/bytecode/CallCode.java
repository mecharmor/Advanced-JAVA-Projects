package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.ArrayList;

public class CallCode extends ByteCode {

    private String name;
    private String functionName;
    private int functionNameReferenceAddress;
    private String toStringOutput;

    public void init(ArrayList<String> code){

        this.name = code.get(0).trim();//Store Name
        this.functionName = code.get(1).trim();//Store function name

    }
    public void execute(VirtualMachine vm){

        vm.pushReturnAddrs(vm.getPC());//Push current pc+1 to returnAddress
        vm.setPC(this.functionNameReferenceAddress);//Jump to that particular method

        this.toStringImplementation(vm);//Get arguments from stack and build into string for output

    }

    public String getArgs(){return this.functionName;}
    public String getByteCodeName(){return this.name;}
    public void setFuncMemoryAddress(int addr){this.functionNameReferenceAddress = addr;} //save memory address of called function

    public String toString() {
        return (this.toStringOutput);
    }

    private void toStringImplementation(VirtualMachine vm){
        //toString implementation
        StringBuilder sb = new StringBuilder();
        ArrayList<Integer> args = vm.getRunStackAboveTopFrame();//get ArrayList of arguments

        if(this.functionName.contains("<<")){
            sb.append(this.name + " " + this.functionName + "    " + this.functionName.substring(0, this.functionName.indexOf('<')) + "(");//if CALL continue<<6>> we will modify to: CALL continue<<6>> continue(
        }else{
            sb.append(this.name + " " + this.functionName + "    " + this.functionName + "(");//if CALL arg we will modify to: CALL arg arg(
        }

        for(Integer tempArg : args){//Loop through all arguments received from top of stack
            sb.append(tempArg + ",");//append #,
        }
        if(sb.charAt(sb.length()-1) == ','){
            sb.deleteCharAt(sb.length()-1);//remove extra comma
        }
        sb.append(")");
        this.toStringOutput = sb.toString();
    }
}
