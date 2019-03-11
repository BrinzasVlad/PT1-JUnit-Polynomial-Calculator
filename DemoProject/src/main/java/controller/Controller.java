package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Polynomial;
import view.MainView;

public class Controller {
	
	private Polynomial p1;
	private Polynomial p2;
	private Polynomial result;
	private Polynomial divisionRemainder; //Only used when we do division
	
	private MainView view;
	
	public Controller(){
		p1 = new Polynomial();
		p2 = new Polynomial();
		result = new Polynomial();
		divisionRemainder = new Polynomial();
		
		view = new MainView();
		//view.setVisible(true);
		//Only make it visible when called to work
		
		setButtonListeners();
	}
	
	private void setButtonListeners() {
		//Set button listeners
		view.addSetPoly1Listener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p1.setValuesFromString(view.getInputField()); //Read p1
				view.setPoly1Label("P1: " + p1.toString()); //Update p1 in view
			}
		});
		view.addSetPoly2Listener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p2.setValuesFromString(view.getInputField()); //Read p2
				view.setPoly2Label("P2: " + p2.toString()); //Update p2 in view
			}
		});
		view.addAddListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				result.setValuesFromPolynomial( p1.add(p2) ); //Compute result
				view.setResultLabel("Result: " + result.toString()); //Update result in view
			}
		});
		view.addSubtractListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				result.setValuesFromPolynomial( p1.subtract(p2) ); //Compute result
				view.setResultLabel("Result: " + result.toString()); //Update result in view
			}
		});
		view.addDerivateListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				result.setValuesFromPolynomial( p1.derivate() ); //Compute result
				view.setResultLabel("Result: " + result.toString()); //Update result in view
			}
		});
		view.addIntegrateListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				result.setValuesFromPolynomial( p1.integrate() ); //Compute result
				view.setResultLabel("Result: " + result.toString()); //Update result in view
			}
		});
		view.addMultiplyListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				result.setValuesFromPolynomial( p1.multiply(p2) ); //Compute result
				view.setResultLabel("Result: " + result.toString()); //Update result in view
			}
		});
		view.addDivideListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				result.setValuesFromPolynomial( p1.divide(p2)[0] ); //Compute result
				divisionRemainder.setValuesFromPolynomial( p1.divide(p2)[1] ); //and remainder
				view.setResultLabel("Result: " + result.toString()
								+ ", remainder: " + divisionRemainder.toString()); //Update results in view
			}
		});
	}
	
	public void work() {
		view.setVisible(true);
	}
	
	public static void main(String[] args) {
		Controller controller = new Controller();
		controller.work();
	}

}
