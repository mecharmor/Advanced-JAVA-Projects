package edu.csc413.calculator.evaluator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.Iterator;
import edu.csc413.calculator.evaluator.Operand;//check for operands
import edu.csc413.calculator.operators.Operator;//check for operators

public class EvaluatorUI extends JFrame implements ActionListener {

    private TextField txField = new TextField();
    private Panel buttonPanel = new Panel();

    private Deque<String> dequeUserInput;

    // total of 20 buttons on the calculator,
    // numbered from left to right, top to bottom
    // bText[] array contains the text for corresponding buttons
    private static final String[] bText = {
        "7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3",
        "*", "0", "^", "=", "/", "(", ")", "C", "CE"
    };

    /**
     * C  is for clear, clears entire expression
     * CE is for clear expression, clears last entry up until the last operator.
     */
    private Button[] buttons = new Button[bText.length];

    public static void main(String argv[]) {
        EvaluatorUI calc = new EvaluatorUI();
    }

    public EvaluatorUI() {
        setLayout(new BorderLayout());
        this.txField.setPreferredSize(new Dimension(600, 50));
        this.txField.setFont(new Font("Courier", Font.BOLD, 28));

        add(txField, BorderLayout.NORTH);
        txField.setEditable(false);

        add(buttonPanel, BorderLayout.CENTER);
        buttonPanel.setLayout(new GridLayout(5, 4));

        //create 20 buttons with corresponding text in bText[] array
        Button bt;
        for (int i = 0; i < EvaluatorUI.bText.length; i++) {
            bt = new Button(bText[i]);
            bt.setFont(new Font("Courier", Font.BOLD, 28));
            buttons[i] = bt;
        }

        //add buttons to button panel
        for (int i = 0; i < EvaluatorUI.bText.length; i++) {
            buttonPanel.add(buttons[i]);
        }

        //set up buttons to listen for mouse input
        for (int i = 0; i < EvaluatorUI.bText.length; i++) {
            buttons[i].addActionListener(this);
        }

        setTitle("Calculator");
        setSize(400, 400);
        setLocationByPlatform(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        //Instantiate Deque
        dequeUserInput = new LinkedBlockingDeque<>();
    }

    public void actionPerformed(ActionEvent arg0) {
        String token = arg0.getActionCommand();//store

        if(token.equals("CE")){
            dequeUserInputCE();//remove last entry --> comparable to UNDO button
        }else if(token.equals("C")){
            dequeUserInputClear();
        }else if(dequeUserInput.isEmpty()){//first element
            if(!token.equals("=")){
                dequeUserInput.add(token);
            }else{
                return;
            }
        }else if(Operand.check(dequeUserInput.getLast()) && Operand.check(token)){ //if operand && operand --> merge
            dequeUserInputMergeLast(token);// ex| 21 != 1 and 2
        }else if(token.equals("=") && !dequeUserInput.isEmpty() ){
            int result = dequeUserInputEvaluate();//calculate result
            dequeUserInputClear();//clean
            txField.setText(Integer.toString(result));//show answer
            return;
        }else{
            dequeUserInput.addLast(token);//insert
        }
        dequeUserInputUpdateDisplay();//Update Display
    }
    private void dequeUserInputCE(){
        //remove last deque element until empty
        if(!dequeUserInput.isEmpty()){
            System.out.println("CE successful, removed: " + dequeUserInput.removeLast());
        }else{
            System.out.println("Cannot CE anymore because entries are all gone");
        }
    }
    private void dequeUserInputMergeLast(String token){
        //if last deque element == operand && token == operand --> merge both
        try{
            String temp = dequeUserInput.removeLast();//store
            dequeUserInput.addLast(temp + token);//merge
        }catch(RuntimeException e){
            System.out.println("Merge Failed, deque was empty \n");//fail
        }
    }
    private void dequeUserInputUpdateDisplay(){
        //display all deque items to calculator txField
        Iterator<String> it = dequeUserInput.iterator();
        StringBuilder token = new StringBuilder();

        while(it.hasNext()){
            token.append(it.next());//Build
        }
        txField.setText(token.toString());//Display
    }
    private void dequeUserInputClear(){
        dequeUserInput.clear();
        txField.setText("");
    }
    private int dequeUserInputEvaluate(){
        Iterator<String> it = dequeUserInput.iterator();
        StringBuilder token = new StringBuilder();

        while(it.hasNext()){
            token.append(it.next());//Build
        }

        Evaluator ev = new Evaluator();
        int result = ev.eval(token.toString());//store
        System.out.println("result: " + result);//debugging
        return result;
    }
}
