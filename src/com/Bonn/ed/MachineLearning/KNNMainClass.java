package com.Bonn.ed.MachineLearning;

import java.sql.DataTruncation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.Bonn.ed.MachineLearning.Beans.AccuracyMonitorBean;
import com.Bonn.ed.MachineLearning.Beans.EntireDataSet;
import com.Bonn.ed.MachineLearning.Beans.singleInstance;
import com.Bonn.ed.MachineLearning.Reader.DataReader;
import com.Bonn.ed.MachineLearning.Util.KDDNearsetNeigborFinder;
import com.Bonn.ed.MachineLearning.Util.MajorityVoteCalulator;
public class KNNMainClass {

	
	public static void main(String [] args)
	{
		if (args.length != 1)
	    {
	      System.err.println("You must call MainClass with one parameter=path to the file ") ;
	      System.exit(1);
	    }
		
		DataReader reader=new DataReader(args[0]);
		EntireDataSet dataSets=reader.readData();
		
		// remove the header because it is not instance
		dataSets.getEntireFileExamples().remove(0);
		
		
		KDDNearsetNeigborFinder nearestPointsFinder=new KDDNearsetNeigborFinder();
		MajorityVoteCalulator majorityCalculator=new MajorityVoteCalulator();
		AccuracyMonitorBean [] confusionMatrix=checkAccuracy(dataSets);
		for(int i=0;i<confusionMatrix.length;i++)
		{
			System.out.println("printing confusion matrix for K="+(i+1));
			System.out.println("[tp="+confusionMatrix[i].tp+",fn="+confusionMatrix[i].fn+"]");
			System.out.println("[fp="+confusionMatrix[i].fp+",tn="+confusionMatrix[i].tn+"]");
			double count=confusionMatrix[i].tp+confusionMatrix[i].fn+confusionMatrix[i].fp+confusionMatrix[i].tn;
			System.out.println("total="+count);
			double accuracy=(confusionMatrix[i].tn+confusionMatrix[i].tp)/count;
			System.out.println("the accuracy is:"+accuracy);
			double prec=(confusionMatrix[i].tp)/(confusionMatrix[i].tp+confusionMatrix[i].fp);
			System.out.println("precision="+prec);
			
			double rec=(confusionMatrix[i].tp)/(confusionMatrix[i].tp+confusionMatrix[i].fn);
			System.out.println("recall="+rec);
			
			// F-measure
			double K=i+1;
			
			//double F_Measure=1/((K/prec)+((1-K)/rec));
			double F_Measure=2*((prec*rec)/(prec+rec));
			System.out.println("F-Measure="+F_Measure);
			System.out.println();
			
		}
		
		/*
		// question B
		while(randomIndicies.size()!=5)
		{
			Random rn = new Random();
			int randomIndex = rn.nextInt(dataSets.getEntireFileExamples().size()) + 0;
			// get random instance
			singleInstance queryPoint=dataSets.getEntireFileExamples().get(randomIndex);
			// check not used before
			if(!randomIndicies.contains(randomIndex))
			{
				System.out.println("------------------------------");
				System.out.println("select instance number:"+randomIndex);
				randomIndicies.add(randomIndex);
				for(int j=1;j<=3;j++)
				{
					System.out.println("compute nearest "+j+" elements");
					HashMap<singleInstance,Double> nearestPoints= nearestPointsFinder.getNearestNeighbors(j, dataSets, queryPoint, randomIndex);
					boolean targetValue=majorityCalculator.getMajorityVote(nearestPoints,true);
					System.out.println("target value after compute the nearest "+j+ " values is:"+targetValue);
					
					
				}
			}
		}
		*/
		
		// question C
		
		//for(int i=1;i<=3;i++)
		//{
			//System.out.println("number of correct classification for K="+i+" is "+checkAccuracy(i, dataSets));
			
		//}
		///System.out.println("the accuracy is"+checkAccuracy(j, queryPoint,randomIndex, dataSets, targetValue));
		
				
				
	}
	
	
	private static AccuracyMonitorBean[] checkAccuracy(EntireDataSet dataSet)
	{
		AccuracyMonitorBean []result=new AccuracyMonitorBean[3];
		result[0]=new AccuracyMonitorBean();
		result[1]=new AccuracyMonitorBean();
		result[2]=new AccuracyMonitorBean();
		
		KDDNearsetNeigborFinder nearestPointsFinder=new KDDNearsetNeigborFinder();
		MajorityVoteCalulator majorityCalculator=new MajorityVoteCalulator();
		for(int K=1;K<=3;K++)
		{
			for(int i=0;i<dataSet.getEntireFileExamples().size();i++)
			{
				//System.out.println("the value of i is:"+i);
				singleInstance testInstance=dataSet.getEntireFileExamples().get(i);
				HashMap<singleInstance,Double> nearestPoints= nearestPointsFinder.getNearestNeighbors(K, dataSet, testInstance, i);
				boolean predictedValue=majorityCalculator.getMajorityVote(nearestPoints,false);
				boolean actualValue="yes".equals(testInstance.getEntry()[testInstance.getEntry().length-1].trim().toLowerCase());
				
				if(predictedValue==true&&actualValue==true)
					result[K-1].tp++;
				else if(predictedValue==true&&actualValue==false)
					result[K-1].fp++;
				else if(predictedValue==false&&actualValue==false)
					result[K-1].tn++;
				else if(predictedValue==false&&actualValue==true)
					result[K-1].fn++;
			
			}
		}
		
		return result;
	}
	
	/*
	private static double checkAccuracy(int K,singleInstance paramInstance,int instanceIndex,EntireDataSet dataSet,boolean targetFunction)
	{
		double result=0.0;
		double numberOfMatch=0;
		KDDNearsetNeigborFinder nearestPointsFinder=new KDDNearsetNeigborFinder();
		MajorityVoteCalulator majorityCalculator=new MajorityVoteCalulator();
		
		for(int i=0;i<dataSet.getEntireFileExamples().size();i++)
		{
			//EntireDataSet filteredDataset=getElementsExcept(dataSet, i);
			
			HashMap<singleInstance,Float> nearestPoints= nearestPointsFinder.getNearestNeighbors(K, dataSet, paramInstance, i,instanceIndex);
			boolean targetValue=majorityCalculator.getMajorityVote(nearestPoints,false);
			if(targetValue==targetFunction)
				numberOfMatch++;
		}
		
		return (numberOfMatch/dataSet.getEntireFileExamples().size());
	}
	*/
	
}
