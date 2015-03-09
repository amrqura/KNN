package com.Bonn.ed.MachineLearning.Util;

import java.util.HashMap;

import com.Bonn.ed.MachineLearning.Beans.singleInstance;

public class MajorityVoteCalulator {

	public boolean getMajorityVote(HashMap<singleInstance,Double> param,boolean print)
	{
		int YesCount=0;
		int NoCount=0;
		for (singleInstance instance: param.keySet()){
			if(instance.getEntry()[instance.getEntry().length-1].trim().toUpperCase().equals("YES"))
				YesCount++;
			else
				NoCount++;
		}
		if(print)
			System.out.println("got "+YesCount+": true , and "+NoCount+": False");
		if(YesCount>NoCount)
			return true;
		return false;
		
	}
	
}
