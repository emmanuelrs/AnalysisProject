package BackEnd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import emmanuelrosales.packagesapp.DeliveryPackage;
import emmanuelrosales.packagesapp.Truck;
import emmanuelrosales.packagesapp.DeliveryPackage;

// Class that has all the attributes and functions of a population

public class Population{
    private ArrayList<Individual> listOfIndividuals;
    private ArrayList<DeliveryPackage> candidateSet;
    private int crossoverValue;
    private double totalFitness;
    private double mutationProbability = 0.05;
    private int chromosome;

    //constructor
    public Population(ArrayList<DeliveryPackage> candidateSet){
        listOfIndividuals = new ArrayList<Individual>();
        this.candidateSet = sortArray(candidateSet);
        crossoverValue = candidateSet.size()/2 + 1;
        mutationProbability = 0.05;
    }

    //Function that organizes the array by volume
    public ArrayList<DeliveryPackage> sortArray(ArrayList<DeliveryPackage> list){
        Collections.sort(list, new Comparator<DeliveryPackage>() {
            public int compare(DeliveryPackage p1, DeliveryPackage p2) {
                return Double.compare(p2.getVolume(),p1.getVolume());
            }

        });
        return list;
    }

    //function that generates a population
    public void generatePopulation(int populationSize,int chromosomeSize, boolean option, Truck container){
        chromosome = chromosomeSize;
        if (option){
            for (int i = 0; i < populationSize; i++){
                Individual individual = new Individual();
                individual.generateChromosome(chromosomeSize);
                individual.calculateFitness(candidateSet, container.getContainerLength(),
                        container.getContainerWidth(), container.getContainerHeight());
                listOfIndividuals.add(individual);
            }
        } else {
            ArrayList<Individual> newListOfIndividuals = new ArrayList<Individual>();
            Individual individual1;
            Individual individual2;
            Individual finalIndividual;
            for(int i = 0; i < populationSize; i++){
                individual1 = selectionFunction();
                individual2 = selectionFunction();
                finalIndividual = crossIndividuals(individual1.getChromosome(), individual2.getChromosome());
                finalIndividual.calculateFitness(candidateSet, container.getContainerLength(),
                        container.getContainerWidth(), container.getContainerHeight());
                newListOfIndividuals.add(finalIndividual);
            }
            listOfIndividuals = newListOfIndividuals;
        }
        calculateTotalFitness();
    }

    public boolean validatePopulation(){
        if(listOfIndividuals.size() == 1){
            return true;
        }
        return false;
    }

    // Function to cross individuals
    public Individual crossIndividuals(ArrayList<Integer> firstIndividual, ArrayList<Integer> secondIndividual){
        Random random = new Random();
        int gene = random.nextInt(1);
        int number = random.nextInt(firstIndividual.size()-1);
        ArrayList<Integer> newChromosome = new ArrayList<Integer>();
        Individual individual = new Individual();
        for(int i = 0; i < crossoverValue; i++){
            newChromosome.add(firstIndividual.get(i));
        }

        for(int i = crossoverValue; i < chromosome; i++){
            newChromosome.add(secondIndividual.get(i));
        }

        if(random.nextDouble() == mutationProbability){
            individual.getChromosome().set(number,gene);
        }


        individual.setChromosome(newChromosome);
        return individual;

    }

    public Individual selectionFunction(){
        ArrayList<Individual> selection = new ArrayList<Individual>();
        Random random = new Random();
        Individual individual;
        int number;
        int size = 5;

        for(int i = 0; i < size; i++){
            number = random.nextInt(listOfIndividuals.size());
            selection.add(listOfIndividuals.get(number));
        }
        individual = selectBestFit(selection);
        return individual;
    }

    //Function to return the best candidate (individual with the best fitness)
    public Individual returnBestCandidate(){
        Individual individual = listOfIndividuals.get(0);
        for(int i = 0; i < listOfIndividuals.size(); i++){
            if(listOfIndividuals.get(i).getFitness() > individual.getFitness()){
                individual = listOfIndividuals.get(i);
            }
        }
        return individual;

    }

    //Function that selects the individual with the best fitness
    public Individual selectBestFit(ArrayList<Individual> selection){
        Individual individual;
        individual = selection.get(0);
        for(int i = 0; i < selection.size(); i++){
            if(selection.get(i).getFitness() > individual.getFitness()){
                individual = selection.get(i);
            }
        }
        return individual;


    }


    public void calculateTotalFitness(){
        for (int i = 0; i < listOfIndividuals.size(); i++){
            totalFitness += listOfIndividuals.get(i).getFitness();
        }
    }

    public ArrayList<Individual> getListOfIndividuals() {
        return listOfIndividuals;
    }

    public int getCrossoverValue() {
        return crossoverValue;
    }

    public double getMutationProbability() {
        return mutationProbability;
    }

    public ArrayList<DeliveryPackage> getCandidateSet(){
        return candidateSet;
    }

    public double getTotalFitness(){
        return totalFitness;
    }

}
