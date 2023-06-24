package src.CL_Assignments;
import java.util.Scanner;
public class Ex1_20220808011 {
    public static void main(String[] args) {
        //Test for 1st exercise
        Stock stock = new Stock("ORCL", "Oracle Corporation");
        stock.currentPrice = 34.35;
        stock.previousClosingPrice = 34.5;
        double a = stock.getChangePercent();
        System.out.println(a);
        //Test for 2nd exercise and bonus question
        System.out.println("Fan number: ");
        Scanner input = new Scanner(System.in);
        int fan_number = input.nextInt();
        Fan[] arr = new Fan[fan_number];
        Fan fan = new Fan();
        //Making radius go one up and turning the color to yellow if they are odd
        for (int i = 1; i < arr.length + 1; i++) {
            if (i % 2 != 0) {
                arr[i - 1] = new Fan(fan.getRadius() + 1, "Yellow");
            } else {
                arr[i - 1] = fan;
            }
        }
    for (int i = 0; i < arr.length; i++){
        System.out.println(arr[i]);
    }
    }
        public static void bonusQuestion(Fan[] arr) {
            for (int i = 1; i < arr.length+1; i++) {
                if (i % 3 == 0) {
                    if (arr[i-1].isOn()) {

                        if (arr[i-1].getSpeed() == 3) {
                            arr[i-1].setSpeed(1);
                        } else {
                            arr[i-1].setSpeed(arr[i-1].getSpeed() + 1);
                        }
                    }
                }
            }
        }
}
    class Stock {
        String symbol;
        String name;
        double previousClosingPrice;
        double currentPrice;
        Stock(String symbol, String name) {
            this.symbol = symbol;
            this.name = name;
        }
        public double getChangePercent() {
            double temp = (currentPrice - previousClosingPrice) / currentPrice;
            return temp * 100;
        }
    }
    class Fan{

    final int SLOW = 1;
        final int MEDIUM = 2;
        final int FAST = 3;

        private int speed;
        private boolean on;
        private double radius;
        String color;
        Fan(){
            radius = 5;
            on = false;
            speed = SLOW;
            color = "blue";
        }
        Fan(double radius, String color){
        this.color = color;
        this.radius = radius;
        }

        public double getRadius() {
            return radius;
        }
        public int getSpeed() {
            return speed;
        }

        public boolean isOn() {
            return on;
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public void setOn(boolean on) {
            this.on = on;
        }

        public void setRadius(double radius) {
            this.radius = radius;
        }
       public String toString(){
            String speed_but_string;
            if (on){
                if (speed == 1){
                    speed_but_string = "SLOW";
                }else if (speed == 2) {
                    speed_but_string = "MEDIUM";
                }else{
                    speed_but_string = "FAST";
                }
                return (speed_but_string +" "+ color +" " + radius);
            }else{
                return color + " "+ radius+ " "+ "fan is off";
            }

        }
}
