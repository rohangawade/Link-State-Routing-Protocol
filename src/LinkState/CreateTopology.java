package LinkState;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CreateTopology {
	int distAdjMat[][];
	String readByLine[];
	String readCurLine[];
	static String inFileName ="";
	int matrixLength = 0;
	int displayFlag=0;
	CreateTopology(int disp)
	{
		displayFlag=disp;
	}
	public int[][] input(){
		Scanner in = new Scanner(System.in);
		if(displayFlag == 1)
		{
		System.out.println("Get input file to Create Topology");
		inFileName = in.nextLine().trim();
		}
		try
		{
		/* Create a bufferedReader object and find the length of input matrix */
		BufferedReader brGetLen = new BufferedReader(new FileReader(inFileName));
		
		//split the line of the file by space and store it in string array readByLine[] to get the length
		readByLine = brGetLen.readLine().trim().split("\\s+");
		matrixLength =readByLine.length;
		
		//Allocate size to matrix distAdjMat
		distAdjMat =  new int [matrixLength][matrixLength];
		
		//close the BufferedReader object and create a new one		
		brGetLen.close();
		//Read the file to get the topology
		BufferedReader brGetData = new BufferedReader(new FileReader(inFileName));
		String getLine="";
		int numOfLines=0;
		while((getLine = brGetData.readLine())!=null)
			{
			readCurLine = getLine.trim().split("\\s+");
			for (int i = 0; i < matrixLength; i++)
				{
		        distAdjMat[numOfLines][i] = Integer.parseInt(readCurLine[i]);    
				}
		  numOfLines++;
			}
		if(displayFlag==1)
			displayMatrix();
		}
		catch(IOException ioe)
		{
			
			ioe.printStackTrace();
		}
		return distAdjMat;
	}

	public void displayMatrix()
	{
		//Display the topology
		System.out.println("The Topology is Created. It is displayed as follows.");
		for(int i=0;i<matrixLength;i++)
		{
			System.out.print("\t"+ "R"+(i+1));
		}
		System.out.println();
		for(int i=0;i<matrixLength;i++)
		{
			System.out.print("R"+(i+1)+"\t");
			for(int j=0;j<matrixLength;j++)
			{
				System.out.print(distAdjMat[i][j] + "\t");
			}
			System.out.println();
		}
		//set create flag to 1
		LinkStateRouting.createFlag = 1 ;
	}
	
}
