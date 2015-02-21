/*********************************************************************************************************
*
*       Author: Maksim Markov, e-mail: maksim.markov.bg@gmail.com , mobile 088 6 839 991
*       Date: 18.02.2015
*
**********************************************************************************************************/
package com.hackbulgaria.tasks;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Problem4 {

	public void convertToGreyscale(String imgPath) {

		int red, blue, green, newpixel;

		try {
			File input = new File(imgPath);
			String[] strFilenameAndExt = input.getName().toString().split("\\.");
			String strOutFileName = strFilenameAndExt[0]+"_grey"+"."+strFilenameAndExt[1];
			String strFilenameGrey = input.getParent()+"\\"+strOutFileName;
			File output = new File(strFilenameGrey);
			
		
			BufferedImage buffImg = ImageIO.read(input);
			
			if(!input.exists()){
				 System.out.println("Input image does not exist.");
				 System.exit(0);
			}
			
			int width = buffImg.getWidth();
			int height = buffImg.getHeight();
			BufferedImage imgGrey = new BufferedImage(width,height,buffImg.getType());
			
			for (int i = 0; i < width; i++)
				for (int j = 0; j < height; j++) {

					red = new Color(buffImg.getRGB(i, j)).getRed();
					blue = new Color(buffImg.getRGB(i, j)).getBlue();
					green = new Color(buffImg.getRGB(i, j)).getGreen();
					newpixel = (int)(red*0.299) + (int)(blue*0.587) + (int)(green*0.114);
					Color newColor = new Color(newpixel, newpixel, newpixel);
					
					imgGrey.setRGB(i, j, newColor.getRGB());
				}

			if(ImageIO.write(imgGrey, strFilenameAndExt[1], output)) 
				System.out.println("Done! "+strFilenameGrey+" was created.");
			else
				System.out.println("Error: Task was not completed.");

		} catch (IllegalArgumentException iex) {
			System.out.println("Error : " + iex);
			iex.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error : " + e);
			e.printStackTrace();
		} 
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Problem4 prob4 = new Problem4();
		//prob4.convertToGreyscale("C:/File/Fields.png");
		//prob4.convertToGreyscale("C:/File/FolderX/Andrews1.png");
		prob4.convertToGreyscale("C:/File/IMG_7471.jpg");
		prob4.convertToGreyscale("C:/File/FolderX/setup.bmp");
	}
}
