import java.util.*;
import java.io.*;

//Market
public class A1083331_checkpoint5_market {
    // Description : The name of this market.
    protected String m_name;
    // Description : The trading data of this market.
    protected ArrayList<String[]> trading_data;
    // Description : The HashMap of all stocks and market.
    protected HashMap<String, String> index_to_market;
    // Description : The variable that stores all the stocks belongs to this market.
    protected ArrayList<A1083331_checkpoint5_stock> stocks = new ArrayList<>();
    // Description : The trading bot of this market.
    trading_bot tb;
    // Description : The constructor of market.
    public A1083331_checkpoint5_market(String market_name, ArrayList<String[]> trading_data,HashMap<String, String> index_to_market) {
        this.m_name = market_name;
        this.index_to_market = index_to_market;
        this.trading_data = trading_data;
        tb = new trading_bot();
        this.trading_data=tb.trading_data_refinement();
        add_stocks();
        tb.auction_trading();
    }

    // Print out data
    public void summarize() {
        System.out.println(m_name+"\nindex,name,open,close,high,low,volume");
        /*********************************The TODO This Time (Checkpoint5) ********************************
         * 
         * TODO(4):  Here you have to summarize all stocks in this market.
         * Hint1: You could use "checkpoint5_stock.print_out()" to print out the data of a stock.
         * 
         **********************************The End of the TODO**************************************/

        /********************************************************************************************
         START OF YOUR CODE
         ********************************************************************************************/ 
        for(int count=0;count<this.stocks.size();count++){
        stocks.get(count).print_out();
        }
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/
        
    }

    // Search for stock
    public A1083331_checkpoint5_stock search_stock(String index){
        /*********************************The TODO This Time (Checkpoint5) ********************************
         * 
         * TODO(5): Here you return a checkpoint5_stock object, having the same index as parameter passed in, belongs to this market.
         * Hint1: Using search_stock("AAPL"), you will get a checkpoint5_stock object named AAPL.
         * Hint2: Return null if there's no corresponding stock.
         **********************************The End of the TODO**************************************/
        
        /********************************************************************************************
         START OF YOUR CODE
         ********************************************************************************************/ 
        for(int i=0;i<this.stocks.size();i++){
        if(index.equals(this.stocks.get(i).index)){   
        A1083331_checkpoint5_stock find= new A1083331_checkpoint5_stock("find","find1");
        find=this.stocks.get(i);
        return find;
        }
        }
        return null;   
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/

    }

