/*********************************************************************************************************
*
*       Author: Maksim Markov, e-mail: maksim.markov.bg@gmail.com , mobile 088 6 839 991
*       Date: 14.02.2015
*
**********************************************************************************************************/
package com.hackbulgaria.tasks;

import java.util.ArrayList;
import java.lang.Math;

public class Problem1 {

	private ArrayList<Integer> primesInAnInterval(int from, int to) {
		ArrayList<Integer> arr = new ArrayList<>();
		if(from>=2 && to>2 && from<to) {
			for (int i = from; i <= to; i++) {
				if (isPrime(i))
					arr.add(new Integer(i));
			}
			return arr;
		} else if (from<2 || to<3) {
			System.out.println("Invalid input. You have to provide numbers From > 1 and To > 2.");
			return arr;
		} else {
			System.out.println("Invalid input. You have to provide From less than To.");
			return arr;
		} 
	}

	private Boolean isPrime(int num) {
		double i = 2.0;
		if (num == 2) {
			return true;
		}
		double sqrNum = Math.sqrt((double) num);
		while (i <= sqrNum) {
			if (num % i == 0) {
				return false;
			}
			i++;
		}
		return true;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Problem1 prob1 = new Problem1();
		ArrayList<Integer> arrL = prob1.primesInAnInterval(2, 20);
		if(!arrL.isEmpty()) System.out.println(arrL);
		arrL = prob1.primesInAnInterval(5, 13);
		if(!arrL.isEmpty()) System.out.println(arrL);
		arrL = prob1.primesInAnInterval(13, 5);
		if(!arrL.isEmpty()) System.out.println(arrL);
		arrL = prob1.primesInAnInterval(2, -3);
		if(!arrL.isEmpty()) System.out.println(arrL);
	}
}
