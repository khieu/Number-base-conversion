package Comp251;

import java.io.IOException;
import java.util.Scanner;
/**
 * this class convert number from any base from 2 to 16 to any expected base from 2 to 16
 * @author hieule
 * @version Sept 2013
 *
 */
public class NumBaseConversion {
	public static final int A = 10, B=11, C=12, D=13, E=14, F=15;// the letter from A to F present the value from 10 to 15	
	/**
	 * convert a number (either the the whole number part or the fraction part) from base n to base 10. 
	 * pre: if input is fraction part then it must start with '.' character.
	 * @param number
	 * @param base
	 * @return
	 */
	private static String BaseNToTen(String number, int base){
		int power;
		int index;// index of the string number
		if (number.charAt(0)=='.'){ // if the input string is the fraction part => power = -1
			power = -1;
			index = 1; 
		} else { // if the input is a whole number => power = 0
			power = 0;
			index = number.length()-1;
		}
		double result = 0;//the output in base 10
		while (index >=0 && index < number.length()){ // loop through the number either to the left or to the right
			int digit;
			try {
				digit = Integer.parseInt(String.valueOf(number.charAt(index))); // take the digit from the number
			} catch (NumberFormatException e) {
				digit = CharToInt(number.charAt(index)); // if digit is a character then convert to number
			}
			if (number.charAt(0)=='.'){ // if input is fractional part then run from left to right
				index++;
				result = result +  (digit*Math.pow(base, power))  ;
				power--;
			} else { 
				index--;
				result =  (int) (digit*Math.pow(base, power))  + result;
				power++;
			}
		}
		if (result >1 || result ==0){ // if result is a whole number, return in integer form
			return String.valueOf((int) result);
		} else {
			return String.valueOf(result).substring(1); // if result is a fraction, the output start with "." char
		}
	}
	/**
	 * convert a whole number from base 10 to the expected base 
	 * @param input
	 * @return
	 */
	private static String WholeNumTenToN(String number, int base){
		int wholeNumber = Integer.parseInt(number);
		String result = "";
		while (wholeNumber/base != 0) { // do the conversion until the number get smaller than the base
			int digit = wholeNumber%base; // each digit in the result is the remainder 
			if (digit >= 10) { // if the remainder > 9 then convert to a character
				result = IntToChar(digit) + result;
			} else {
				result = wholeNumber%base + result;// concatenate the next digit with the result
			}
			wholeNumber = wholeNumber/base;
		}
		int firstDigit = wholeNumber%base; //add the highest order digit to the result
		if (firstDigit >=10){ 
			result = IntToChar(firstDigit) + result;
		} else {
			result = wholeNumber%base + result;
		}
		return result;
	}
	/**
	 * convert a fraction from base 10 to the expected base
	 * pre: a fraction must < 1
	 * @param input
	 * @return
	 */
	private static String FractionTenToN(String number, int base){
		double fraction = Double.parseDouble(number);// convert the input number to a double
		String result =".";// the string result in expected base
		int count = 0; // count the number of digits after the radix point
		while (fraction!= 0 && count <8){ // check whether the conversion is done or there's enough digit after radix point
			int digit = (int) (fraction*base); 
			if (digit > 9){ // if the digit > 10 then convert to a character and concatenate to the result
				result = result + IntToChar(digit);
			} else {
				result = result + digit;
			}
			count++;
			fraction = fraction*base - (int)(fraction*base); // take the fraction of the product of base and fraction as the fraction.
		}
		return result;
	}
	// convert the char to int if the char is from A-F
	public static int CharToInt(char input){
		int output = 0;
		switch (input){
			case 'A': output = A;
			break; 
			case 'B': output = B;
			break; 
			case 'C': output = C;
			break; 
			case 'D': output = D;
			break; 
			case 'E': output = E;
			break; 
			case 'F': output = F;
			break; 
		}
		return output;
	}
	// convert a digit to a char if the digit >= 10
	public static String IntToChar(int input){
		String output="";
		switch (input){
			case A: output = "A";
			break;
			case B: output = "B";
			break;
			case C: output = "C";
			break;
			case D: output = "D";
			break;
			case E: output = "E";
			break;
			case F: output = "F";
		}
		return output;
	}
	/**
	 * convert a number from any base from 2 to 16 to any expected base from 2 to 16. the number is represented in a
	 * string list, the 1st element is the wholenumber, the 2nd is the fraction if the number is a double.
	 * @param number
	 * @param base
	 * @param expectedBase
	 * @return
	 */
	public static String numberConversion(String[] number, int base, int expectedBase){
		String wholeNumber = number[0];
		String fraction = number[1];
		String wholeNumberInTen = BaseNToTen(wholeNumber, base);
		String wholeNumberInBaseN = WholeNumTenToN(wholeNumberInTen, expectedBase);
		String fractionInBaseN = "";
		if (!fraction.equals("")){ // if the number have a fractional part then convert the fraction
			String fractionInTen = BaseNToTen(fraction, base);
			fractionInBaseN = FractionTenToN(fractionInTen, expectedBase);
		} 
		return wholeNumberInBaseN + fractionInBaseN;
	}
	public static void main(String [] args){
		String[] number = {"0",".25"};
		String converted = numberConversion(number ,10,12);
		System.out.println(converted);
	}
}
