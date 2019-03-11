package test;

import static org.junit.Assert.*;

//import org.junit.After;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Ignore;
import org.junit.Test;

import model.Polynomial;

public class OperationTester {
	private static Polynomial p1;
	private static Polynomial p2;
	
	private static Polynomial res;
	private static Polynomial remainder;
	
	@Test
	public void testBadInputOne() {
		p1 = new Polynomial("1 2 3 a4");
		
		assertEquals("1 + 2x + 3x^2", p1.toString());
	}
	
	@Test
	public void testBadInputTwo() {
		p1 = new Polynomial("8.0 4 17");
		
		assertEquals("0", p1.toString());
	}
	
	@Test
	public void testSumOne() {
		p1 = new Polynomial("0 2 4 6");
		p2 = new Polynomial("0 0 3");
		
		res = p1.add(p2);
		
		assertEquals("2x + 7x^2 + 6x^3", res.toString());
	}
	
	@Test
	public void testSumTwo() {
		p1 = new Polynomial("0");
		p2 = new Polynomial("-1 3 -1");
		
		res = p1.add(p2);
		
		assertEquals("- 1 + 3x - x^2", res.toString());
	}
	
	@Test
	public void testDiffOne() {
		p1 = new Polynomial("3 1 2");
		p2 = new Polynomial("3 1 2");
		
		res = p1.subtract(p2);
		
		assertEquals("0", res.toString());
	}
	
	@Test
	public void testDiffTwo() {
		p1 = new Polynomial("0 0 3");
		p2 = new Polynomial("0 0 -107");
		
		res = p1.subtract(p2);
		
		assertEquals("110x^2", res.toString());
	}
	
	@Test
	public void testDerivOne() {
		p1 = new Polynomial("1 1 1 1");
		
		res = p1.derivate();
		
		assertEquals("1 + 2x + 3x^2", res.toString());
	}
	
	@Test
	public void testDerivTwo() {
		p1 = new Polynomial("-12");
		
		res = p1.derivate();
		
		assertEquals("0", res.toString());
	}
	
	@Test
	public void testIntegOne() {
		p1 = new Polynomial("12 -12 12");
		
		res = p1.integrate();
		
		assertEquals("12x - 6x^2 + 4x^3", res.toString());
	}
	
	@Test
	public void testIntegTwo() {
		p1 = new Polynomial("0 0 0 0 0 0 1");
		
		res = p1.integrate();
		
		assertEquals("0.142857x^7", res.toString());
	}
	
	@Test
	public void testMultOne() {
		p1 = new Polynomial("0 0");
		p2 = new Polynomial("1 2 3 4 5");
		
		res = p1.multiply(p2);
		
		assertEquals("0", res.toString());
	}
	
	@Test
	public void testMultTwo() {
		p1 = new Polynomial("3 -5 2");
		p2 = new Polynomial("0 0 4 -7");
		
		res = p1.multiply(p2);
		
		assertEquals("12x^2 - 41x^3 + 43x^4 - 14x^5", res.toString());
	}
	
	@Test
	public void testDivOne() {
		p1 = new Polynomial("1 3");
		p2 = new Polynomial("4 6 7");
		
		res = p1.divide(p2)[0];
		remainder = p1.divide(p2)[1];
		
		assertEquals("0", res.toString());
		assertEquals("1 + 3x", remainder.toString());
	}
	
	@Test
	public void testDivTwo() {
		p1 = new Polynomial("0 0 1");
		p2 = new Polynomial("17");
		
		res = p1.divide(p2)[0];
		remainder = p1.divide(p2)[1];
		
		assertEquals("0.058823x^2", res.toString());
		assertEquals("0", remainder.toString());
	}
}
