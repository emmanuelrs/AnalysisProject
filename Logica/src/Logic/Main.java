
package Logic;

import java.util.ArrayList;
import java.util.Random;


public class Main {

	/**
	 * 
	 */
	public static void main(String[] args) {
		Random random = new Random();
		Container truck = new Container();
		Individual individual;
		ArrayList<Owner> ownerList = new ArrayList<Owner>();
		ArrayList<Packages> listOfPackages = new ArrayList<Packages>();
		ArrayList<Container> truckList = new ArrayList<Container>(); 
			
		ownerList.add(new Owner("Persona", 7, 12, "Escazu"));
		ownerList.add(new Owner("Alguien", 9, 18, "San Jose Centro"));
		ownerList.add(new Owner("Otro", 15, 23, "Tres Rios"));
		ownerList.add(new Owner("Alguien mas", 17, 21, "Moravia"));
		
		truckList.add(new Container());
		truckList.add(new Container());
		truckList.add(new Container());
		
		for(int i = 0; i < 10; i++){
			Owner owner;
			int length = random.nextInt(12 - 1) + 1; 
			int width = random.nextInt(6 - 1) + 1;
			int height = random.nextInt(6 - 1) + 1;
			int number = random.nextInt(ownerList.size());
			int deliveryLimit = random.nextInt(1);		
			owner = ownerList.get(number);
			listOfPackages.add(new Packages(length, width, height, deliveryLimit, owner));	
		}  
		
		String[] letters = new String[listOfPackages.size()]; 

		int truckCounter = 0; 
		int indexLetter = 0; 
		int packageCounter = 0;
		while(!listOfPackages.isEmpty() && truckCounter < truckList.size()){ 
		
			truck = truckList.get(truckCounter);
			truck.organizeOwner(listOfPackages);
			System.out.println("---------------------------------------");
			
			truck.setPackagesToDeliver(listOfPackages);
			truck.greedy(listOfPackages.size());
			listOfPackages = truck.getPaquetesEntregados();
			
			Population population = new Population(listOfPackages);
			population.generatePopulation(15, listOfPackages.size(), true, truck);

			
			int i = 0;
			if(listOfPackages.size() > 1){
				while(i < 20){
					population.generatePopulation(15, listOfPackages.size(), false, truck);
					i++;
				}
			} 
					
			System.out.println("---------------------------------");
			System.out.println("Paquetes para meter al camion: " + listOfPackages.size());
			System.out.println("length: " + truck.getContainerLength());
			System.out.println("width: " +  truck.getContainerWidth());
			System.out.println("height: " + truck.getContainerHeight()); 
			
		 
	        for(int i1 = 0; i1 < listOfPackages.size(); i1++) {   

	        	if(population.returnBestCandidate().getChromosome().get(i1) == 1){
		        	String message = "";
	                message = message + Integer.toString(packageCounter + 1);
	                message = message + "        " + listOfPackages.get(i1).getOwner().getOwnerName();
	                message = message + " " + listOfPackages.get(i1).getOwner().getAddress();
	               // message = message + " " + Integer.toString(listOfPackages.get(i).getTruckNumber()); 
	            //    System.out.println(message);
	                message = message + " " + Integer.toString(listOfPackages.get(i1).getOwner().getStartAvailablity());
	                letters[indexLetter] = message;
	                indexLetter += 1; 
	                packageCounter += 1; 
	        	}
                 
              
	        }
	   
	 
			System.out.println(population.returnBestCandidate().getChromosome());
			System.out.println("Paquetes en el camion: " + population.returnBestCandidate().getFitness());
			individual = population.returnBestCandidate();
			individual.eliminatePackages(listOfPackages);
			truck.addPackages(listOfPackages);
			truckCounter++;
		} 
		for(int i = 0; i < letters.length; i++){ 
			System.out.println(letters[i]);
		}
		System.out.println("--------------------------------");
		System.out.println("Camiones utilizados: " + (truckCounter));
		if(!listOfPackages.isEmpty()){
			System.out.println("No todos los paquetes se pudieron entregar en un dia.");
		} 
		

	}
}