package Logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Individual {
	private ArrayList<Integer> chromosome;
	private ArrayList<Packages> phenotype;
	private int fitness;
	
	/* Generates an individual based on the size of the solution
	 * set inserted through
	 */
	public Individual(){
		this.chromosome = new ArrayList<Integer>();
	}
	
	public void generateChromosome(int chromosomeSize){
		int number;
		Random random = new Random();
		number = random.nextInt(5);
		for	(int i = 0; i < chromosomeSize/2; i++){
			int bit = 0;
			if(random.nextInt(5) == number){
				bit = 1;
			}
			chromosome.add(bit);
		}
		for	(int i = chromosomeSize/2; i < chromosomeSize; i++){
			int bit = 1;
			if(random.nextInt(5) == number){
				bit = 0;
			}
			chromosome.add(bit);
			Collections.shuffle(chromosome);
		}
	}
	
	public void calculateFitness(ArrayList<Packages> list, int length, int width, int height){
		int counter = 0;
		int p_length = length;
		int p_width = width;
		int p_height = height;
		Subspace subspace_remaining = new Subspace();
		Subspace subspace_right = new Subspace();
		Subspace subspace_up = new Subspace();
		boolean firstPackage = false;
		for(int i = 0; i < chromosome.size(); i++){
			if(chromosome.get(i) == 1 && !firstPackage){
				subspace_remaining = new Subspace(p_length-list.get(i).getPackageLength(), p_width, p_height);
				subspace_right = new Subspace(p_length, p_width-list.get(i).getPackageWidth(),p_height);
				subspace_up = new Subspace(p_length, p_width,p_height-list.get(i).getPackageHeight());
				counter++;
				firstPackage = true;
			} else if (chromosome.get(i) == 1){
				p_length = list.get(i).getPackageLength();
				p_width = list.get(i).getPackageWidth();
				p_height = list.get(i).getPackageHeight();
				if(subspace_right.getLength() - p_length >= 0  && subspace_right.getWidth() - p_width >= 0 && subspace_right.getHeight() - p_height  >= 0 
						 && !subspace_right.isFull()){
					if(subspace_right.getWidth() - p_width <= 1){	
						subspace_right.setFull(true);
					} else {
						subspace_right.setWidth(subspace_right.getWidth() - p_width);
					}
					counter++;
				} else if(subspace_up.getLength() - p_length  >= 0  && subspace_up.getWidth() - p_width >= 0 && subspace_up.getHeight() - p_height  >= 0  
						&& !subspace_up.isFull()){
					subspace_up.setHeight(subspace_up.getHeight() - p_height);
					if(subspace_up.getHeight() - p_height <= 1){	
						subspace_up.setFull(true);
					} else {
						subspace_up.setHeight(subspace_up.getHeight() - p_height);
					}
					counter++;
				} else if(subspace_remaining.getLength() -  p_length  >= 0  && subspace_remaining.getWidth() - p_width >= 0 && subspace_remaining.getHeight() - p_height  >= 0 
						&& !subspace_remaining.isFull()){
					if(subspace_remaining.getLength() -  p_length <= 1){	
						subspace_remaining.setFull(true);
					} else {
					   subspace_remaining.setLength(subspace_remaining.getLength() -  p_length);
					}
					counter++;
				} else {
					counter = 0;
					break;
				}
			}
		}
			this.fitness = counter;
	}

	private ArrayList<Integer> createRange(int start, int end){
		ArrayList<Integer> range = new ArrayList<Integer>();
		for (int i = 0; i < start; i++){
			range.add(i);
		}
		return range;
	}
	
	public void calculatePhenotype(ArrayList<Packages> listOfPackages){
		phenotype = new ArrayList<Packages>();
		for(int i = 0; i < listOfPackages.size(); i++){
			if(chromosome.get(i) == 1){
				phenotype.add(listOfPackages.get(i));
			}
		}
	}
	
	
	public void eliminatePackages(ArrayList<Packages> listOfPackages){
		int i = 0; 
		int index = 0;
		while(i < getFitness()){
			if (chromosome.get(index) == 1){
				listOfPackages.remove(index);
				i++;
			} else {
				index += 1;
			}
		}
	}
	
	
	public ArrayList<Packages> getPhenotype() {
		return phenotype;
	}

	public void setFitness(int fitness) {
		this.fitness = fitness;
	}

	public void setChromosome(ArrayList<Integer> chromosome) {
		this.chromosome = chromosome;
	}

	public ArrayList<Integer> getChromosome() {
		return chromosome;
	}

	public int getFitness() {
		return fitness;
	}

}
