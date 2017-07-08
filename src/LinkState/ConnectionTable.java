package LinkState;
import java.util.*;

public class ConnectionTable {

	int matrixlength;
	int interfaceNode[], distFromSource[], visitedNodes[];
	int minval;
	int nextRouter;
	int routerAdjMat[][];
		
	public ConnectionTable(int routerAdjMat[][])
	{
		this.routerAdjMat = routerAdjMat;
		matrixlength = routerAdjMat.length;
		interfaceNode = new int[matrixlength];
		distFromSource = new int[matrixlength];
		visitedNodes = new int[matrixlength];
		minval = 999;
		nextRouter = 0;
	
		/* Initialize interfaceNode distFromSource and visitedNodes array */		
		for(int i=0;i<matrixlength;i++)
		{
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
	}
	/* Step 5 get the best router*/
	public void getBestRouter()
	{
		int routersource=0;
		int minshortestpathcost = 0;
		int bestrouter = -1;
		int sumpath = 0;
		int totalcostrouter[] = new int[routerAdjMat.length];
		while(routersource<routerAdjMat.length)
		{
			for(int i = 0;i < routerAdjMat.length;i++)
			{
				visitedNodes[i]=0;
				
			}
			sumpath = 0;
			distFromSource = routerAdjMat[routersource];
			//Mark source node as visited
			visitedNodes[routersource] = 1;
			//The distance from source to source is zero. Hence set distFromSource for source = 0
			distFromSource[routersource] = 0;
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
					//Go to the next unvisited node and check for the minimum edge
					if(visitedNodes[routernumb]!=1)
					{
						int distNextRouter = routerAdjMat[nextRouter][routernumb];
						if((minval+distNextRouter) < distFromSource[routernumb])
						{
							
							distFromSource[routernumb] = (minval)+distNextRouter;
							interfaceNode[routernumb] = nextRouter + 1;
						}
					}				
				}
			}	
			//Find the total cost for the routers
			for(int i=0;i<distFromSource.length;i++)
			{
				sumpath = sumpath + distFromSource[i];	
			}
			totalcostrouter[routersource] = sumpath;
		routersource++;	
		}		
		
		//Get the router which has minimum total cost
		System.out.println("Router\t Total Cost ");
		minshortestpathcost = Integer.MAX_VALUE;
		for(int routnumb = 0;routnumb<totalcostrouter.length;routnumb++)
		{
			if(minshortestpathcost>totalcostrouter[routnumb])
			{
				minshortestpathcost = totalcostrouter[routnumb];
				bestrouter = routnumb + 1;
			}
			if(minshortestpathcost == totalcostrouter[routnumb])
			{
				Random random = new Random();
				bestrouter = random.nextBoolean() ? (routnumb+1) : bestrouter;
				
			}
			
			System.out.println((routnumb+1)+"\t"+totalcostrouter[routnumb]);
		}
		System.out.println("The Best Router is " + bestrouter + " and has cost " + minshortestpathcost);
		
	}
	
	public void getInterfaceConnection(int source)
	{
		distFromSource = routerAdjMat[source];
		int firstHop=-1;
		//Distance to other nodes from source router
		//Mark source node as visited
		visitedNodes[source] = 1;
		//The distance from source to source is zero. Hence set distFromSource for source = 0
		distFromSource[source] = 0;
		
		/* Find the minimum distance from source router to other router 
		 * and the immediate interface router after source node.
		 * Traverse through the adjacency matrix and check for the
		 * minimum distance
	     */
		/* Link State Routing Algorithm */
		for(int routercount = 0; routercount < matrixlength; routercount++)
		{
			minval = 1000;
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
				if(visitedNodes[routernumb]!=1)
				{
					int distNextRouter =routerAdjMat[nextRouter][routernumb];
					if((minval+distNextRouter) < distFromSource[routernumb])
					{	
						distFromSource[routernumb] = (minval)+distNextRouter;
						interfaceNode[routernumb] = nextRouter + 1;
						if(firstHop == -1)
							firstHop = nextRouter+1;
					}
				}
			}
		}
		
		Scanner inp = new Scanner(System.in);
		String reply ="";
		//Print the connection table for router
		System.out.println("Do you want to print connection table with minimum distance from source router ?(Y/N)");
		reply = inp.next();				
		System.out.println("Connection Table with Minimum Distance From Source Router R"+(source+1));
		if(reply.equalsIgnoreCase("y"))
		{
			System.out.println("Router \t Interface Hop \t Router Before Dest \t Distance From Source R"+(source+1));
			for(int routernumb = 0;routernumb < matrixlength; routernumb++)
			{
				int intNumb = interfaceNode[routernumb]==0? (routernumb+1) : interfaceNode[routernumb];
				int firstH = interfaceNode[routernumb] == 0? (routernumb+1):firstHop;
				System.out.println("R"+(routernumb+1)+"\t"+ " R"+firstH + " \t\t R" + intNumb +"\t\t\t "+distFromSource[routernumb]);
			}
		}
		if(reply.equalsIgnoreCase("n"))
		{
			System.out.println("Router \t Interface");
			for(int routernumb = 0;routernumb < matrixlength; routernumb++)
			{
				int intNumb = interfaceNode[routernumb]==0? (routernumb+1) : interfaceNode[routernumb];
				int firstH = interfaceNode[routernumb] == 0? (routernumb+1):firstHop;
				System.out.println("R"+(routernumb+1)+"\t"+ "R"+ firstH);
			}	
		}
	}
}
