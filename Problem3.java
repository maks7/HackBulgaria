/*********************************************************************************************************
*
*       Author: Maksim Markov, e-mail: maksim.markov.bg@gmail.com , mobile 088 6 839 991
*       Date: 18.02.2015
*
**********************************************************************************************************/
package com.hackbulgaria.tasks;

import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;

public class Problem3 {

	private int currentStartIndex = 0;
	private int finalStartIndex = 0;
	private int finalEndIndex = 0;
	private int currentLen = 0;
	private int minLen = Integer.MAX_VALUE;
	private char currentChar = ' ';
	private boolean isStarted = false;
	private boolean isFound = false;
	
	Map<Character, Integer> alphabetMap = new HashMap<>();
	Map<Character, Integer> encountered = new HashMap<>();
	HashSet<Character> charsCovered = new HashSet<>();
	
	private void updateEncounteredMapAndCharSet(Character currentChar, Map<Character,Integer> encounteredChar) {
		Character ch = Character.toLowerCase(currentChar);
		
		if(encounteredChar.containsKey(ch)) {
			encounteredChar.put(ch, encounteredChar.get(ch)+1);
		} else {
			encounteredChar.put(ch, 1);
		}
		
		charsCovered.add(ch);
	}
	
	public String smallestSubstringContainingTheAlphabet(String strInput){

		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		String str = toLowerChars(strInput);
		resetVariables();
		
		instantiateAlphabetMap(alphabet, alphabetMap);
		int charsToBeCovered = alphabetMap.size();
		
		for(int i=0; i<str.length(); i++) {
			if(21>str.charAt(i) || 255<str.charAt(i)){
				return "Error: The string has no ASCII character.";
			} else if (Character.isWhitespace(str.charAt(i)))
				return "Error: The string contains white space.";
		}
		
		for(int i=0; i<str.length(); i++){
			currentChar=str.charAt(i);
			
			if(alphabetMap.containsKey(currentChar) && !isFound) {
				if(!isStarted && !isFound) {
					isStarted = true;
					currentStartIndex = i;
				}
			
				updateEncounteredMapAndCharSet(currentChar, encountered);
				if(charsCovered.size()==charsToBeCovered){
					currentLen = i - currentStartIndex;
					isFound = true;
					updateMinLength(i);
					
					for(int j=0; j<(i-alphabetMap.size()+1); j++) {
						
						if(charsCovered.contains(str.charAt(currentStartIndex)) && encountered.get(str.charAt(currentStartIndex))>1) {
							encountered.put(str.charAt(currentStartIndex), (encountered.get(str.charAt(currentStartIndex))-1));
							currentStartIndex++;
						} else if(!charsCovered.contains(str.charAt(currentStartIndex))){
							currentStartIndex++;
						}
					}
					currentLen = i - currentStartIndex;
					updateMinLength(i);
				}
			
			} else if (alphabetMap.containsKey(currentChar) && isFound) {
				updateEncounteredMapAndCharSet(currentChar, encountered);
				
				for(int j=currentStartIndex; j<(i-alphabetMap.size()+1); j++) {
					
					if(charsCovered.contains(str.charAt(currentStartIndex)) && encountered.get(str.charAt(currentStartIndex))>1) {
						encountered.put(str.charAt(currentStartIndex), (encountered.get(str.charAt(currentStartIndex))-1));
						currentStartIndex++;
					
					} else if(!charsCovered.contains(str.charAt(currentStartIndex))){
						currentStartIndex++;
					}
				}
				
				currentLen = i - currentStartIndex;
				updateMinLength(i);
			}
		} // end str.length()
		
		
		return strInput.substring(finalStartIndex, finalEndIndex+1);
	}
	
	private void instantiateAlphabetMap(String str, Map<Character,Integer> enMap) {

	    for (char c : str.toCharArray())
	    	enMap.put(Character.toLowerCase(c), 1);
	}
	
	private void updateMinLength(int index) {
	    if (minLen > currentLen) {
	        minLen = currentLen;
	        finalStartIndex = currentStartIndex;
	        finalEndIndex = index;
	    }
	}
	
	private String toLowerChars(String string){
		char[] chars = new char[string.length()];
		for(int i=0; i<string.length(); i++){
			chars[i] = Character.toLowerCase(string.charAt(i));
		}
		return new String(chars);
	}
	
	private void resetVariables(){
		currentStartIndex = 0;
		finalStartIndex = 0;
		finalEndIndex = 0;
		currentLen = 0;
		minLen = Integer.MAX_VALUE;
		currentChar = ' ';
		isStarted = false;
		isFound = false;
		encountered.clear();
		charsCovered.clear();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Problem3 prob3 = new Problem3();
		System.out.println(prob3.smallestSubstringContainingTheAlphabet("###aaaaaabcdefghijklmnopqrstuvwxyz"));
		System.out.println(prob3.smallestSubstringContainingTheAlphabet("1abaaaaa333bcdefghijklmnopqrstuvwxyz"));
		System.out.println(prob3.smallestSubstringContainingTheAlphabet("a%%%%b4444cdefghijklmn124345678!@#$%^&*opqrstuvwxyza!*abcdefgh$$$$$$$$$$$$$$$$$ijklmn"));
		System.out.println(prob3.smallestSubstringContainingTheAlphabet("abcdefghijkmn124345678!@#$%^&*opqrstuvwxyz!*abcdefghijklmn"));
	}
}
