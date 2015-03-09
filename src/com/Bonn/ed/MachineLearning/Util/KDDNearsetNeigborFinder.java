package com.Bonn.ed.MachineLearning.Util;

import java.awt.image.SinglePixelPackedSampleModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import com.Bonn.ed.MachineLearning.Beans.EntireDataSet;
import com.Bonn.ed.MachineLearning.Beans.singleInstance;

public class KDDNearsetNeigborFinder {

	/*
	 * this function get the nearest K neighbors and return hashMap of each neighbor and the distance
	 */
	public HashMap<singleInstance,Double> getNearestNeighbors(int K,EntireDataSet trainginSets,singleInstance requiredInstance,int instanceIndex)
	{
		HashMap<singleInstance,Double> result=new HashMap<singleInstance,Double>();
		for(int i=0;i<trainginSets.getEntireFileExamples().size();i++)
		{
			// don't compute the nearest neigbour with my self
			if(i!=instanceIndex )
			{
				singleInstance tmpInstance=trainginSets.getEntireFileExamples().get(i);
				// fill the map first
				if(result.size()<K)
				{
					result.put(tmpInstance,getDistance(tmpInstance, requiredInstance));
				}
				else
				{
					double currentDistance=getDistance(tmpInstance, requiredInstance);
					result=fitInstanceLocation(tmpInstance, currentDistance, result);
				
				}
			}
		}
		
		
		return result;
	}
	
	private void printMap(HashMap<singleInstance, Double> param)
	{
        //System.out.println("----------------------------");

		for (singleInstance name: param.keySet()){

            String key =name.toString();
            String value = param.get(name).toString();  
            System.out.println(key + " " + value);  


		}
	}
	
	
	/*
	 * sort the hashMap
	 * return sorted hashSet of the values
	 */
	private HashMap<singleInstance, Double> sortList(HashMap<singleInstance, Double> map)
	{
		HashMap<singleInstance, Double> sortedValues = new HashMap<singleInstance, Double>();
		
        //System.out.println("----------------------------");

        
        //printMap(map);
		
		
        //ValueComparator bvc =  new ValueComparator(map);
        //TreeMap<singleInstance,Float> sorted_map = new TreeMap<singleInstance,Float>(bvc);
        
        //sorted_map.putAll(map);
        
        //sortedValues.putAll(sorted_map);
        sortedValues=(HashMap<singleInstance, Double>) MapUtil.sortByValue(map);
        
        //System.out.println("----------------------------");
        //printMap(sortedValues);
		
		return sortedValues;
	}
	
	class ValueComparator implements Comparator<singleInstance> {

	    Map<singleInstance, Double> base;
	    public ValueComparator(Map<singleInstance, Double> base) {
	        this.base = base;
	    }

	    // Note: this comparator imposes orderings that are inconsistent with equals.    
	    public int compare(singleInstance a, singleInstance b) {
	        if (base.get(a) <= base.get(b)) {
	            return -1;
	        } else {
	            return 1;
	        } // returning 0 would merge keys
	    }
	}
	
	/*
	 * return distance between two instances
	 */
	private double getDistance(singleInstance a,singleInstance b)
	{
		return a.getDistance(b);
		
	}

	/*
	 * utility function to insert the instance in correct position
	 */
	private HashMap<singleInstance, Double> fitInstanceLocation(singleInstance currentInstance,double distance,HashMap<singleInstance, Double> sortedInstances)
	{
		// add the instance in the Map
		sortedInstances.put(currentInstance, distance);
		//sort them ASC
		sortedInstances=sortList(sortedInstances);
		// remove the last "biggest" one
		singleInstance lastInstance=(singleInstance)sortedInstances.keySet().toArray()[sortedInstances.keySet().size()-1];
		
		sortedInstances.remove(lastInstance);
		//printMap(sortedInstances);
		return sortedInstances;
		
		
	}

}
