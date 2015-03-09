package com.Bonn.ed.MachineLearning.Beans;

import java.util.ArrayList;
import java.util.List;

public class singleInstance {

	
	private String [] Entry;
	
	public singleInstance(String [] Entry)
	{
		this.Entry=Entry;
	}
	
	public String[] getEntry() {
		return Entry;
	}

	public void setEntry(String[] entry) {
		Entry = entry;
	}

	/*
	 * this function get the distance between two samples with high dimensions
	 */
	
	public double getDistance(singleInstance param)
	{
		float result=0;
		for(int i=0;i<Entry.length-1;i++)// because the last one is the goal
		{
			if(isInteger(Entry[i])) // numeric // get the square of it
			{
				result+=Math.pow(Integer.parseInt(Entry[i])-Integer.parseInt(param.Entry[i]),2);
			}
			else // binary or categorical
			{
				if(!Entry[i].toUpperCase().trim().equals(param.Entry[i].toUpperCase().trim()))
				{
					result++; // increase one if they are not the same
				}
				// else if they are the same , we add zero
				
			}
		}
		
		// return square root of the summation
		return Math.sqrt(result);
		
	}
	
	
	private  boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}
	
}
