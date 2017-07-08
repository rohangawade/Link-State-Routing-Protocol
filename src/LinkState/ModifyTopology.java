package LinkState;

import java.io.FileWriter;
import java.io.*;
import java.util.Scanner;

public class ModifyTopology {
	int routerAdjMat[][];
	
	public int[][] modifyTopology(int routerAdjMatrix[][],int src, int dest)
	{
		try
		{
			//Create filewriter to open the file
			FileWriter filewrite = new FileWriter(CreateTopology.inFileName);
			BufferedWriter writeLine = new BufferedWriter(filewrite); 	 
			routerAdjMat = routerAdjMatrix;
			Scanner inp = new Scanner(System.in);

			//Enter the router number to be removed
			System.out.println("Enter the router number that is down");
			int remRouterNumber = inp.nextInt()-1;
			
			//Display updated matrix
			for(int i=0;i<(routerAdjMat.length);i++)
			{
				System.out.print("\t"+ "R"+(i+1));
			}			
			System.out.println();
			for(int i=0;i< (routerAdjMat.length);i++)
			{
				System.out.print("R"+(i+1)+"\t");
				for(int j=0;j<(routerAdjMat.length);j++)
				{
					if(i == remRouterNumber || j == remRouterNumber)
					{
					routerAdjMat[i][j] = -1; 
					}
					writeLine.write(routerAdjMat[i][j]+" ");
					System.out.print(routerAdjMat[i][j]+"\t");
				}
				writeLine.newLine();
				System.out.println();
			}
			writeLine.close();			
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		
		return routerAdjMat;
	}
}
