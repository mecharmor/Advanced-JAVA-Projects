package interpreter;

import interpreter.bytecode.*;

import javax.management.openmbean.InvalidKeyException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;

public class Program {

    private ArrayList<ByteCode> program;

    public Program() {
        program = new ArrayList<>();
    }

    protected ByteCode getCode(int pc) {
        return this.program.get(pc);
    }

    public int getSize() {
        return this.program.size();
    }

    /**
     * This function should go through the program and resolve all addresses.
     * Currently all labels look like LABEL <<num>>>, these need to be converted into
     * correct addresses so the VirtualMachine knows what to set the Program Counter(PC)
     * HINT: make note what type of data-stucture bytecodes are stored in.
     *
     * @param program Program object that holds a list of ByteCodes
     */
    public void resolveAddrs() {
        /**
         * Resolve Address was solved by populating a HashMap with the function names ex| LABEL continue<<6>> and replacing continue<<6>> with the
         * proper memory address by setting the FuncMemoryAddress contained within ByteCode.
         * A Stack was used to store the function call ByteCodes that did not find a defined LABEL.
         * This allows the program to use one pass to store everything and any missed methods are then popped from the stack
         */
        Map<String, Integer> definedFunctions = new HashMap<>(); /** STORE ANY Functions Defined by ByteCode LABEL */
        Stack<ByteCode> noMatchFunctionStack = new Stack<>(); /** STORE ANY Functions that could not be found within definedFunctions during the first pass */

        //Loop through Every instruction in program
        int memoryAddress = 0;
       for(ByteCode currentByteCode : program){
           String currentName = currentByteCode.getByteCodeName();
           if(currentName.equals("LABEL")){ // if LABEL instruction then store in definedFunctions
                definedFunctions.put(currentByteCode.getArgs() , memoryAddress); // input should be: continue<<6>> , 10
           }else if(CodeTable.validLabelByteCode(currentName)) { //if current ByteCode uses a label or not {{{Validation for bad ByteCodes containing a label}}
                if(definedFunctions.containsKey(currentByteCode.getArgs())){
                    currentByteCode.setFuncMemoryAddress(definedFunctions.get(currentByteCode.getArgs())); // Set the memory address of the matched function
                }else{ //function was not in definedFunctions yet so we push to the stack to pop later
                    noMatchFunctionStack.push(currentByteCode);//push to stack
                }
           }
           memoryAddress++;
       }
       //After Loop we need to pop everything off of the Stack
        while(!noMatchFunctionStack.isEmpty()){
            ByteCode currentByteCode = noMatchFunctionStack.pop();
            String bcName = currentByteCode.getByteCodeName();//get ByteCode Name
            try{
                if(definedFunctions.containsKey(currentByteCode.getArgs())){
                    currentByteCode.setFuncMemoryAddress(definedFunctions.get(currentByteCode.getArgs())); //set memory address
                }else{
                    throw new InvalidKeyException();//ByteCode requests LABEL that does not exist
                }
            }catch(InvalidKeyException e){
                System.out.println("[Program.java->resolveAddrs] ByteCode requests LABEL that does not exist" + e.getMessage());
            }
        }
    }

    protected void addByteCode(ByteCode arg){
        this.program.add(arg);
    }




}
