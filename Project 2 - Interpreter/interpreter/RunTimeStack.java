package interpreter;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;



public class RunTimeStack {

    private ArrayList runTimeStack;
    private Stack<Integer> framePointer;

    public RunTimeStack() {
        runTimeStack = new ArrayList<>();
        framePointer = new Stack<>();
        // Add initial Frame Pointer, main is the entry
        // point of our language, so its frame pointer is 0.
        framePointer.add(0);
    }
    public void dump(){
        /**
         * Uses StringBuilder class to append to a large string that is outputted at the end of the function call
         */
        StringBuilder sb = new StringBuilder(); //String Builder to output
        int lastIndex = 0;

        for(int framePtr : framePointer){
            sb.append(dumpHelper(lastIndex, framePtr));
            lastIndex = framePtr;
        }
        if(lastIndex < runTimeStack.size()){
            sb.append(dumpHelper(lastIndex, runTimeStack.size()));
        }
        System.out.println(sb.toString());
    }
    private StringBuilder dumpHelper(int startIndex, int endIndex){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
            for(int i = startIndex; i < endIndex; i++){
                sb.append(runTimeStack.get(i) + ",");
            }
            if(sb.charAt(sb.length()-1) == ','){
                sb.deleteCharAt(sb.length()-1);//remove extra comma ','
            }
            sb.append("]");
        return sb;
    }
    public ArrayList<Integer> getRunStackAboveTopFrame(){
        /**
         * Returns all values inside top frame of runTimeStack
         */
        ArrayList<Integer> args = new ArrayList<>();//declare
        if(!framePointer.isEmpty()){
            int startIndex = this.framePointer.peek();//grab top frameIndex
            for(int i = startIndex; i < runTimeStack.size(); i++){
                args.add((Integer)this.runTimeStack.get(i));
            }
        }
        return args;
    }

    public int peek(){
        /**
         * Peeks top of runTimeStack
         */
        try{
            if(!runTimeStack.isEmpty()){
                return (Integer)runTimeStack.get(runTimeStack.size()-1);
            }else{
                throw new EmptyStackException();//Stack should not be empty to peek so throw exception here
            }
        }catch(EmptyStackException e){
            System.out.println("[RunTimeStack.java->peek] runTimeStack cannot peek an empty stack\n" + e.getMessage());
        }
        return -1;
    }
    public int pop(){
        /**
         * Pops top of runTimeStack
         */
        try{
            if(runTimeStack.size()-1 >= framePointer.peek()){//Don't pop past Frame Boundary. Only Error could be the >= instead of > because runTimeStack will be less than framePointer index
                int removedItem;
                if(!runTimeStack.isEmpty()){
                    removedItem = (Integer)runTimeStack.get(runTimeStack.size()-1);//save
                    runTimeStack.remove(runTimeStack.size()-1);//remove
                }else{
                    throw new EmptyStackException(); //Threw exception because there should be an element when pop is called
                }
                return removedItem;
            }
        }catch(EmptyStackException e){
            System.out.println("[RunTimeStack.java->pop] runTimeStack cannot pop an empty stack \n" + e.getMessage());
        }
        return -1;//Should not return this unless exception is caught
    }
    public int push(int val){
        runTimeStack.add(val);
        return val;
    }
    public void newFrameAt(int offSet){
        /**
         * Takes the newFrame offset and get's the correct index for it
         */
        int newOffSet = (runTimeStack.size())-offSet;//get index of frame given offSet
        try{
            if(newOffSet >= 0){//out of bounds of stack
                framePointer.push(newOffSet);
            }else{
                throw new IndexOutOfBoundsException();
            }
        }catch(IndexOutOfBoundsException e){
            System.out.println("[RunTimeStack.java->newFrameAt] offSet is too large for runTimeStack \n" + e.getMessage());
        }
    }
    public void popFrame(){
        /**
         * we pop the top of frame when we return from a function.
         * Before popping, the functions return value is at the
         * top of the stack, so we'll save the value, then pop the top frame and then push the return value back
         * onto the stack. It is assumed return values are at the top of the stack.
         */
        int savedReturnValue;
        try{
            if(!framePointer.isEmpty()){
                savedReturnValue = (Integer)runTimeStack.get((Integer)runTimeStack.size()-1); //get return value
                runTimeStack.remove(runTimeStack.size()-1);//pop return value

                int framePointerIndex = framePointer.pop();//pop frame off of stack
                while((runTimeStack.size()) >  framePointerIndex) {//keep popping until framePointerIndex & above is gone
                    //keep removing until the frame pointer item is removed
                    runTimeStack.remove(framePointerIndex);
                }
                runTimeStack.add(savedReturnValue);//put return value back onto the stack
            }else{
                throw new EmptyStackException();
            }
        }catch(EmptyStackException e){
            System.out.println("[RunTimeStack.java->popFrame] framePointer stack is empty" + e.getMessage());
        }
    }
    public int store(int offSet){
        /**
         * Used to store values into variables. Store will pop the top value of the stack and replace
         * the value at the given offset in the current frame. The value stored is returned.
         */
        int poppedValue = (Integer)runTimeStack.remove(runTimeStack.size()-1);
        runTimeStack.set(framePointer.peek()+offSet, poppedValue);

        return poppedValue;
    }
    public int load(int offSet){
        /**
         * Used to load variables onto the RuntimeStack from a
         * given offset within the current frame. This means we
         * will go to the offset in the current frame, copy the
         * value and push it to the top of the stack. No values
         * should be removed with load.
         */
        int newOffset;//New Index
        int copyOfOffsetValue = -1;//Copied Value
        if(!this.framePointer.isEmpty()){
          newOffset = (framePointer.peek()) + offSet;
        }else{
            newOffset = offSet;
        }
        try{
            if(newOffset < runTimeStack.size()){
                copyOfOffsetValue = (Integer)runTimeStack.get(newOffset);//copy offSet value
                runTimeStack.add(copyOfOffsetValue);//copy to top of stack
            }else{
                throw new IndexOutOfBoundsException();
            }
        }catch(IndexOutOfBoundsException e){
            System.out.println("[RunTimeStack->load] offset went past the top of the runTimeStack\n" + e.getMessage());
        }
        return copyOfOffsetValue;
    }
    public Integer push(Integer val){
        runTimeStack.add(val);
        return val;
    }
}