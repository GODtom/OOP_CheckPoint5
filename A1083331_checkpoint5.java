import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class A1083331_checkpoint5 {
    public static void main(String[] args) {
        // Description : The name of file to read in as the trading data.
        String listName = args[0];

        /*********************************The TODO This Time (Checkpoint5) ********************************
         * 
         * TODO(1): This time, you have to read in the trading data from csv file and make trading matching for each market, and last make an summary of each market.
         * Hint1: e.g: Use "checkpoint5_market DJI = new checkpoint5_market("DJI", import_data(listName), data_classification())" to create a object of checkpoint5_market, named "DJI".
         * Hint2: e.g: Use "import_data(listName)" to query data from the csv file. 
         * Hint3: e.g: Use "data_classification()" to record the market of each stock.
         * Hint4: e.g: Use "checkpoint5_market.summarize()" to print out the final result of the day.
         * Hint5: For the further usage of HashMap, you could check TODO3.
         * Hint6: IMPORTANT! YOU HAVE TO """FIRST""" SUMMARIZE DJI THEN NASDAQ. In other words,
         * you should print out these stocks' details in the order of"AXP,BA,CAT,CVX,,DD,DIS,GE,GS,HD,IBM,JNJ,AAPL,ADBE,ADP,ADSK,AKAM,ALTR,AMAT,AMGN,AMZN,APOL" 
         * Hint7: ********  Please make sure the output is same as the pictures in document.    ********
         **********************************The End of the TODO**************************************/


        /********************************************************************************************
         START OF YOUR CODE
         ********************************************************************************************/ 
        ArrayList<String[]> data= new ArrayList<>();        
        data=import_data(listName);
        HashMap<String,String> exp1 =new HashMap<>();//this will make hashmap object
        exp1=data_classification();
        A1083331_checkpoint5_market dji =new A1083331_checkpoint5_market("DJI",data,exp1);//market test start
        dji.summarize();
        A1083331_checkpoint5_market dji1 =new A1083331_checkpoint5_market("NASDAQ",data,exp1);
        dji1.summarize();
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/
        
    }

    // Import data form specified csv file.
    public static ArrayList<String[]> import_data(String listName) {

        /*********************************The TODO This Time (Checkpoint5) ********************************
         * 
         * TODO(2): Here you have to read in the csv file and return the trading data as ArrayList. Also, you have to
         *  convert the transaction time to seconds.
         * Hint1: e.g: For "11:30:1" will be converted as "41401".(11hr*60*60)+(30min*60)+1= 41401sec.
         * Hint2: The parameter "listName" will be like "File_name.csv"(e.g: "trading_data.csv").
         * 
         **********************************The End of the TODO**************************************/

        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/ 
         ArrayList<String[]> data1= new ArrayList<>(); 
         try{
        FileReader fileReader = new FileReader(listName);
        BufferedReader buff = new BufferedReader(fileReader);
        String stop=buff.readLine();
        int i=0;
        while((stop=buff.readLine())!=null){
        String[] read1= {stop};
        int hour,min,sec;
        String temp1;
        String[] temp2,temp3;
        temp1=read1[0].substring(0,10);
        temp2=temp1.split(",");
        temp3=temp2[0].split(":");
        hour=Integer.valueOf(temp3[0]);
        min = Integer.valueOf(temp3[1]);
        sec = Integer.valueOf(temp3[2]);
        int totalsec=hour*3600+min*60+sec;
        String strsec = String.valueOf(totalsec);
        String[] read2={""};
        if(temp2[0].length()==5){ 
        String temp4,temp5;
        temp4=read1[0].substring(5,read1[0].length());
        temp5=temp4.replaceFirst(",","K,");
        temp4=temp5.replaceFirst("K",strsec);
        read2[0]= temp4;
        }
        else if(temp2[0].length()==6){
        String temp4,temp5;
        temp4=read1[0].substring(6,read1[0].length());
        temp5=temp4.replaceFirst(",","K,");
        temp4=temp5.replaceFirst("K",strsec);
        read2[0]= temp4;
        }
        else if(temp2[0].length()==7){
        String temp4,temp5;
        temp4=read1[0].substring(7,read1[0].length());
        temp5=temp4.replaceFirst(",","K,");
        temp4=temp5.replaceFirst("K",strsec);
        read2[0]= temp4;   
        }
        else if(temp2[0].length()==8){
        String temp4,temp5;
        temp4=read1[0].substring(8,read1[0].length());
        temp5=temp4.replaceFirst(",","K,");
        temp4=temp5.replaceFirst("K",strsec);
        read2[0]= temp4; 
        }
        String[] cut=read2[0].split(",");
        data1.add(cut); 
        i++;
        }
        buff.close();
        return data1;
        }
        catch(IOException e){
            System.out.println("Oops!Tricks on you!");
            System.exit(0);
            return data1;
        }
        catch(NumberFormatException e) { 
            System.out.println("There is a invalid format in the csv file you import !"); 
            System.exit(0);
            return data1;
        } 
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/

    }
        public static HashMap<String, String> data_classification() {
        /*********************************The TODO This Time (Checkpoint5) ********************************
         * 
         * Q1:   What is HashMap?
         * A1:   HashMap stores datas in pairs of keys and values. That is to say i can get values through keys , vice versa.
         * 
         * Q2:   What is key and value?
         * A2:   A key refers to only one value. e.g: Pan is stupid, and John is smart. Both "Pan" and "John" are keys, and "stupid" and "smart" are values.
         * 
         * Q3:   How to refer values to keys?
         * A3:   Use HashMap.put(key,value). e.g: HashMap.put("Pan","stupid") , HashMap.put("John","smart").
         * 
         * Q4:   How can i get values through keys?
         * A4:   Use HashMap.get(key) will return the corresponding value. e.g: HashMap.get("Pan") will return "stupid".
         * 
         * 
         * TODO(3): Here you have make a HashMap of stocks names and market name. The keys are stocks' names and values are markets' names.
         * Hint1: If i use HashMap.get("AAPL") then i will get "NASDAQ".(AAPL belongs to NASDAQ)
         * Hint2: The HashMap will help you get the market's name to which a stock belongs. 
         * Hint3: There are two market: DJI and NASDAQ.
         **********************************The End of the TODO**************************************/

        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/ 
        HashMap<String,String> exp =new HashMap<>();
        try{
        FileReader fileReader = new FileReader("DJI.csv");
        BufferedReader buff = new BufferedReader(fileReader);
        String stop=buff.readLine();
        while((stop=buff.readLine())!=null){
        String[] temp;
        temp=stop.split(",");
        exp.put(temp[0],"DJI");
        }
        buff.close();
        FileReader fileReader1 = new FileReader("NASDAQ.csv");
        BufferedReader buff1 = new BufferedReader(fileReader1);
        String stop1=buff1.readLine();
        while((stop1=buff1.readLine())!=null){
        String[] temp1;
        temp1=stop1.split(",");
        exp.put(temp1[0],"NASDAQ");
        }
        buff1.close();
        return exp;        
        }
        catch(IOException e){
            System.out.println("Oops!Tricks on you!");
            System.exit(0);
            return exp;
        }
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/
    }

}