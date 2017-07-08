package LinkState;
import java.util.*;

public class DijkstraShortestPath {

	int matrixlength;
	int interfaceNode[], distFromSource[], visitedNodes[];
	int minval;
	int nextRouter;
	int routerAdjMat[][];
	int source = -1, destination = -1;
		
	public void Dijkstra(int routerAdjMatrix[][],int src,int dest)
	{
		//Initialize the required variables
		routerAdjMat = routerAdjMatrix;
		matrixlength = routerAdjMat.length;
		interfaceNode = new int[matrixlength];
		distFromSource = new int[matrixlength];
		visitedNodes = new int[matrixlength];
		minval = 999;
		nextRouter = 0;
		source = src;
		destination = dest;
		for(int i=0;i<matrixlength;i++)
		{
			//Initialize the variables
			visitedNodes[i] = 0;
			distFromSource[i] = 0;			
			interfaceNode[i]=0;
			for(int j=0;j<matrixlength;j++)
			{
				//Change the cost between router and itself from 0 to 999 and the indirectly connected router cost from -1 to 999
				if(routerAdjMat[i][j]== 0 ||routerAdjMat[i][j] == -1){
					routerAdjMat[i][j] = 999;
				}				
			}			
		}
		distFromSource = routerAdjMat[source];
		
		//Mark source node as visited
		visitedNodes[source] = 1;
		//The distance from source to source is zero. Hence set distFromSource for source = 0
		distFromSource[source] = 0;
		/*Link State Routing Algorithm */
		for(int routercount = 0; routercount < matrixlength; routercount++)
		{
			minval = 999;
			/* Determine which router to visit next */
			for(int routernumb = 0;routernumb < matrixlength;routernumb++)
			{				
				if(minval > distFromSource[routernumb] && visitedNodes[routernumb]!=1 )
				{
					minval = distFromSource[routernumb];
					nextRouter = routernumb;
				}
			}
			//Mark the node with minimum distance as visited.
			visitedNodes[nextRouter] = 1;
			
			/* Find the Router with minimum distance from Next Router and add the distance
			 * Store the nextRouter value */
		
			for(int routernumb = 0;routernumb < matrixlength; routernumb++)
			{
				//Process all the unvisited routers and select the best one
				if(visitedNodes[routernumb]!=1)
				{
					int distNextRouter =routerAdjMat[nextRouter][routernumb];
					if((minval+distNextRouter) < distFromSource[routernumb])
					{
						distFromSource[routernumb] = (minval)+distNextRouter;
						interfaceNode[routernumb] = nextRouter + 1;
					}
				}
			}
		}
		//Get the shortest Path between t1he two routers
		if(distFromSource[destination]==999){
			System.out.println("Path does not exist");
		}
		else
		{
		int routernumb = -1;
		for(int routercount = 0;routercount<matrixlength; routercount++)
		{
			if(routercount!=source)
			{
				if(routercount == destination)
				{
					System.out.print("\n The shortest path from source R"+ (source+1) + " to destination R" + (destination+1)+ ": R"+(routercount+1));
				
					int nextHop;
					routernumb = routercount;
					do
					{
						nextHop = interfaceNode[routernumb];
						routernumb = interfaceNode[routernumb]-1;
				
						if(nextHop == 0)
						{
							System.out.print("<--R"+(source+1));
						}
						else
						{
							System.out.print("<--R"+(nextHop));
						}
					}while(routernumb!=-1);
				}			
			}			
		}		
		System.out.println(" The total cost is "+distFromSource[destination]);
		}
	}
	
}
