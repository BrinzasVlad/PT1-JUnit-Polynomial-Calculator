package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Polynomial {
	//Polynomial implemented as a sorted list of Monomials
	
	//Attributes
	private List<Monomial> values;
	
	//Constructors
	public Polynomial() {
		//Creates a new empty polynomial. Values must be added to it separately
		this.values = new ArrayList<Monomial>();
	}
	
	public Polynomial(String coefficients) {
		//Creates a new polynomial with the coefficients as given by the String parameter
		//The format for the input string must be space-separated coefficients
		//starting from the lowest-term ones to the highest-term one
		//For instance, if coefficients = "0 17 -3 0 5" the polynomial
		//will be 5x^4 - 3x^2 + 17x
		this.values = new ArrayList<Monomial>();
		
		//Easiest way to parse a space-separated String; works for other delimiters, too
		Scanner parseString = new Scanner(coefficients);
		int currentExponent = 0;
		while(parseString.hasNextInt()) {
			this.addValue(new Monomial(parseString.nextInt(), currentExponent));
			currentExponent++;
		}
		parseString.close();
	}
	
	//Management methods
	public void addValue(Monomial m) {
		//Adds the value m to the polynomial.
		//If a monomial with the same power already exists, the coefficients of the two are added instead of inserting a new value.
		//This way, we have a guarantee that no duplicates exist in the list
		
		boolean foundDuplicate = false;
		for(Monomial mon : values) {
			if(m.getPower() == mon.getPower()) {
				
				int i = values.indexOf(mon); //This hack-ish part is so because modifying mon doesn't actually change the values list as it is
				values.set(i, mon.add(m));
				
				foundDuplicate = true;
				break; //Here just to spare us of parsing the rest of the list, since we've found the match
			}
		}
		if(!foundDuplicate) {
			values.add(new Monomial(new BigDecimal(m.getCoeff().toString()),m.getPower())); //We avoid direct referencing and send a copy.
			values.sort(null);
		}
	}
	
	public void setValuesFromPolynomial(Polynomial p) {
		//Sets the Polynomial to the same values as the Polynomial p
		//Take care, since this DOES NOT duplicate values, so care
		//must be taken to avoid reference tangling!
		this.values.clear();
		
		for(Monomial m : p.values) {
			this.addValue(m);
		}
	}
	
	public void setValuesFromString(String s) {
		//Sets the Polynomial to the values given by the String s, much like instantiating
		//a new Polynomial with the Polynomial(String) constructor
		//String s should follow the same format as for Polynomial(String).
		this.values.clear();
		
		Polynomial stringPoly = new Polynomial(s);
		for(Monomial m : stringPoly.values) {
			this.addValue(m);
		}
	}
	
	public Monomial getHighestTerm() {
		//Returns the Monomial corresponding to the highest power of x in this Polynomial.
		//If the Polynomial is empty, will return 0
		Monomial result = new Monomial(0, 0);
		
		for(Monomial m : this.values) {
			if(0 != m.getCoeff().compareTo(BigDecimal.ZERO) && m.getPower() >= result.getPower()) { //If we've found a term that has a higher exponent and non-null coefficient...
				result = m;
			}
		}
		
		return result;
	}
	
	//toString
	public String toString() {
		//Outputs a String corresponding to the polynomial, such as x^3 - 3x^2 + 15
		String output = "";
		
		for(Monomial mon : values) {
			if("" != mon.toString()) { //Do not add 0-coefficient monomials to output
				String sign;
				if(-1 == mon.getCoeff().signum()) sign = " - "; //Determine leading sign of the current term
				else sign = " + ";
				
				output += sign + mon.toAbsoluteString();
			}
		}
		
		if(output.isEmpty()) output += "0"; //Display 0 for a void polynomial
		else if(output.startsWith(" + ")) output = output.substring(3); //Cut a leading +, since it is implicit
		output = output.trim(); //Cut extra whitespace, if needed (for instance for negative first term)
		
		return output;
	}
	
	//Arithmetic methods
	public Polynomial add(Polynomial p) {
		//Returns a Polynomial object representing the sum of this Polynomial and p
		//As a side-effect of how Polynomial works, instantiating an empty Polynomial, 
		//then adding a different one to it will, in essence, make a copy of the latter.
		
		Polynomial result = new Polynomial();
		
		for(Monomial mon : this.values) result.addValue(mon);
		for(Monomial pon : p.values) result.addValue(pon);
		
		return result;
	}
	
	public Polynomial subtract(Polynomial p) {
		//Returns a Polynomial object representing the difference of this Polynomial and p
		
		Polynomial result = new Polynomial();
		
		for(Monomial mon : this.values) result.addValue(mon);
		for(Monomial pon : p.values) result.addValue(pon.negate()); //Add negated p to obtain subtraction
		
		return result;
	}
	
	public Polynomial derivate() {
		//Returns a Polynomial object that is the derivative of this Polynomial
		
		Polynomial result = new Polynomial();
		
		for(Monomial mon : this.values) result.addValue(mon.derivate());
		
		return result;
	}
	
	public Polynomial integrate() {
		//Returns a Polynomial object that is the primitive (integral) of this Polynomial
		
		Polynomial result = new Polynomial();
		
		for(Monomial mon : this.values) result.addValue(mon.integrate());
		
		return result;
	}
	
	public Polynomial multiply(Polynomial p) {
		//Returns a Polynomial object that represents the product of this Polynomial and the Polynomial p
		
		Polynomial result = new Polynomial();
		
		for(Monomial mon : this.values) {
			for(Monomial pon : p.values) {
				result.addValue(mon.multiply(pon)); //For every pair of Monomials, add their product
			}
		}
		
		return result;
	}
	
	public Polynomial multiply(Monomial m) {
		//Returns a Polynomial object that represents the product of this Polynomial and the Monomial m
		
		Polynomial result = new Polynomial();
		
		for(Monomial mon : this.values) {
			result.addValue(mon.multiply(m)); //For every Monomial in this Polynomial, multiply it by m
		}
		
		return result;
	}
	
	public Polynomial[] divide(Polynomial p) {
		//Returns a pair of Polynomials, signifying the quotient and the remainder of the division.
		//May behave oddly if attempting to divide 0 or by 0
		
		Polynomial result[] = new Polynomial[2];
		
		Polynomial quotient = new Polynomial(); //A 0-Polynomial; the to-be quotient
		Polynomial remainder = this.add(new Polynomial()); //Hacky way of assigning a copy of this polynomial to result[0]; the to-be 
		
		while(!remainder.toString().equals("0")  &&  remainder.getHighestTerm().getPower() >= p.getHighestTerm().getPower()) {
			//While the remainder is non-null and the p still "fits" into it (as in, the remainder's degree is no lower than p's), we go through the division procedure.
			
			Monomial newQuotientTerm = remainder.getHighestTerm().divide(p.getHighestTerm());
			
			quotient.addValue(newQuotientTerm);
			remainder = remainder.subtract(p.multiply(newQuotientTerm));
			
			//Ugly-hack line to avoid getting stuck in infinite loops because of infinite fractions like 1/17
			if(remainder.getHighestTerm().getCoeff().abs().compareTo(new BigDecimal("0.0001") ) < 0 ) { //For really small deviations, ignore this factor
				remainder.addValue( remainder.getHighestTerm().negate() );
			}
		}
		
		result[0] = quotient;
		result[1] = remainder;
		return result;
	}
}
