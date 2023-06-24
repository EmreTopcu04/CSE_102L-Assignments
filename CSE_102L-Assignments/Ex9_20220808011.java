import java.lang.reflect.Array;
import java.util.Arrays;

public class Ex9_20220808011 {
    public static void main(String[] args) {

    }
}

interface Sellable {
    String getName();

    double getPrice();
}

interface Package<T> {
    T extract();

    boolean pack(T item);

    boolean isEmpty();

    double getPriority();
}

interface Wrappable extends Sellable {
}

interface Common<T> {
    boolean isEmpty();

    T peek();

    int size();
}

interface Stack<T> extends Common<T> {
    boolean push(T item);

    T pop();
}

interface Node<T> {
    final int DEFAULT_CAPACITY = 2;

    void setNext(T item);

    T getNext();

    double getPriority();
}

interface PriorityQueue<T> extends Common<T> {
    final int FLEET_CAPACITY = 3;

    boolean enqueue(T item);

    T dequeue();
}


abstract class Product implements Sellable {
    private String name;
    private double price;

    Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " (" + name + ", " + price + ")";
    }
}

class Mirror extends Product {
    private int width;
    private int height;

    Mirror(int width, int height) {
        super("mirror", 2);
        this.width = width;
        this.height = height;
    }

    public int getArea() {
        return width * height;
    }

    public <T> T reflect(T item) {
        System.out.println(item);
        return item;
    }
}

class Paper extends Product implements Wrappable {
    private String note;

    Paper(String name, double price, String note) {
        super("A4", 3);
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

class Matroschka<T extends Wrappable> extends Product implements Wrappable, Package<T> {
    private T item;

    Matroschka(T item) {
        super("Doll", 5 + item.getPrice());
        this.item = item;
    }

    @Override
    public T extract() {
        if (isEmpty())
            return null;

        T temp = item;
        item = null;
        return temp;
    }

    @Override
    public boolean pack(T item) {
        if (isEmpty()) {
            this.item = item;
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return item == null;
    }

    @Override
    public double getPriority() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public String toString() {
        return super.toString() + " {" + item + "}";
    }
}

class Box<T extends Sellable> implements Package<T> {
    private T item;
    private boolean seal;
    private int distanceToAddress;


    Box() {
        item = null;
        seal = false;
    }

    Box(T item, int distanceToAddress) {
        this.item = item;
        seal = true;
        this.distanceToAddress = distanceToAddress;

    }

    @Override
    public T extract() {
        if (isEmpty()) {
            seal = false;
            return null;
        }
        T temp = item;
        item = null;
        seal = false;
        return temp;
    }

    @Override
    public boolean pack(T item) {
        if (isEmpty()) {
            this.item = item;
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return item == null;
    }

    @Override
    public double getPriority() {
        return distanceToAddress / item.getPrice();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {" + item + "} Seal: " + seal;
    }
}

class Container implements Stack<Box>, Node<Container>, Comparable<Container> {
    private Box[] boxes;
    private int top;
    private int size;
    private double priority;
    private Container next;

    Container() {
        this.top = -1;
        this.next = null;
        this.priority = 0;
        this.boxes = new Box[DEFAULT_CAPACITY];
    }

    @Override
    public String toString() {
        return "Container with priority: " + priority;
    }

    @Override
    public boolean isEmpty() {
        int counter = 0;
        for (Box box : boxes) {
            if (box == null) {
                counter++;
            }
        }
        return counter == boxes.length;
    }

    @Override
    public Box peek() {
        return boxes[top];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean push(Box item) {
        if (top == boxes.length - 1) {
            return false;
        }
        int temp = top + 1;
        boxes[temp] = item;
        top++;
        size++;
        return true;
    }

    @Override
    public Box pop() {
        if (isEmpty()) {
            return null;
        }
        Box popped = boxes[top--];
        size--;
        return popped;
    }

    @Override
    public void setNext(Container item) {
        this.next = item;
    }

    @Override
    public Container getNext() {
        return next;
    }

    @Override
    public double getPriority() {
        return priority;
    }

    @Override
    public int compareTo(Container compare) {
        return Double.compare(priority, compare.getPriority());
    }
}

class CargoFleet implements PriorityQueue<Container> {
    private Container head;
    private int size;

    CargoFleet() {
        head = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public Container peek() {
        if (size == 0) {
            return null;
        } else {
            return this.head;
        }

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean enqueue(Container item) {
        if (size == 0) {
            size++;
            head = item;
            return true;
        } else if (size == 1) {
            if (item.getPriority() > head.getPriority()) {
                head.setNext(item);
                size++;
                return true;
            } else {
                item.setNext(head);
                head = item;
                size++;
                return true;
            }
        } else if (size == 2) {
            Container first = head;
            Container second = head.getNext();
            Container third = item;
            if (second.getPriority() > third.getPriority()) {
                Container temp = second;
                second = third;
                third = temp;
                if (first.getPriority() > second.getPriority()) {
                    Container temp_2 = first;
                    first = second;
                    second = temp_2;
                }
            }
            head = first;
            head.setNext(second);
            head.getNext().setNext(third);
            size++;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Container dequeue() {
        if (size == 0) {
            return null;
        }
        Container temp = head;
        head = head.getNext();
        size--;
        return temp;
    }
}

class CargoCompany {
    private Container stack;
    private CargoFleet queue;

    CargoCompany() {
        this.stack = new Container();
        this.queue = new CargoFleet();
    }

    public <T extends Box<?>> void add(T box) {
        if (stack.push(box)) {
        } else {
            if (queue.enqueue(stack)) {
                stack = new Container();
                add(box);
            } else {
                ship(queue);
                add(box);
            }
        }
    }

    private void ship(CargoFleet fleet) {
        while (!fleet.isEmpty()) {
            empty(fleet.dequeue());
        }
    }

    private void empty(Container container) {
        while (!container.isEmpty()) {
            System.out.println(deliver(container.pop()));
        }
    }

    private <T extends Box<?>> Sellable deliver(Box box) {
        return box.extract();
    }
}