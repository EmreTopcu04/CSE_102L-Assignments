public class Ex8_20220808011 {
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
}

interface Wrappable extends Sellable {
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
    public String toString() {
        return super.toString() + " {" + item + "}";
    }
}

class Box<T extends Sellable> implements Package<T> {
    private T item;
    private boolean seal;

    Box() {
        item = null;
        seal = false;
    }

    Box(T item) {
        this.item = item;
        seal = true;

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
    public String toString() {
        return getClass().getSimpleName() + " {" + item + "} Seal: " + seal;
    }
}