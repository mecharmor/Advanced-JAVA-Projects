package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.ArrayList;

public class DumpCode extends ByteCode {

    private String name;
    private String arg;
    private boolean status = false;//Flag Dump Status

    public void init(ArrayList<String> code){
        this.name = code.get(0);//Save Name
        this.arg = code.get(1);//Save status type ON/OFF
        //Check
        if(code.get(1).equalsIgnoreCase("ON")){
            this.status = true;//Dumping is now on
            System.out.println("[DumpCode.java] Dumping is ON (Set by init method)");
        }
    }
    public void execute(VirtualMachine vm){
        vm.dumpRunStackStatus(this.status);//This will tell the VM to dump or not
    }

    public String getArgs(){return this.arg;}
    public String getByteCodeName(){return this.name;}
    public void setFuncMemoryAddress(int addr){}
    public String toString() {return this.name + " " + this.arg;}

}
