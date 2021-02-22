package guicalculator;


/*
 * Simple calculator that performs standard operations of +, -, x, /, =, C and
 * incorporates standard memory functions of m+, m-, mc, mr
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The calculator panel
 * @author 
 */
public class CalculatorPanel extends JPanel {

    /**
     * Define instance variables
     */
    // declare height and width of the calculator
    final int CACL_WIDTH = 250;
    final int CACL_HEIGHT = 225;
    
    private JLabel result;
    private JButton[] numbers = new JButton[10]; //array that stores number buttons
    private JButton[] operators = new JButton[10]; //array that stores operatore buttons
    private String[] ops = {"*","+","/","=","C","-","mc","m+","m-","mr"}; //array of ops symbol
    
    private float num1, num2; //variables to store 2 numbers
    private char op; //variable to store the opertator
    private float memory = 0;
    private boolean num_start = false; //to control the start of second number
    
    /**
     * Constructor for the Calculator Panel: Sets up the GUI
     */
    public CalculatorPanel() {
        setBackground(Color.LIGHT_GRAY); //set background color to light grey
        setPreferredSize(new Dimension(CACL_WIDTH, CACL_HEIGHT));//set size of the calculator
        
        //Set the result for the calculator
        result = new JLabel("0", JLabel.RIGHT); //set number 0 to be displayed in the beginning of the program,
                                                //and set display alignment to the right side
        result.setPreferredSize(new Dimension((CACL_WIDTH-5), 50));//set size of the result
        result.setFont(new Font("Helvetica", Font.BOLD, 40));//set font, style, size of the display in result
        result.setOpaque(true);//set the result not to be transparent
        result.setBackground(Color.WHITE);//set result color to white
        add(result); //add result to the panel
        
         
          
    /*************** Set buttons of the calculator **************/     
        // set result, size, font and color for number buttons
        for (int i = 0; i<10; i++){
            numbers[i] = new JButton(String.valueOf(i)); // set number for the buttons
            numbers[i].setPreferredSize(new Dimension(55, 30)); //set size for the buttons
            numbers[i].setFont(new Font("Helvetica", Font.BOLD, 15)); //set font for the number
            numbers[i].setForeground(Color.BLACK); //set color for the number
            numbers[i].addActionListener(new ButtonListener());//associate listener to each button
        }
        
        // set size, font and color for operater buttons
        for (int i = 0; i<10;i++){
            operators[i] = new JButton(ops[i]); //assign button with its respective symbol
            operators[i].setPreferredSize(new Dimension(55, 30)); //set size for the buttons
            operators[i].setFont(new Font("Helvetica", Font.BOLD, 15)); //set font for the buttons
            // set color for the number
            if (i == 4) // 'C' button
            {
                operators[i].setForeground(Color.RED); //set C button to RED
            }
            else //other buttons
            {
                operators[i].setForeground(Color.BLUE); //set other buttons to BLUE
            }
            operators[i].addActionListener(new ButtonListener());//associate listener to each button
        }      
        
        
    /*************** Add buttons to the panel **************/        
        
        for (int i = 6; i <10; i++){ //mc, m+, m-, mr located in the first row
            add(operators[i]);
        }
        for (int i = 7; i < 10; i++){ //7,8,9 located in the second row
            add(numbers[i]);
        }
        add(operators[0]);//add operator * in the second row
        for (int i = 4; i < 7; i++){ //4,5,6 located in the third row
            add(numbers[i]);
        }
        add(operators[1]);//add operator + in the second row
        for (int i = 1; i < 4; i++){ //1,2,3 located in the fourth row
            add(numbers[i]);
        }
        add(operators[2]);//add operator / in the second row
        add(numbers[0]);//add number 0 in loacted in the fifth row
        for (int i = 3; i < 6; i++){ // add =,C,- operators in the fifth row
            add(operators[i]);
        }
    
    }//end constructor CalcularPanel()
        

   
   
