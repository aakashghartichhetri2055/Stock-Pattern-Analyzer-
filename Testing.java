

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class Testing {

	public static void main(String[] args) {
		 ArrayList<String> Stock = new ArrayList<String>();
		 ArrayList<String> ETF = new ArrayList<String>();
		 Stock= reader("/Users/aakashXettri/Documents/untitled folder/ssspp/src/Data/Stocks.txt");//read the file stocks.txt and add the stock name in arraylist
		ETF= reader("/Users/aakashXettri/Documents/untitled folder/ssspp/src/Data/ETFs.txt");
		 double[] RiskFactor ={0.5, 1, 2, 5, 10};
			Vector<Trade> Trades = new Vector<Trade>(3000);

		 for(int k = 0; k < 5; k++) {
			for(int i=0;i<Stock.size();i++) {

				symbolTester tester = new symbolTester(Stock.get(i), "/Users/aakashXettri/Documents/untitled folder/ssspp/src/Data/", RiskFactor[k]);
				tester.test();
				Trades.addAll(tester.getTrades());
			}
		
		
	
	
	 int LongWinner=0;
	 int HoldingPeriod=0;
		//int Longloser=0;
		int shortWinner=0;
		//int shortLoser=0;
		int LongTrade=0;
		int shortTrade=0;
		double Longprofit=0;
		double shortProfit=0;
			for(int i=0; i<Trades.size();i++)
			{
				HoldingPeriod+=Trades.elementAt(i).getHoldingPeriod();
				if(Trades.elementAt(i).percentPL()>=0)
				{
					if(Trades.elementAt(i).getDir()==Direction.LONG)
					{
						LongWinner++;
						LongTrade++;
						Longprofit+=(Trades.elementAt(i).percentPL());
					
					}
					else {
						shortWinner++;
						shortTrade++;
						shortProfit=(Trades.elementAt(i).percentPL());
					}
					
				}
				else
				{
					if(Trades.elementAt(i).getDir()==Direction.SHORT)
					{
						//shortLoser++;
						shortTrade++;
					}
					else {
						//Longloser++;
						LongTrade++;
					}
				
				}
			}
			
			System.out.println("-------------Results for riskFactor: " + RiskFactor[k]+ "---------------------------------");
			
			int TotalTrade= Trades.size();
			System.out.println("Total Trade: "+ TotalTrade+" LongTrade: "+ LongTrade+" shortTrade: "+ shortTrade + " Long Profit: "+ Longprofit+ " short Profit: "+ shortProfit);
            
            double Percentwinners= ((LongWinner+shortWinner)*100.0)/TotalTrade;
            System.out.println("Percent winners: "+Percentwinners+ "%");
            
            double percentLongWinner=(LongWinner*100.0)/LongTrade;
            System.out.println("Long Winner percentage: "+percentLongWinner+ "%");
            
            double percentshortWinner=(shortWinner*100.0)/shortTrade;
            System.out.println("short Winner percentage: "+percentshortWinner+ "%");
            
            double averageProfit= (Longprofit+shortProfit)/TotalTrade;
            System.out.println("Average Profit: "+ averageProfit);
            
            double averageLongProfit= Longprofit/LongTrade;
            System.out.println("Average Long Profit: "+ averageLongProfit);

            double averageShortProfit= shortProfit/LongTrade;
            System.out.println("Average Short Profit: "+ averageShortProfit);
          
            
            
            double avgHoldingPeriod =(double)HoldingPeriod/TotalTrade;
            System.out.println("Average Holding Period: "+ avgHoldingPeriod);
            
            double averageProfitPerHoldingPeriod = averageProfit/avgHoldingPeriod;
            System.out.println("Average Profit per Holding Period: "+ averageProfitPerHoldingPeriod);
		 }
			
            
         Trades = new Vector<Trade>(3000);

            
            //Simulations for ETFs
         System.out.println("--------------------For ETF trade-----------------------------");
            for(int p = 0; p < 5; p++) {
    			for(int i=0;i<ETF.size();i++) {

    				symbolTester tester = new symbolTester(ETF.get(i), "/Users/shankaralemagar/Documents/untitled folder/ssspp/src/Data/", RiskFactor[p]);
    				tester.test();
    				Trades.addAll(tester.getTrades());
    			}
            

            //compute the stats for ETFs as you did for stocks
			int eLongWinner=0;
			 int eHoldingPeriod=0;
				//int Longloser=0;
				int eshortWinner=0;
				//int shortLoser=0;
				int eLongTrade=0;
				int eshortTrade=0;
				double eLongprofit=0;
				double eshortProfit=0;
					for(int i=0; i<Trades.size();i++)
					{
						eHoldingPeriod+=Trades.elementAt(i).getHoldingPeriod();
						if(Trades.elementAt(i).percentPL()>=0)
						{
							if(Trades.elementAt(i).getDir()==Direction.LONG)
							{
								eLongWinner++;
								eLongTrade++;
								eLongprofit+=(Trades.elementAt(i).percentPL());
							
							}
							else {
								eshortWinner++;
								eshortTrade++;
								eshortProfit=(Trades.elementAt(i).percentPL());
							}
							
						}
						else
						{
							if(Trades.elementAt(i).getDir()==Direction.SHORT)
							{
								//shortLoser++;
								eshortTrade++;
							}
							else {
								//Longloser++;
								eLongTrade++;
							}
						
						}
					}
					
					System.out.println("-------------Results for riskFactor: " + RiskFactor[p]+ "---------------------------------");
					
					int eTotalTrade= Trades.size();
					System.out.println("Total Trade: "+ eTotalTrade+" LongTrade: "+ eLongTrade+" shortTrade: "+ eshortTrade + " Long Profit: "+ eLongprofit+ " short Profit: "+ eshortProfit);
		            
		            double ePercentwinners= ((eLongWinner+eshortWinner)*100.0)/eTotalTrade;
		            System.out.println("Percent winners: "+ePercentwinners+ "%");
		            
		            double epercentLongWinner=(eLongWinner*100.0)/eLongTrade;
		            System.out.println("Long Winner percentage: "+epercentLongWinner+ "%");
		            
		            double epercentshortWinner=(eshortWinner*100.0)/eshortTrade;
		            System.out.println("short Winner percentage: "+epercentshortWinner+ "%");
		            
		            double eaverageProfit= (eLongprofit+eshortProfit)/eTotalTrade;
		            System.out.println("Average Profit: "+ eaverageProfit);
		            
		            double eaverageLongProfit= eLongprofit/eLongTrade;
		            System.out.println("Average Long Profit: "+ eaverageLongProfit);

		            double eaverageShortProfit= eshortProfit/eLongTrade;
		            System.out.println("Average Short Profit: "+ eaverageShortProfit);
		          
		            
		            
		            double eavgHoldingPeriod =(double)eHoldingPeriod/eTotalTrade;
		            System.out.println("Average Holding Period: "+ eavgHoldingPeriod);
		            
		            double eaverageProfitPerHoldingPeriod = eaverageProfit/eavgHoldingPeriod;
		            System.out.println("Average Profit per Holding Period: "+ eaverageProfitPerHoldingPeriod);
		            
		            
		 }
		 }
	
		

	@SuppressWarnings({ "unchecked", "unchecked" })
	public static <T> ArrayList<T> reader(String s)
	{
		
		@SuppressWarnings("rawtypes")
		ArrayList<T> list= new ArrayList();
		try {
			FileReader fr = new FileReader(s);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			list.add((T) line);
			while((line = br.readLine()) != null) {
				//create a bar using the constructor that accepts the data as a String
				//add the bar to the Vector 
				 list.add((T) line);
			}
			br.close();
			fr.close();
		}catch(IOException e) {
			System.out.println("Something is wrong: " + e.getMessage());
			return list;
		}
		return list;
		
		
	}

}