package guicalculator;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The calculator panel
 * @author vu0081
 */
public class CalculatorPanel extends JPanel {

    /**
     * Define instance variables
     */
    private final int CALC_WIDTH = 250;
    private final int CALC_HEIGHT = 235;

    private JLabel result;
    private JButton[] numbers = new JButton[10]; //array that stores number buttons
    private JButton[] operators = new JButton[10]; //array that stores operator buttons
    private String[] ops = {"*","+","/","=","C","-","mc","m+","m-","mr"}; //array of ops symbols

    private float num1, num2 = 0; //variables to store 2 numbers
    private char op; //variable to store the operator
    private float memory = 0;
    private boolean startingNumber = true; //to control the beginning of a number


    /**
     * Constructor for the Calculator Panel: Sets up the GUI
     */
    public CalculatorPanel() {
        this.setBackground(Color.lightGray);
        this.setPreferredSize(new Dimension(CALC_WIDTH, CALC_HEIGHT));
        result = new JLabel("0", JLabel.RIGHT);
        result.setPreferredSize(new Dimension(CALC_WIDTH - 5, 50));
        result.setFont(new Font("Helvetica", Font.BOLD, 40));
        result.setBackground(Color.WHITE);
        result.setOpaque(true);
        this.add(result);

        /** Set buttons of the calculator*/
        for (int i = 0; i<10; i++){
            // set NUMBER BUTTONS
            numbers[i] = new JButton(String.valueOf(i)); // set number for the buttons
            numbers[i].setPreferredSize(new Dimension(55, 30)); //set size for the buttons
            numbers[i].setFont(new Font("Helvetica", Font.BOLD, 15)); //set font for the number
            numbers[i].setForeground(Color.BLACK); //set color for the number
            //associate listener to each NUMBER button
            numbers[i].addActionListener(new ButtonListener());

            // set OPERATOR BUTTONS
            operators[i] = new JButton(ops[i]); //assign button with its respective ops symbol
            operators[i].setPreferredSize(new Dimension(55, 30)); //set size for the buttons
            operators[i].setFont(new Font("Helvetica", Font.BOLD, 13));// 15 //set font for the buttons
            // set color for the number
            if (i == 4) // 'C' button
                operators[i].setForeground(Color.RED); //set C button to RED
            else //other buttons
                operators[i].setForeground(Color.BLUE); //set other buttons to BLUE
            //associate listener to each OPERATOR button
            operators[i].addActionListener(new ButtonListener());
        }

        /** Add buttons of the calculator*/
        //mc, m+, m-, mr located in the 1st row
        for (int i = 6; i <10; i++){ add(operators[i]); }

        //7,8,9 located in the 2nd row
        for (int i = 7; i < 10; i++){ add(numbers[i]); }
        add(operators[0]);//add operator * in the 2nd row

        //4,5,6 located in the 3rd row
        for (int i = 4; i < 7; i++){ add(numbers[i]); }
        add(operators[1]);//add operator + in the 3rd row

        //1,2,3 located in the 4th row
        for (int i = 1; i < 4; i++){ add(numbers[i]);}
        add(operators[2]);//add operator / in the 4th row

        add(numbers[0]);//add number 0 located in the 5th row
        // add =,C,- operators in the fifth row
        for (int i = 3; i < 6; i++){ add(operators[i]); }
 
    }

    
    /**
     * Define the calculate method
     * Perform the calculations on <i>num1</i> and <i>num2</i> depending on
     * the operation <i>op</i>
     * @param op the operation
     * @param num1 the first number of the calculation
     * @param num2 the second number of the calculation
     * @return the result of the calculation
     */
    private float calculate(char op, float num1, float num2){
        switch (op) {
            case '*' -> num1 *= num2;
            case '/' -> num1 /= num2;
            case '+' -> num1 += num2;
            case '-' -> num1 -= num2;
        }
        return num1; //return the result of the calculation
    }



    /**
     * Define the private inner class ButtonListener
     */
    private class ButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();//determine the pressed button
            String button = String.valueOf(command.charAt(0));//identify the first character of the pressed button
            switch (button) {
                case "0" -> pressZero();
                case "1", "2", "3", "4", "5", "6", "7", "8", "9" -> pressNumber(button);
                case "*", "/", "+", "-" -> pressOperator(command.charAt(0));
                case "=" -> pressEqual();
                case "C" -> pressClear();
                case "m" -> pressMemory(command.charAt(1));//identify which memory function is pressed
            }

        }

        /**
         * Method for pressing number 0
         */
        void pressZero(){ // 0 is only added to the label if another number is already recorded
            if (!startingNumber) result.setText(result.getText()+"0");
        }

        /**
         * Method for pressing number 1 to 9
         * @param number the number
         */
        void pressNumber(String number){
            if (startingNumber){
                result.setText(number);
                startingNumber = false;
            } else{
                result.setText(result.getText()+number);
            }

        }

        /**
         * Method for pressing operators
         * @param operator the operator
         */
        void pressOperator(char operator){
            num1 = Float.parseFloat(result.getText()); //save first string to float variable num1
            op = operator; //save chosen operator to variable op
            startingNumber = true; // control the beginning of num2
        }

        /**
         * Method for pressing equal button
         */
        void pressEqual(){
            num2 = Float.parseFloat(result.getText());//save second string to variable num2
            float answer = calculate(op,num1,num2); //perform the calculation method and save the answer to variable result
            displayIntOrFloat(answer); //perform method of display the answer on the result (based on type int or float)
            num1 = num2 = 0;
        }

        /**
         *  Method for pressing Clear button
         */
        void pressClear(){
            result.setText("0"); //set the current text to 0
            startingNumber = true;
        }

        /**
         * Method for pressing memory buttons
         * @param memoryFunction the memory function
         */
        void pressMemory(char memoryFunction){
            switch (memoryFunction) {
                case '+' -> memory += Float.parseFloat(result.getText()); //add the value to the current value of memory
                case '-' -> memory -= Float.parseFloat(result.getText()); //subtract the current value of the JLabel from the float variable memory
                case 'r' -> displayIntOrFloat(memory);//display (based on type) the current value of memory on the JLabel
                case 'c' -> memory = 0;//set the value of memory to 0
            }
        }

        /**
         * Method to display integer or float type on the result
         * @param value the result
         */
        void displayIntOrFloat(float value){
            // if number is an integer
            if (value == (int) value) result.setText(String.valueOf((int)value));//display integer
            // if decimal number
            else result.setText(String.valueOf(value));//display float
        }
    } // end ButtonListener class
    
}