    // Add stocks that belongs to the market
    public void add_stocks() {
        /*********************************The TODO This Time (Checkpoint5) ********************************
         * 
         * TODO(6):  Here you have to add stocks, belonging to this market, to the market.
         * Hint1: You could read in the NASDAQ.csv if this market is NASDAQ, by the same token, you could read in DJI.csv if this market is DJI.
         **********************************The End of the TODO**************************************/

        /********************************************************************************************
         START OF YOUR CODE
         ********************************************************************************************/ 
        try{
        if(this.m_name=="DJI"){       
        FileReader fileReader = new FileReader("DJI.csv");
        BufferedReader buff = new BufferedReader(fileReader);
        String stop=buff.readLine();
        while((stop=buff.readLine())!=null){
        String[] temp;
        temp=stop.split(",");
        A1083331_checkpoint5_stock a1=new A1083331_checkpoint5_stock(temp[0],temp[1]);
        this.stocks.add(a1);
        }
        }
        if(this.m_name=="NASDAQ"){       
        FileReader fileReader = new FileReader("NASDAQ.csv");
        BufferedReader buff = new BufferedReader(fileReader);
        String stop=buff.readLine();
        while((stop=buff.readLine())!=null){
        String[] temp;
        temp=stop.split(",");
        A1083331_checkpoint5_stock a1=new A1083331_checkpoint5_stock(temp[0],temp[1]);
        this.stocks.add(a1);
        }
        }
        }
        catch(IOException e){
            System.out.println("Oops!Tricks on you!");
            System.exit(0);
        }
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/

    }
    // Trading bot
    public class trading_bot {
        public trading_bot() {}       
        // Mactching trades based on the rule of auction trading.
        public void auction_trading() {
            //The market is open from 09:00:00 to 14:00:00.
            for (int i = 32400; i <= 50400; i += 60) {
                ArrayList<String[]> data_to_comfirmed = new ArrayList<>();
                for (String[] one : trading_data) {
                    if (Integer.parseInt(one[0]) >= i && Integer.parseInt(one[0]) <= i + 59)
                        data_to_comfirmed.add(one);
                    else
                        continue;
                }
                if (data_to_comfirmed.size() == 0)
                    continue;
                else{
                    for (String target_index : duplicate_trading_data(data_to_comfirmed)) {
                        ArrayList<String[]> filtered_trading_data = filter_by_index(target_index, data_to_comfirmed);
                        while(filtered_trading_data.size()>0){
                            String[] str=filtered_trading_data.get(0);
                            ArrayList<Integer> index_to_del=new ArrayList<>();
                            filtered_trading_data.remove(0);
                            int index_count=0;
                            while(index_count<filtered_trading_data.size()){
                                String[] candidate=filtered_trading_data.get(index_count);
                                //Trading request meets the requirement of same price and volume, also one is buyer, the other one is seller.
                                if(str[3].equals(candidate[3]) && str[4].equals(candidate[4]) && !str[5].equals(candidate[5])){
                                    index_to_del.add(index_count);
                                    search_stock(str[2]).update(Double.parseDouble(str[3]),Integer.parseInt(str[4]));
                                    break;
                                }
                                index_count+=1;
                            }
                            index_count=0;
                            for(int k:index_to_del){
                                filtered_trading_data.remove(k-index_count);
                                index_count+=1;
                            }
                        }
                    }
                }

            }
        }

        //Remove the trading data that doesn't belong to the market, and sort the data in the order of transcation time.
        public ArrayList<String[]> trading_data_refinement() {
            ArrayList<String[]> new_trading_data=new ArrayList<>();
            for(String[] str:trading_data){
                if (index_to_market.get((str[2])).equals(m_name))
                    new_trading_data.add(str);
            }
            Collections.sort(new_trading_data, new Comparator<String[]>() {
                @Override
                public int compare(String[] s1, String[] s2) {
                    if (Integer.parseInt(s1[0]) > Integer.parseInt(s2[0]))
                        return 1;
                    if (Integer.parseInt(s1[0]) < Integer.parseInt(s2[0]))
                        return -1;
                    else
                        return 0;
                }
            });
            return new_trading_data;
        }

