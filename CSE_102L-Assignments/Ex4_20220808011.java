import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Ex04_20220808011 {

}


class Computer{
    protected CPU cpu;
    protected RAM ram;
    Computer(CPU cpu, RAM ram){
        this.ram = ram;
        this.cpu = cpu;
    }
    public void run(){
        int total = 0;
        int capacityOfRam = ram.getCapacity();
        for(int i = 0; i < capacityOfRam; i++){
                int temp = ram.getValue(i,i);
                total = cpu.compute(temp,total);
          }
        ram.setValue(0,0,total);
    }
    @Override
    public String toString() {
        return "Computer: "+ cpu + " "+ ram;
    }
}




class Laptop extends Computer{
 private int milliAmp;
 private int battery;

    Laptop(CPU cpu, RAM ram, int milliAmp) {
        super(cpu,ram);
        this.battery = (milliAmp * 3)/10;
        this.milliAmp = milliAmp;
    }
public int batteryPercentage(){
        return (battery*100)/milliAmp;
}
public void charge(){
  while (batteryPercentage() < 90){
      battery += milliAmp/50;
  }
 }
@Override
    public void run(){
     if (batteryPercentage() > 5){
         battery -= (milliAmp/100)*3;
         super.run();
     }else{
       charge();
     }
 }

    @Override
    public String toString() {
       return super.toString()+ " "+ battery;
    }
}
class Desktop extends Computer{
    private ArrayList<String> peripherals;
    Desktop(CPU cpu, RAM ram, String... peripherals) {
        super(cpu,ram);
        this.peripherals = new ArrayList<String>(Arrays.asList(peripherals));
    }

    @Override
    public void run() {
        int total = 0;
        for (int i = 0; i < ram.getCapacity(); i++) {
            for (int j = 0; j < ram.getCapacity(); j++) {
                int temp = ram.getValue(i,j);
                total = cpu.compute(temp,total);
            }
        }
        ram.setValue(0,0,total);
    }
   public void plugIn(String peripheral){
        peripherals.add(peripheral);
   }
   public String plugOut(){
        int last = peripherals.size() - 1;
        return peripherals.remove(last);
   }
   public String plugOut(int index) {
       return peripherals.remove(index);
    }

    @Override
    public String toString() {
        String added = "";
        for (int i = 0; i < peripherals.size(); i++){
            added += " "+peripherals.get(i);
        }
        return super.toString() + added;
    }
}
class CPU{
private String name;
private double clock;
CPU(String name, double clock){
    this.clock = clock;
    this.name = name;
}

    public String getName() {
        return name;
    }

    public double getClock() {
        return clock;
    }
    public int compute(int a, int b){
    return  a + b;
    }

    @Override
    public String toString() {
        return "CPU: "+ name+ " "+clock + "Ghz" ;
    }
}
class RAM{
private String type;
private int capacity;
private int[][] memory;
RAM(String type, int capacity){
    this.type = type;
    this.capacity = capacity;
    initMemory();
}

    public int getCapacity() {
        return capacity;
    }

    public String getType() {
        return type;
    }
    private void initMemory(){
    Random r = new Random();
    memory = new int[capacity][capacity];
    for (int i = 0; i < memory.length; i++){
        for (int j = 0; j < memory[0].length; j++){
            memory[i][j] = r.nextInt(11);
        }
    }
}
private boolean check(int i, int j){
    if (capacity > i && capacity > j && i >= 0 && j >= 0)
        return true;
    else
        return false;

}
public int getValue(int i, int j){
    if (!check(i,j))
        return -1;
    else
        return memory[i][j];
   }
public void setValue(int i, int j, int value){
   if (!check(i,j)){

   }else {
       memory[i][j] = value;
   }
  }

    @Override
    public String toString() {
        return "RAM: "+type+" "+capacity+"GB";
    }
}
