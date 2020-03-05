
package interpreter;

import interpreter.bytecode.*; // import all classes inside bytecode

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;


public class ByteCodeLoader extends Object {

    private BufferedReader byteSource;
    
    /**
     * Constructor Simply creates a buffered reader.
     * YOU ARE NOT ALLOWED TO READ FILE CONTENTS HERE
     * THIS NEEDS TO HAPPEN IN LOADCODES.
     */
    public ByteCodeLoader(String file) throws IOException {
        this.byteSource = new BufferedReader(new FileReader(file));
    }
    public Program loadCodes()
    {
        /**
         Responsible for loading all bytecodes into the program object. Once all bytecodes have been loaded and initialized,
         the function then will request that the program object
         resolve all symbolic addresses before returning. An exampl of a before and after has been given in the Program section of this document
         */
        Program program = new Program(); //return object

        try{

            String currentLine;

            while((currentLine = this.byteSource.readLine()) != null){

                //Determine Class Type
                String className;
                if(currentLine.contains(" ")){
                  className = currentLine.substring(0, currentLine.indexOf(' ')); //Get the argument class name
                }else{
                    className = currentLine;
                }
                Class byteCodeInstruction = Class.forName("interpreter.bytecode." + CodeTable.getClassName(className.trim())); //get className Object
                //Determine Class arguments
                ArrayList<String> arguments = new ArrayList<String>(Arrays.asList(currentLine.split("\\s+"))); // Split Name and Arguments

                ByteCode bc = (ByteCode) byteCodeInstruction.getDeclaredConstructor().newInstance();
                bc.init(arguments);//call init function in current ByteCode and pass ArrayList of arguments
                program.addByteCode(bc);
            }
        }catch(IOException e){
            System.out.println("File IO Error " + e.getMessage());
        }catch(ClassNotFoundException e){
            System.out.println("Error Could not find class" + e.getMessage());
        }catch(NoSuchMethodException e){
            System.out.println("Could not instantiate upcasted object" + e.getMessage());
        }catch(Exception e){
            System.out.println("An Error Occured inside loadCodes()" + e.getMessage());
        }
       program.resolveAddrs();
       return program;
    }
}