        //Return trading datas that have the same index.
        public ArrayList<String> duplicate_trading_data(ArrayList<String[]> data_to_comfirmed) {
        /*********************************The TODO This Time (Checkpoint5) ********************************
         * 
         * TODO(7): This method will have inputs of numbers of trading data, you have to help find out stocks that have multiple trading requests, and return an ArrayList of stocks' indexs.
         * Hint1: e.g: "10:4:53,Crispula-4,GS,322.1,1,buy
         *              10:4:54,Crispula-6,GS,322.1,1,sell
         *              10:4:55,Crispula-7,GS,322.1,1,sell
         *              10:4:56,Crispula-8,AAPL,322.1,1,sell"
         * For this situation, stock "GS" had 3 trading requests and "AAPL" only got one, 
         * which means "GS" need for a further check for potential trading matchs but "AAPL" 
         * is no need for check because a successful match must have buyer and seller. 
         * Therefore, the method will return an ArrayList contained "GS".
         * Hint2: You may use HashMap for this.
         * 
         **********************************The End of the TODO**************************************/

        /********************************************************************************************
         START OF YOUR CODE
         ********************************************************************************************/ 
        ArrayList<String[]> d1=new ArrayList<>(); 
        d1=data_to_comfirmed; 
        HashMap<String,String> exp0 =new HashMap<>();
        try{
        FileReader fileReader0 = new FileReader("DJI.csv");
        BufferedReader buff0 = new BufferedReader(fileReader0);
        String stop0=buff0.readLine();
        while((stop0=buff0.readLine())!=null){
        String[] temp0;
        temp0=stop0.split(",");
        exp0.put(temp0[0],"DJI");
        }
        buff0.close();
        FileReader fileReader1 = new FileReader("NASDAQ.csv");
        BufferedReader buff1 = new BufferedReader(fileReader1);
        String stop1=buff1.readLine();
        while((stop1=buff1.readLine())!=null){
        String[] temp1;
        temp1=stop1.split(",");
        exp0.put(temp1[0],"NASDAQ");
        }
        buff1.close();       
        }
        catch(IOException e){
            System.out.println("Oops!Tricks on you!");
            System.exit(0);
        }      
        try{
        ArrayList<A1083331_checkpoint5_stock> stocks = new ArrayList<>();
        if(exp0.get(d1.get(0)[2]).equals("DJI")){   
        FileReader fileReader = new FileReader("DJI.csv");
        BufferedReader buff = new BufferedReader(fileReader);
        String stop=buff.readLine();
        while((stop=buff.readLine())!=null){
        String[] temp;
        temp=stop.split(",");
        A1083331_checkpoint5_stock a1=new A1083331_checkpoint5_stock(temp[0],temp[1]);
        stocks.add(a1);
        }
        }
        if(exp0.get(d1.get(0)[2]).equals("NASDAQ")){   
        FileReader fileReader = new FileReader("NASDAQ.csv");
        BufferedReader buff = new BufferedReader(fileReader);
        String stop=buff.readLine();
        while((stop=buff.readLine())!=null){
        String[] temp;
        temp=stop.split(",");
        A1083331_checkpoint5_stock a1=new A1083331_checkpoint5_stock(temp[0],temp[1]);
        stocks.add(a1);
        }
    }   
}
            catch(IOException e){
            System.out.println("Oops!Tricks on you!");
            System.exit(0);
        }
        ArrayList<String> no =new ArrayList<>();
        HashMap<Integer,String> exp1 =new HashMap<>();
        for(int c=0;c<stocks.size();c++){
        exp1.put(c,stocks.get(c).index);
        }
        for(int c1=0;c1<stocks.size();c1++){
            int count=0;
            for(int x=0;x<d1.size();x++){   
                String temp; 
                temp=d1.get(x)[2];
                if(exp1.get(c1).equals(temp)){
                        count=count+1;       
                        if(count==2){    
                                no.add(exp1.get(c1));
                                break;
                }
            }    
        }
    }
        return no;
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/
        }

        //Filter out trading datas that have the same index with target_index.
        public ArrayList<String[]> filter_by_index(String target_index, ArrayList<String[]> datas) {
        /*********************************The TODO This Time (Checkpoint5) ********************************
         * 
         * TODO(8): After finding out stocks that need a further check, you have to return trading datas with index specified
         * as "target_index" among trading datas that all need a further check.
         * Hint1: e.g: example_data="10:4:53,Crispula-4,GS,322.1,1,buy
         *              10:4:54,Crispula-6,GS,322.1,1,sell,
         *              10:4:55,Crispula-7,GS,322.1,1,sell,
         *              10:4:56,Crispula-8,AAPL,322.1,1,sell,
         *              10:4:57,Crispula-9,AAPL,322.1,1,sell,
         *              10:4:57,Crispula-10,AAPL,322.1,1,sell"
         * Using filter_by_index("AAPL",example_data) will return "10:4:56,Crispula-8,AAPL,322.1,1,sell,10:4:56,Crispula-9,AAPL,322.1,1,sell,10:4:56,Crispula-10,AAPL,322.1,1,sell".

         **********************************The End of the TODO**************************************/


        /********************************************************************************************
         START OF YOUR CODE
         ********************************************************************************************/
        ArrayList<String[]> fdata= new ArrayList<>();
        String[] row;
        for(int i=0;i<datas.size();i++){
        if(datas.get(i)[2].equals(target_index)){
            row=datas.get(i);
            fdata.add(row);
        }    
        }
        return fdata;
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/

        }
    }
}
