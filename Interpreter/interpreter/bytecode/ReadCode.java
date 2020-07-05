package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.ArrayList;
import java.util.Scanner;

public class ReadCode extends ByteCode {

    private String name;
    private int arg;

    public void init(ArrayList<String> code){
        this.name = code.get(0);
    }
    public void execute(VirtualMachine vm){
        /**
         * Read User Input and verify given value is type integer
         */
        Scanner scanner = new Scanner(System.in);

        this.prompt();

        while(!scanner.hasNextInt()){//Validate
            this.prompt();
            scanner.next();
        }
        this.arg = vm.pushRunStack(scanner.nextInt());//Save
    }
    public String getArgs(){return this.arg + "";}
    public String getByteCodeName(){return this.name;}
    public void setFuncMemoryAddress(int x){}
    public String toString() {return this.name + " " + this.arg;}
    private void prompt(){//Message to the user
        System.out.print("Please enter an Integer Value: ");
    }

}