    /*****Perform the calculations on num1 and num2 depending on the operation op *****/
    private float calculate(char op, float num1, float num2){
        switch (op){
            case '*':
                num1 *= num2; //perform multiplication
                break;
            case '/': 
                num1 /= num2; //perform division
                break;
            case '+':
                num1 += num2; //perform addition
                break;
            case '-':
                num1 -= num2; //perform subtraction
                break;
        }
        return num1; //return the result of the calculation
    }    
    
    
    /**
     * Define the private inner class ButtonListener
     */
    private class ButtonListener implements ActionListener
       
    {   
        public void actionPerformed(ActionEvent event){
            String command = event.getActionCommand();//determine the pressed button
            char c = command.charAt(0); //identify the first character of the pressed button
            switch (c)
            {
                case'0':
                    pressZero(); //perform this method when 0 is pressed
                    break;
                case'1':
                case'2':
                case'3':
                case'4':
                case'5':
                case'6':
                case'7':
                case'8':
                case'9':
                    pressNumber(command);//perform this method when 1-9 is pressed
                    break;
                case'*':
                case'/':
                case'+':
                case'-':
                    pressOperator(command);//perform this method when an operator is pressed
                    break;
                case'=':
                    pressEqual();//perform this method when '=' is pressed
                    break;
                case'C':
                    pressClear();//perform this method when Clear is pressed
                    break;
                case'm':
                    pressMemory(command.charAt(1));//perform this method when a memory button is pressed 
                    break;                         //identify which memory function is pressed
            }
        }// end actionPerform()
                
    }// end ButtonListener class
    
    /*********** Method for pressing number 0 **********/
    private void pressZero(){
        if (num_start == false){ // this is not the first character of a number                   
            if (!(result.getText().equals("0"))){//make sure 0 is only be added after another number button was pressed
                result.setText(result.getText()+"0");//add 0 to the current string
            }
        }
    }
    
    /*********** Method for pressing number 1 to 9 **********/
    private void pressNumber(String command){
        //if clause to determine the start of the second number
        if(num_start == false){ // if this is not the first character of second string    
            if (result.getText().equals("0")){ //if this is the first character of first string
                result.setText(command.substring(0,1)); //start the string with pressed number 
            }
            else { // if this is the following characters of first string
                result.setText(result.getText()+command.substring(0,1)); //add the number to the current string
            }
        }
        else { // if this is the first character of second string    
            result.setText(command.substring(0,1));//start the second string with pressed number
            num_start = false; //announce the first character of second string has been added
        }
    }
    
    /*********** Method for pressing operators **********/
    private void pressOperator(String command){
        num1 = Float.parseFloat(result.getText()); //save first string to variable num1
        op = command.charAt(0); //save chosen operator to variable op
        num_start = true; //end first number, start second number
    }
    
    /*********** Method for pressing equal button **********/
    private void pressEqual(){
        num2 = Float.parseFloat(result.getText());//save second string to variable num2
        float answer = calculate(op,num1,num2); //perform the calculation method and save the answer to variable result
        displayIntOrFloat(answer); //perform method of display the answer on the result (based on type int or float)
    }
    
    /*********** Method for pressing Clear button **********/
    private void pressClear(){
        num1 = 0; //clear variable num1
        num2 = 0; //clear variable num2
        result.setText("0"); //set the current text to 0
    }
    
    /*********** Method for pressing memory buttons **********/
    private void pressMemory(char function){
        switch (function){
            case '+': //m+
                memory += Float.parseFloat(result.getText()); //add the value to the current value of memory
                 break;
            case '-': //m-
                memory -= Float.parseFloat(result.getText()); //subtract the current value of the JLabel from the float variable memory
                break;
            case 'r': //mr
                displayIntOrFloat(memory);//display (based on type) the current value of memory on the JLabel
                break;
            case 'c': //mc
                 memory = 0;//set the value of memory to 0
                 break;
        } 
    }
    
    /**** Method for displaying integer or float type on the result ****/
    private void displayIntOrFloat(float value){
        if (value%1==0){ // if number is an integer
            result.setText(String.valueOf((int)value));//display integer
        }
        else { // if decimal number
            result.setText(String.valueOf(value));//display float
        }
    }
}
