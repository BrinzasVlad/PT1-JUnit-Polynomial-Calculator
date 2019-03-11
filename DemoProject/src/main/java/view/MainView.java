package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//Normally, it would be wise to actually go through with the serialVersionUID.
//However, we never serialize anything in this class, nor will in the near future,
//so we can suppress it for now.
@SuppressWarnings("serial")
public class MainView extends JFrame {
	//The main window of the view for the Polynomials project.
	
	//Attributes that need to be accessed from outside
	
	//Input field for inputting polynomials
	private JTextField polynomialInputField;
	
	//Buttons to set currently used polynomials
	private JButton setPoly1Button;
	private JButton setPoly2Button;
	
	//Labels to display current polynomials on
	private JLabel poly1Label;
	private JLabel poly2Label;
	
	//Label for polynomial operation result
	private JLabel polyResultLabel;
	
	//Buttons for operations
	private JButton addButton;
	private JButton subtractButton;
	private JButton derivateP1Button;
	private JButton integrateP1Button;
	private JButton multiplyButton;
	private JButton divideButton;
	
	//Constructors
	public MainView() {
		initialize();
	}
	
	//Setup methods
	private void initialize() {
		setTitle("Polynomial Calculator");
        setSize(500, 300);
        setLocationRelativeTo(null); //Centered on screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        addElementsToPane(this.getContentPane());
	}
	
	private void addElementsToPane(Container pane) {
		JPanel polynomialsPanel;
		JPanel resultPanel;
		JPanel controlsPanel;

		//Polynomials Panel setup
		polynomialsPanel = generatePolynomialsPanel();
		
		//Result Panel setup
		resultPanel = generateResultPanel();
		
		//Controls Panel setup
		controlsPanel = generateControlsPanel();
		
		pane.add(polynomialsPanel, BorderLayout.NORTH);
		pane.add(resultPanel, BorderLayout.CENTER);
		pane.add(controlsPanel, BorderLayout.SOUTH);
	}
	
	private JPanel generatePolynomialsPanel() {
		JPanel polynomialsPanel = new JPanel(new GridLayout(0, 1));
		//Polynomials panel stores the current polynomials, as well as
		//the input field for new polynomials, usage instructions and Set buttons
		
		//We use an uneditable JTextArea as label to describe how the input should be given
		JTextArea usageLabel = new JTextArea("Polynomial input format: write the coefficients, "
								+ "starting from the lowest order to the highest order "
								+ "(set 0 where necessary). For example: x^3 - 17x + 5 "
								+ "is written as: 5 -17 0 1");
		usageLabel.setLineWrap(true);
		usageLabel.setWrapStyleWord(true);
		usageLabel.setEditable(false);
		usageLabel.setOpaque(false);
		polynomialsPanel.add(usageLabel);
		
		//There is a JTextField for receiving input from the user
		//This can't be declared here, since it needs to be visible
		//from the addListeners methods
		polynomialInputField = new JTextField();
		polynomialsPanel.add(polynomialInputField);
		
		//We have a JPanel for each of the two Polynomials that we might be working with at once
		JPanel poly1Panel = new JPanel(); //Flow Layout is okay
		JPanel poly2Panel = new JPanel();
		
		//Polynomial 1
		setPoly1Button = new JButton("Set P1");
		poly1Label = new JLabel("P1: 0");
		poly1Panel.add(setPoly1Button);
		poly1Panel.add(poly1Label);
		
		//Polynomial 2
		setPoly2Button = new JButton("Set P2");
		poly2Label = new JLabel("P2: 0");
		poly2Panel.add(setPoly2Button);
		poly2Panel.add(poly2Label);
		
		polynomialsPanel.add(poly1Panel);
		polynomialsPanel.add(poly2Panel);
		
		return polynomialsPanel;
	}
	
	private JPanel generateResultPanel() {
		JPanel resultPanel = new JPanel(new GridLayout(0,1));
		//Result panel stores the result of the last operation performed,
		//as well as separators (for prettiness)
		
		resultPanel.add(new JSeparator()); //Separate from current polynomials
		
		//A JLabel storing the result of the last computation made
		polyResultLabel = new JLabel("Result: 0");
		polyResultLabel.setHorizontalAlignment(JLabel.CENTER);
		resultPanel.add(polyResultLabel);
		
		resultPanel.add(new JSeparator()); //Separate from control buttons
		
		return resultPanel;
	}
	
	private JPanel generateControlsPanel() {
		JPanel controlsPanel = new JPanel(new GridLayout(2,3));
		//Controls panel stores the buttons for the various available operations
		
		//Add button
		addButton = new JButton("P1 + P2");
		controlsPanel.add(addButton);
		
		//Subtract button
		subtractButton = new JButton("P1 - P2");
		controlsPanel.add(subtractButton);
		
		//Derivate button
		derivateP1Button = new JButton("Derivate P1");
		controlsPanel.add(derivateP1Button);
		
		//Multiply button
		multiplyButton = new JButton("P1 * P2");
		controlsPanel.add(multiplyButton);
		
		//Divide button
		divideButton = new JButton("P1 / P2");
		controlsPanel.add(divideButton);
		
		//Integrate button
		integrateP1Button = new JButton("Integrate P1");
		controlsPanel.add(integrateP1Button);
		
		return controlsPanel;
	}
	
	//Access methods
	public String getInputField() {
		return polynomialInputField.getText();
	}
	public void setPoly1Label(String s) {
		poly1Label.setText(s);
	}
	public void setPoly2Label(String s) {
		poly2Label.setText(s);
	}
	public void setResultLabel(String s) {
		polyResultLabel.setText(s);
	}
	
	//Passing ActionListeners to the Controller, since they work with the Model
	public void addSetPoly1Listener(ActionListener e) {
		setPoly1Button.addActionListener(e);
	}
	public void addSetPoly2Listener(ActionListener e) {
		setPoly2Button.addActionListener(e);
	}
	public void addAddListener(ActionListener e) {
		addButton.addActionListener(e);
	}
	public void addSubtractListener(ActionListener e) {
		subtractButton.addActionListener(e);
	}
	public void addDerivateListener(ActionListener e) {
		derivateP1Button.addActionListener(e);
	}
	public void addIntegrateListener(ActionListener e) {
		integrateP1Button.addActionListener(e);
	}
	public void addMultiplyListener(ActionListener e) {
		multiplyButton.addActionListener(e);
	}
	public void addDivideListener(ActionListener e) {
		divideButton.addActionListener(e);
	}
	
	//Auxiliary methods, to avoid cluttering
	
}
