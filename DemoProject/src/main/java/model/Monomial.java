package model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Monomial implements Comparable<Monomial> {
	//Monomial implemented using BigDecimal coefficients, for highly customisable precision
	//Stores a coefficient and a power; corresponds to  coeff * x ^ power
	
	//Constants
	public final int TOP_DECIMALS_USED = 6; //Maximum number of decimals to be stored in case of integration or division, which can muck up the coefficients
	
	//Attributes
	private BigDecimal coeff;
	private int power;
	
	//Constructors
	public Monomial(BigDecimal coeff, int power) {
		//Take care, as this constructor takes in a *reference* to coeff, since that's how Java rolls.
		//This means, try to send a new BigDecimal as argument, or simply use the other constructor
		this.coeff = coeff;
		this.power = power;
	}
	public Monomial(double coeff, int power) {
		this.coeff = BigDecimal.valueOf(coeff);
		this.power = power;
	}
	public Monomial(int coeff, int power) {
		this.coeff = new BigDecimal(coeff);
		this.power = power;
	}
	
	//Getters & setters
	public BigDecimal getCoeff() { return this.coeff; }
	public int getPower() { return this.power; }
	public void setPower(int power) { this.power = power; }
	public void setCoeff(BigDecimal coeff) { this.coeff = coeff; }
	public void setCoeff(double coeff) { this.coeff = BigDecimal.valueOf(coeff); }
	public void setCoeff(int coeff) { this.coeff = new BigDecimal(coeff); }
	
	//toString
	public String toString() {
		String output;
		
		if( 0 == this.coeff.compareTo(BigDecimal.ZERO) ) {
			output = ""; //Return an empty string in the case we have a 0-coefficient Monomial, for good integration into the Polynomial class.
		}
		else if(0 == this.power) {
			output = coeff.stripTrailingZeros().toPlainString();
		}
		else if(1 == this.power) {
			output = coeff.stripTrailingZeros().toPlainString() + "x";
		}
		else {
			output = coeff.stripTrailingZeros().toPlainString() + "x^" + power;
		}
		
		if( output.startsWith("1x") ) output = output.substring(1); //Skip leading "1" for cases like 1x^2
		
		return output;
	}
	public String toAbsoluteString() {
		//Works like toString, but omits the sign, essentially outputting an absolute-value of the Monomial
		return new Monomial(this.coeff.abs(), this.power).toString();
	}
	
	//compareTo
	public int compareTo(Monomial m) {
		if( this.power == m.power && 0 == (this.coeff.compareTo(m.coeff)) ) return 0; //Total equality
		else {
			if( this.power == m.power ) return this.coeff.compareTo(m.coeff); //If same power, compare the coefficients
			else {
				if (this.power > m.power) return 1; //Else, higher power wins
				else return -1;
			}
		}
	}
	
	//Arithmetic methods
	public Monomial add(Monomial m) {
		//Returns a Monomial object whose coefficient is the sum of this element and the parameter m
		//If called in a case where the powers of the two Monomials are of different powers, it will simply return a copy of this Monomial
		if(this.power != m.power) {
			return new Monomial(this.coeff.doubleValue(), this.power); //Return a copy, not this actual object, just in case
		}
		else {
			return new Monomial(this.coeff.add(m.coeff), this.power);
		}
	}
	
	public Monomial subtract(Monomial m) {
		//Returns a Monomial object whose coefficient is the difference of this element and the parameter m
		//If called in a case where the powers of the two Monomials are of different powers, it will simply return a copy of this Monomial
		//Note that this function DOES NOT check whether the result is zero! Should that be relevant, a separate check must be made.
		if(this.power != m.power) {
			return new Monomial(this.coeff.doubleValue(), this.power); //Return a copy, not this actual object, just in case
		}
		else {
			return new Monomial(this.coeff.subtract(m.coeff), this.power);
		}
	}
	
	public Monomial negate() {
		//Returns a Monomial that is equal to the negated value of this Monomial.
		//For instance, the negated value of -x^3 is +x^3
		return new Monomial(this.coeff.negate(), this.power);
	}
	
	public Monomial derivate() {
		//Returns a Monomial object that is obtained through derivation from this one.
		//Note that this function will return 0 when derivating a constant (power = 0) Monomial.
		if(0 == this.power) {
			return new Monomial(0, 0); //0x^0 = 0
		}
		else {
			BigDecimal newCoeff = this.coeff.multiply(new BigDecimal(this.power));
			int newPower = this.power - 1;
			
			return new Monomial(newCoeff, newPower);
		}
	}
	
	public Monomial integrate() {
		//Returns a Monomial object that is obtained through integration from this one.
		int newPower = this.power + 1;
		BigDecimal newCoeff = this.coeff.divide(new BigDecimal(newPower), Math.max(this.coeff.scale(), TOP_DECIMALS_USED), RoundingMode.HALF_EVEN); //Make sure we have enough decimals
		
		return new Monomial(newCoeff, newPower);
	}
	
	public Monomial multiply(Monomial m) {
		//Returns a Monomial corresponding to the product of this Monomial and m
		return new Monomial(this.coeff.multiply(m.coeff), this.power + m.power);
	}
	
	public Monomial divide(Monomial m) {
		//Returns a Monomial corresponding to the quotient of dividing this Monomial by m
		//Note that dividing by 0 or by a Monomial of power higher than this one's may produce incorrect results!
		int newPower = this.power - m.power;
		BigDecimal newCoeff = this.coeff.divide(m.coeff, Math.max(this.coeff.scale(), TOP_DECIMALS_USED), RoundingMode.HALF_EVEN);
		
		return new Monomial(newCoeff, newPower);
	}
}
