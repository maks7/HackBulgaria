/*********************************************************************************************************
*
*       Author: Maksim Markov, e-mail: maksim.markov.bg@gmail.com , mobile 088 6 839 991
*       Date: 16.02.2015
*
**********************************************************************************************************/
package com.hackbulgaria.tasks;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Paths;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.io.IOException;

public class Problem2 {
	
	private List<String> getFileNames(List<String> fileNames, Path dir){

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir))
	    {    
			for (Path path : stream) {
	            if(path.toFile().isDirectory()) getFileNames(fileNames, path);
	            else {
	                fileNames.add(path.toAbsolutePath().toString());
	            }
	        }
	    } catch(IOException e){
	        System.out.println("Exception is thrown: "+e);
	    	e.printStackTrace();
	    } 
	
		return fileNames;
	} 
	
	private List<String> checkFiles(List<String> fileList) {
		List<String> list = new ArrayList<>();
		List<String> fileForCheck = new LinkedList<>();
		List<String> duplicate = new ArrayList<>();
		List<String> mainFile = new ArrayList<>();
		Map<Double, List<String>> fileMap = new HashMap<>();
		
		// 1 - Get file size and put in Map with size Key
		Double fileSize;
		for(String fStr : fileList){
			fileSize = new Double(new File(fStr).length());
        if(fileMap.containsKey(fileSize)) { 
        	List<String> fileString = fileMap.get(fileSize);
        	fileString.add(fStr);
        	fileMap.put(fileSize, fileString);
         } else {
        	List<String> fileStr = new ArrayList<>();
        	fileStr.add(fStr);
        	fileMap.put(fileSize, fileStr);
        }
		}
		
		// 2 - Compare files by first and end Bytes
		for(List<String> ls : fileMap.values()) {
			if(ls.size()>1) {
				while(ls.size()>=2){
					int[] dupl = new int[ls.size()];
					Arrays.fill(dupl, 0);
					for (int j=0; j<(ls.size()-1); j++){
						if(isEqualsFileFirstEndBytes(ls.get(j), ls.get(ls.size()-1))){
							if(dupl[ls.size()-1]!=1) { 
								dupl[ls.size()-1]=1;
							}
							dupl[j]=1;
						}
					}
					
					for(int j=0; j<=(dupl.length-1); j++){
						if(dupl[j]==1){	
							if(!fileForCheck.contains(ls.get(j))) { 
								fileForCheck.add(ls.get(j));
								duplicate.add(ls.get(j));
							}
						} else {
							if(dupl.length>2 && j==(dupl.length-1)) list.add(ls.get(j));
							if(dupl.length==2) list.add(ls.get(j));
						}
					}
					if(duplicate.size()>0){
						ls.removeAll(duplicate);
						duplicate.clear();
					} else if (ls.size()==2)
						ls.clear();
					else if (ls.size()>2)
						ls.remove(ls.size()-1);
				}
			} else {
				list.add(ls.get(0));
			}
		}
	
		// 3 - Compare files by Bytes and remove the entry for duplicate
		if(!fileForCheck.isEmpty()){
			while(fileForCheck.size()>1) {	
				for (int j=0; j<(fileForCheck.size()-1); j++)
					if(isEqualsFileByBytes(fileForCheck.get(j), fileForCheck.get(fileForCheck.size()-1))){
						duplicate.add(fileForCheck.get(j));
						if(j==(fileForCheck.size()-2)) { 
							list.add(fileForCheck.get(fileForCheck.size()-1));
							mainFile.add(fileForCheck.get(fileForCheck.size()-1));
						}
				}
				fileForCheck.removeAll(duplicate);
				duplicate.clear();
				fileForCheck.removeAll(mainFile);
				mainFile.clear();
			}
		}
		return list;
	}
	
	private Boolean isEqualsFileFirstEndBytes(String file1, String file2) {
		
		File f1 = new File(file1);
		File f2 = new File(file2);
		byte[] b1 = new byte[100];
		byte[] b2 = new byte[100];
		byte[] b1end = new byte[100];
		byte[] b2end = new byte[100];
		
		if(f1.length()>200 && f2.length()>200) {
			
			try ( RandomAccessFile randomAccessFile1 = new RandomAccessFile(file1, "r");
					RandomAccessFile randomAccessFile2 = new RandomAccessFile(file2, "r")) {
				randomAccessFile1.seek(0); 
				randomAccessFile2.seek(0); 
				randomAccessFile1.read(b1, 0, 100);
				randomAccessFile2.read(b2, 0, 100);
				randomAccessFile1.seek(new File(file1).length() - 100); 
				randomAccessFile2.seek(new File(file2).length() - 100); 
				randomAccessFile1.read(b1end, 0, 100);
				randomAccessFile2.read(b2end, 0, 100);
				
				if(Arrays.equals(b1,b2)==true && Arrays.equals(b1end,b2end)==true) return true;
				else 
					return false;
				
			} catch (IOException e) {
				System.out.println("Exception is thrown : " + e);
				e.printStackTrace();
			}	
		}
		return true;
	}
	
	private Boolean isEqualsFileByBytes(String file1, String file2) {
		File f1 = new File(file1);
		File f2 = new File(file2);
		try(FileInputStream fis1 = new FileInputStream (f1);
		FileInputStream fis2 = new FileInputStream (f2)) {
		
		int n=0;
		byte[] b1;
		byte[] b2;
			
			while ((n = fis1.available()) > 0) {
			if (n>100) n=100;
			b1 = new byte[n];
			b2 = new byte[n];
			
			int res1 = fis1.read(b1);
			int res2 = fis2.read(b2);
			if(res1!=-1 && res2!=-1){
				if (Arrays.equals(b1,b2)==false) return false;
			}
			} // end while
		}
		catch (IOException e) {
			System.out.println("Exception is thrown : " + e);
			e.printStackTrace();
		}
		return true;
	}
	
	public List<String> listDuplicatingFiles(String dir) {
		List<String> ldf = new ArrayList<>();

		ldf = getFileNames(ldf, Paths.get(dir));
		List<String> ldf2 = checkFiles(ldf);
		return ldf2;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		List<String> fileNamesList = new ArrayList<>();
		
		Problem2 prob2 = new Problem2();
		
		fileNamesList = prob2.listDuplicatingFiles("c:\\File");
		
		if(!fileNamesList.isEmpty()) System.out.println(fileNamesList);
	}
}