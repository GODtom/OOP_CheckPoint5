//Stock class
public class A1083331_checkpoint5_stock {
    public String index;
    public String name;
    public double open = 0, close = 0, high = 0, low = 0;
    public int volume = 0;

    public A1083331_checkpoint5_stock(String index, String name) {
        /*********************************The TODO This Time (Checkpoint5) ********************************
         * 
         * TODO(9):  Here you have to set up the constructor for this class.
         * Hint1: You have to set up some variable for this class based on parameters passed in.
         * 
         **********************************The End of the TODO**************************************/

        /********************************************************************************************
         START OF YOUR CODE
         ********************************************************************************************/ 
        this.index=index;
        this.name=name;
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/

    }
    //Updating status of the stock
    public void update(double c_price,int c_volume) {
        /*********************************The TODO This Time (Checkpoint5) ********************************
         * 
         * TODO(10):  Here you update the status of stock based on the current price that passed in as c_price. 
         * After a successful trading match, you have to update the status of this stock.
         * Hint1: For one successful trading match, you should update the vaiable of "volume" .
         * e.g: "10:4:54,Crispula-6,GS,322.1,5,sell
         *       10:4:55,Crispula-7,GS,322.1,5,buy"
         * For this situation, the volume of "GS" should be plussed 5.
         * Hint2: Also, if the current price is the highest of the day by far, then it should be recorded as 
         * the highest of the day, which also applys to the lowest of the day.
         * 
         **********************************The End of the TODO**************************************/

        /********************************************************************************************
         START OF YOUR CODE
         ********************************************************************************************/ 
        if(this.open==0){
           this.open=c_price;
        }
        if(this.low==0){
            this.low=c_price;
        }
        this.volume=this.volume+c_volume;
        if(this.high<c_price){
            this.high=c_price;
        }
        if(this.low>c_price){
            this.low=c_price;
        }
        this.close=c_price;
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/

    }

    public void print_out() {
        System.out.println(index + "," + name +","+open +"," + close + "," + high + "," + low +  "," + volume);
    }

}
