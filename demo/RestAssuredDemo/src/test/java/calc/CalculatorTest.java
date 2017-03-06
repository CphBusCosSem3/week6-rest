/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calc;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk
 */
public class CalculatorTest {
    Calculator instance = new Calculator();
    public CalculatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of add method, of class Calculator.
     */
    @org.junit.Test
    public void testAdd() {
        System.out.println("add");
        int i1 = 10;
        int i2 = 5;
        int expResult = 15;
        int result = instance.add(i1, i2);
        assertEquals(expResult, result);
    }

    /**
     * Test of sub method, of class Calculator.
     */
    @org.junit.Test
    public void testSub() {
        System.out.println("sub");
        int i1 = 100;
        int i2 = 5;
        int expResult = 95;
        int result = instance.sub(i1, i2);
        assertEquals(expResult, result);
        }

    /**
     * Test of mul method, of class Calculator.
     */
    @org.junit.Test
    public void testMul() {
        System.out.println("mul");
        int i1 = 10;
        int i2 = 5;
        int expResult = 50;
        int result = instance.mul(i1, i2);
        assertEquals(expResult, result);
    }

    /**
     * Test of div method, of class Calculator.
     */
    @org.junit.Test
    public void testDiv() {
        System.out.println("div");
        int i1 = 10;
        int i2 = 2;
        int expResult = 5;
        int result = instance.div(i1, i2);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of div method for dividing by 0
     */
    @org.junit.Test(expected = ArithmeticException.class) 
    public void testDivWith0() {
        System.out.println("div with 0");
        int i1 = 10;
        int i2 = 0;
        int result = instance.div(i1, i2);
    }
}
