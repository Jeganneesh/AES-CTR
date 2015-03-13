

import java.io.*;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
//import javax.crypto.Cipher;
import javax.crypto.SecretKey;

//import javax.crypto.spec.DESedeKeySpec;


public class Encryptor implements Runnable {

    private static String INPUT_FILE_NAME="C:\\Users\\Jeganneesh\\Desktop\\CTR_Encryptor.java";
	private static String OUTPUT_FILE_NAME="C:\\Users\\Jeganneesh\\Desktop\\CTR_Encryptor.java.txt";
	//private static String PASSWORD;
	private static int BLOCK_SIZE = 256;
	static Thread myThread;
	public static void main(String[] args) throws Exception {		
		Encryptor enc = new Encryptor();
		myThread = new Thread(new Encryptor());
		myThread.start();
 	  	enc.read1(INPUT_FILE_NAME, OUTPUT_FILE_NAME);
 	   	}
	
	//@SuppressWarnings("null")
	private void read1(String aInputFileName, String aOutputFileName) throws Exception {
		File iFile = new File(aInputFileName);
		File oFile = new File(aOutputFileName);
		try {
			InputStream input = null;
			OutputStream output = null;
			byte[] block = new byte[BLOCK_SIZE];
			int b = 0;
			
			try {
				input = new BufferedInputStream(new FileInputStream(iFile));
				output = new BufferedOutputStream(new FileOutputStream(oFile));
				
				
				
				/***********AES IMPLEMENTATION 256-bit KEY***************/
				Cipher c = Cipher.getInstance("AES/CTR/NoPadding");
				KeyGenerator keyGen = KeyGenerator.getInstance("AES");
				c.init(Cipher.ENCRYPT_MODE, keyGen.generateKey());
				SecretKey secretKey = keyGen.generateKey();
				System.out.println(secretKey); 
				byte[] key = secretKey.getEncoded(); 
				int x = key.length;
				System.out.println(x);
			   
				/****************Reading block-wise********************/		
					
				myThread = new Thread(new Encryptor());
				myThread.start();
					int readByte = 0;
					while(readByte != -1) {
					for (b = 0; b < BLOCK_SIZE; b++) {
						readByte = input.read();
						if (readByte == -1)
							break;
						block[b] = (byte) readByte;
					}
					
					System.out.println(b);
					
				
					byte[] encryptedBlock = encrypt(block,key);	
					output.write(encryptedBlock);
					
				}						 
		}
					
			finally {
				log("Closing streams");
				input.close();
				output.close();
			}			
		}
		catch(FileNotFoundException ex) {
			log("File not found");
		}
		catch (IOException ex) {
			log(ex);
		}
	}
	
	
	
	
	/*************END OF READ************************/ 
	
	
	
	
	
		private byte[] encrypt(byte[] block,byte[] key) {
		
		
		byte[] cipherblock = new byte[BLOCK_SIZE]; //XOR block and key //
		int i=0;
		for(int b=0;b<block.length;b++)
			if(i<key.length)
			cipherblock[b]= (byte)(block[b]^key[i++]);
				    
				cipherblock = swapColumns(cipherblock,0,2);
				cipherblock = swapRows(cipherblock,1,2);
				cipherblock = swapColumns(cipherblock,2,3);
				cipherblock = swapRows(cipherblock,0,3);
				cipherblock = transpose(cipherblock); 
				return cipherblock;
		
		
	}
		
		
   private byte[] transpose(byte[] block) {
			// TODO Auto-generated method stub
		for(int k=0;k<4;k++)
		{
			for(int l=(k*4);l<((k*5)+1);l++){
				byte temp = block[k];
				block[k]= block[l];
				block[l]= temp;
			} 
		}
			return block;
		}

	private byte[] swapRows(byte[] block, int i, int j) {
			// TODO Auto-generated method stub
		int r1= i*4;
		int r2= j*4;
		for(int k = 0;k<4;k++)
		{
			byte temp = block[r1+k];
			block[r1+k]= block[r2+k];
			block[r2+k]= temp;
			
		}
		
			return block;
		}

	private byte[] swapColumns(byte[] block, int i, int j) {
		byte temp;
		// TODO Auto-generated method stub
		   for(int k=0; k<16;k=k+4){
		   temp = block[i+k];
		   block[i+k]=block[j+k];
		   block[j+k]=temp;
		   }
			return block;
		}
		
	private static void log(Object aThing) {
		System.out.println(String.valueOf(aThing));
	}

	
	
	/****************NEW THREAD**************/
	
	@Override
	public void run() {
			
	}
	
	
	}