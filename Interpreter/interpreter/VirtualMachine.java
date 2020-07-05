package interpreter;

import interpreter.bytecode.*;

import java.util.ArrayList;
import java.util.Stack;

public class VirtualMachine {

    private RunTimeStack runStack;
    private Stack returnAddrs;
    private Program program;
    private int pc;
    private boolean isRunning;
    private boolean dumpRunTimeStack = true;

    protected VirtualMachine(Program program) {
        this.program = program;
    }

    public void executeProgram(){
        this.pc = 0;
        this.runStack = new RunTimeStack();
        this.returnAddrs = new Stack<Integer>();
        this.isRunning = true;
        while(isRunning){

            ByteCode code = program.getCode(pc);
            code.execute(this);
            if(dumpRunTimeStack && !(code.getByteCodeName()).equalsIgnoreCase("DUMP")){
                System.out.println(code);
                runStack.dump();
            }
            pc++;
        }
    }
    /**
     * runTimeStack methods
     */
    public int popRunStack(){return this.runStack.pop();}
    public int peekRunStack(){return this.runStack.peek();}
    public int pushRunStack(int val){return this.runStack.push(val);}
    public Integer pushRunStack(Integer val){return this.runStack.push(val);}
    public void newFrameAtRunStack(int offSet){this.runStack.newFrameAt(offSet);}
    public void popFrameRunStack(){this.runStack.popFrame();}
    public int storeRunStack(int offSet){return this.runStack.store(offSet);}
    public int loadRunStack(int offSet){return this.runStack.load(offSet);}
    public ArrayList<Integer> getRunStackAboveTopFrame(){return this.runStack.getRunStackAboveTopFrame();}//Get all values above current frame ex: ARGS 2 will push a frame down two and we need to get those values
    /**
     *Dump Flag
     */
    public void dumpRunStackStatus(Boolean status){ this.dumpRunTimeStack = status;}
    /**
     * Address Stack methods
     */
    public int popReturnAddrs(){
        return (Integer) this.returnAddrs.pop();
    }
    public void pushReturnAddrs(int currentPC){
        this.returnAddrs.push(currentPC);
    }
    /**
     * PC Methods
     */
    public void setPC(int newPC){
        this.pc = newPC;
    }
    public int getPC(){
        return this.pc;
    }
    /**
     * Stop program from running method (Used in HALT ByteCode)
     */
    public void setRunningStatus(boolean status){this.isRunning = status;}//Used By HaltCode?

}