package Comp251;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * this is the interface of the project. This class will take the inputs, check their validity and use 
 * method from NumbBaseConversion to convert the number and print out the result
 * @author hieule
 * @version Sept 2013
 */
public class NumberConversionInterface {
	public static final Pattern numberInput = Pattern.compile("([0-9A-F]*)\\.?([0-9A-F]*)");//available characters for input number
	public static final Pattern baseInput = Pattern.compile("[0-9]*");//available characters for input number
	/**
	 * separate the number into whole number part and fraction part if the number is not integer
	 * else return the array with that number. 
	 * @param number
	 * @return
	 */
	public static String[] separate(String number){
		if (number.indexOf(".") != -1 ){ // check if the number has fraction part
			if (number.indexOf(".") == 0) { //if user omits 0 with number < 1 like .5, add 0 to the whole part.
				return new String[]{"0", number.substring(number.indexOf("."))};
			} else {
			return new String[]{number.substring(0, number.indexOf(".")), number.substring(number.indexOf("."))};
			}
		} else {
			return new String[]{number,""};
		}		
	}
	/**
	 * check that the number and the base of that number is consistent 
	 * @param numb
	 * @param base
	 * @return
	 */
	public static boolean NumAndBaseConsistence(String numb, int base){
		for (char ch: numb.toCharArray()){
			int digit;
			if (ch == '.'){
				continue;
			}
			try {
				digit = Integer.parseInt(String.valueOf(ch)); // take the digit from the number
			} catch (NumberFormatException e) {
				digit = NumBaseConversion.CharToInt(ch); // if digit is a character then convert to number
			}
			if (digit >= base){ //if the digit is greater than the base then return false
				return false;
			}
		}
		return true;
	}
	/**
	 * the main method
	 * @param args
	 */
	public static void main(String[] args){
		Scanner src = new Scanner(System.in); //create a scanner
		while (true){
			System.out.println("type in the number, the base of the number and the expected base, each number must be " +
							   "separated by at least ',' character and might have space (no space at the end) \ntype 'q' to quit");
			System.out.println("input:");
			String[] input = src.nextLine().split("\\s*,\\s*"); // split the data
			if (input[0].equals("q")){  // check if user want to quit
				break;
			} else if (input.length != 3) {// check if there are enough inputs
				System.out.println("invalid number of input");
				continue;
			}
			Matcher numbMatch = numberInput.matcher(input[0]); // check if the number input is valid
			boolean numValid = numbMatch.matches();
			Matcher baseMatch = baseInput.matcher(input[1]); // check if the base input is valid
			boolean baseValid = baseMatch.matches();
			Matcher expectedBaseMatch = baseInput.matcher(input[2]); // check if the expected base input is valid
			boolean expectedBaseValid = expectedBaseMatch.matches();
			if (!numValid || !baseValid || !expectedBaseValid){
				System.out.println("bad data!!!!!");
				continue;
			}
			String[] number = separate(input[0]); //separate the number into whole number and fraction
			int base = Integer.parseInt(input[1]); // convert base into int
			int expectedBase = Integer.parseInt(input[2]); // convert the expected base to int
			if (base <2 || base >16 ||expectedBase<2 || expectedBase >16){ //check that bases are from 2 to 16
				System.out.println("invalid base!!!!!");
				continue;
			}
			if (!NumAndBaseConsistence(input[0],base)){ //check that the number and the base are consistent
				System.out.println("number and base are not consistent!");
				continue;
			}
			String result = NumBaseConversion.numberConversion(number, base, expectedBase);
			System.out.println(result);
		}
		src.close();
		System.exit(0);
	}
}
