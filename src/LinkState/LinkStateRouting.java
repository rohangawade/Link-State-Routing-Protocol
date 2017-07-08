package LinkState;
import java.awt.Color;
import java.util.*;

public class LinkStateRouting {
	public int routerAdjMatrix[][];
	int copyrouterAdjMat[][];
	static int source = -1,destination = -1;
	static int createFlag = 0;
    public void menu(){
    	int exit = 0;
    	do
    	{   	
    	System.out.println("\t\t  ********** Menu **********  ");
    	System.out.println("1. Create a Network Topology");
    	System.out.println("2. Building a Connection Table");
    	System.out.println("3. Shortest Path to Destination Router");
    	System.out.println("4. Modify Topology by removing a router");
    	System.out.println("5. Best Router For Broadcast");
    	System.out.println("6. Exit");
    	Scanner in = new Scanner(System.in);
    	
		//Enter the choice to select what function you need to perform
		System.out.println("Enter your choice :");
		int ch = in.nextInt();
		switch(ch)
			{
			case 1:
				//Create a Network Topology
				//Read the file to create network topology
				routerAdjMatrix = new CreateTopology(1).input();
				System.out.println();
				break;
			case 2:
				//Build a connection table
				if(createFlag == 1)
				{
				System.out.println("----------Build a connection table----------");
				routerAdjMatrix = new CreateTopology(0).input();	
				ConnectionTable contab = new ConnectionTable(routerAdjMatrix);
				//Get Source Router
				System.out.println("Enter the source router");
				//Decrement 1 from source value as array starts from 0
				source =in.nextInt()-1;
				contab.getInterfaceConnection(source);
				}
				else
					System.err.println("Error: Create the topology first");
				break;
			case 3: 
				if(createFlag == 1)
				{
				//Shortest Path to Destination Router
				System.out.println("----------Shortest path to destination router----------");
				routerAdjMatrix = new CreateTopology(0).input();
				//Get the values for source and destination router
				Scanner inputval = new Scanner(System.in);
				System.out.println("Enter Source Router : ");
				source = inputval.nextInt()-1;
				System.out.println("Enter Destination Router : ");
				destination = inputval.nextInt()-1;
				//Call shortest path algorithm Dijkstra();
				new DijkstraShortestPath().Dijkstra(routerAdjMatrix,source,destination);
				}
				else
					System.err.println("Error: Create the topology first");
				break;
			case 4:
				if(createFlag == 1)
				{
				routerAdjMatrix = new CreateTopology(0).input();
				routerAdjMatrix = new ModifyTopology().modifyTopology(routerAdjMatrix,source,destination);
				System.out.println("----------New Shortest Path from source to destination----------");
				//If source and destination are not yet inputted
				if(source ==-1 && destination == -1)
				{
					System.out.println("Enter the source :");
					source = in.nextInt()-1;
					System.out.println("Enter the destination :");
					destination = in.nextInt()-1;			
				}
				//calculate the shortest path with new configuration.
				new DijkstraShortestPath().Dijkstra(routerAdjMatrix,source,destination);
				}
				else
					System.err.println("Error: Create the topology first");
				break;
			case 5:
				if(createFlag == 1)
				{
				System.out.println("----------Best Router----------");
				routerAdjMatrix = new CreateTopology(0).input();
				ConnectionTable ctab = new ConnectionTable(routerAdjMatrix);
				ctab.getBestRouter();
				}
				else
					System.err.println("Error: Create the topology first");
				break;
			case 6:
				exit = 6;
				System.out.println("Good Bye");
				System.exit(0);
				break;			
			}
    	}while(exit!=1 || exit == 6);
    }	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("************************Link State Routing Protocol************************");
		//Call menu function to display menu
		LinkStateRouting lsr =new LinkStateRouting();
		lsr.menu();
	}
}
