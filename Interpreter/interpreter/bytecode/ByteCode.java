package interpreter.bytecode;

import java.util.ArrayList;
import interpreter.VirtualMachine;

public abstract class ByteCode {
    /**
     * init() : Takes in an array list of arguments and initializes the ByteCode values similar to a constructor
     * execute(): Takes a reference to the virtual machine class and performs necessary actions regarding that specific byteCodes functions
     * getByteCodeName() : returns the byteCode name of that specific byteCode which is initialized through the init method. Note: code.get(0) is ALWAYS the ByteCode name.
     * getArgs() : returns the argument set for that specific byteCode. Note: Only used in a few cases
     * setFuncMemoryAddress() : Used to keep track of LABEL declaration memory counter reference. This is used so ByteCodes can set the PC accordingly during function calls.
     */
    public abstract void init(ArrayList<String> code);//Passing entire argument, ex| : LABEL continue<<6>> or LIT 3
    public abstract void execute(VirtualMachine vm);

    public abstract String getByteCodeName(); //Return ByteCode name, such as: WRITE, or LIT, LOAD
    public abstract String getArgs(); //Return string argument containing arguments such as: c<id>
    public abstract void setFuncMemoryAddress(int addr);


}
