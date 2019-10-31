package bitmapGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class Main {

	public static void main(String[] args) {

		if(args.length!=2) {
			//Too few or too many arguments
			System.out.println("ERROR: Wrong argument count. Please provide a letter associated with one of the RGB components and an integer in the range <0;255> seperated by a space");
		}else {
			//Assigning the arguments to a variable
			String fixedComponent= args[0];
			Integer fixedValue = Integer.parseInt(args[1]);
			
			//Checking the first argument
			if((!fixedComponent.equals("R"))&&(!fixedComponent.equals("G"))&&(!fixedComponent.equals("B"))) {
				System.out.println("The first argument is wrong. Please provide either 'R' 'G' or 'B'");
				
			}else if(fixedValue<0||fixedValue>255) {  //Checking the second argument
				System.out.println("The second argument is wrong. Please provide an integer between 0 and 255 (inclusive)");
			}else {
				//Everything seems ok
				//Creating the file and output streams (defined as a separate object below the Main function)
				ImageWriter out = new ImageWriter();
				
				//Writing contents of the file in hex
				try {
					//File type ID - BM in ascii
					out.write("42");
					out.write("4D");
					//Size  of the file - 54bytes for the header + 3*256*256 bytes for the pixel array = 196662 = 0x30036
					out.write("60");
					out.write("03");
					out.write("30");
					out.write("00");
					//Application info - none
					out.write("00");
					out.write("00");
					out.write("00");
					out.write("00");
					//How far is the pixel array - 54 bytes (again)
					out.write("36");
					out.write("00");
					out.write("00");
					out.write("00");
					//Number of bytes in the header from this point onward - 40
					out.write("28");
					out.write("00");
					out.write("00");
					out.write("00");
					//Width of the bitmap - 256
					out.write("00");
					out.write("01");
					out.write("00");
					out.write("00");
					//Height of the bitmap - 256
					out.write("00");
					out.write("01");
					out.write("00");
					out.write("00");
					//How many color planes
					out.write("01");
					out.write("00");
					//Bits per pixel - 24
					out.write("18");
					out.write("00");
					//Compression?
					out.write("00");
					out.write("00");
					out.write("00");
					out.write("00");
					//Size of pixel array in bytes - (256*256*3) 0x30000
					out.write("00");
					out.write("00");
					out.write("30");
					out.write("00");
					//Standard printing resolution
					out.write("13");
					out.write("0B");
					out.write("00");
					out.write("00");
					out.write("13");
					out.write("0B");
					out.write("00");
					out.write("00");
					//Colors in the palette
					out.write("00");
					out.write("00");
					out.write("00");
					out.write("00");
					//All colors are important
					out.write("00");
					out.write("00");
					out.write("00");
					out.write("00");
					
					//Thankfully 256*3=768 is divisible by 4 so I don't need to include information about padding
					//Writing the pixel array
					if(fixedComponent.equals("R")) {
						for(int x=0; x<256; x++) {
							for(int y=0; y<256; y++) {
								out.write(y);
								out.write(x);
								out.write(fixedValue);
							}
						}
					}else if(fixedComponent.equals("G")) {
						for(int x=0; x<256; x++) {
							for(int y=0; y<256; y++) {
								out.write(y);
								out.write(fixedValue);
								out.write(x);
							}
							}
						}
						
					
					else if(fixedComponent.equals("B")) {
						for(int x=0; x<256; x++) {
							for(int y=0; y<256; y++) {
								out.write(fixedValue);
								out.write(y);
								out.write(x);
							}
						}
					}
					
					//Closing the output stream
					out.close();
					System.out.println("File successfully generated");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		}
		
	}

}

class ImageWriter {
	File output;
	FileOutputStream outputStream;
	public ImageWriter() {
		File output = new File("output.bmp");
		try {
			outputStream = new FileOutputStream(output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void write(String hex) throws IOException {
		//Interprets the string as a hex number and converts it to an int which can be passed to a stream
		int value;
		value=Integer.parseInt(hex, 16);
		outputStream.write(value);
	}
	
	public void write(int value) throws IOException {
		outputStream.write(value);
	}
	
	public void close() {
		try {
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
