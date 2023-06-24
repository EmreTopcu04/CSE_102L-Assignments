import java.util.ArrayList;

public class Ex6_20220808011 {
    public static void main(String[] args) {

    }
}

abstract class Product implements Comparable <Product> {
    private String name;
    private double price;

    Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int compareTo(Product product) {
        if (this.price < product.price) {
            return -1;
        } else if (this.price > product.price) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return getClass().getName() + "[name= " + name + ", " + "price= " + price + "]";
    }
}

abstract class Book extends Product {

    private String author;
    private int pageCount;

    Book(String name, double price, String author, int pageCount) {
        super(name, price);
        this.author = author;
        this.pageCount = pageCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public String getAuthor() {
        return author;
    }

}

class ReadingBook extends Book {
    private String genre;

    ReadingBook(String name, double price, String author, int pageCount, String genre) {
        super(name, price, author, pageCount);
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }
}

class ColoringBook extends Book implements Colorable {
    private String color;

    ColoringBook(String name, double price, String author, int pageCount) {
        super(name, price, author, pageCount);
    }

    public String getColor() {
        return color;
    }

    @Override
    public void paint(String color) {
        this.color = color;
    }
}

class ToyHorse extends Product implements Rideable {
    ToyHorse(String name, double price) {
        super(name, price);
    }

    @Override
    public void ride() {

    }
}

class Bicycle extends Product implements Colorable, Rideable {

    private String color;

    Bicycle(String name, double price) {
        super(name, price);
    }

    public String getColor() {
        return color;
    }

    @Override
    public void paint(String color) {
        this.color = color;
    }

    @Override
    public void ride() {

    }
}

class User {
    private String username;
    private String email;
    private PaymentMethod payment;
    private ArrayList<Product> cart = new ArrayList<Product>();

    User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Product getProduct(int index) {
        return cart.get(index);
    }

    public void removeProduct(int index) {
        cart.remove(index);
    }

    public void addProduct(Product product) {
        cart.add(product);
    }

    public void setPayment(PaymentMethod payment) {
        this.payment = payment;
    }

    public void purchase() {
        double totalPrice = 0;
        for (int i = 0; i < cart.size(); i++) {
            totalPrice += cart.get(i).getPrice();
        }
        if (payment.pay(totalPrice)) {
            cart.clear();
        }
    }
}

class CreditCard implements PaymentMethod {
    private long cardNumber;
    private String cardHolderName;
    private java.util.Date expirationDate;
    private int cvv;

    CreditCard(long cardNumber, String cardHolderName, java.util.Date expirationDate, int cvv) {
        this.cvv = cvv;
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean pay(double amount) {
        return true;
    }
}

class Paypal implements PaymentMethod {
    private String username;
    private String password;

    Paypal(String username, String password) {
        this.username = username;
        /*I used Caesar encryption technique, It should shift the letters.
        But in this case I thought since a password can include special characters
        as well. I extended it. It is still a known encryption system.
        */
        String encryptedPassword = "";
        for (int i = 0; i < password.length(); i++) {
            char passwordChar = password.charAt(i);
            if (Character.isLetter(passwordChar)) {
                passwordChar = (char) (passwordChar + 3);
                if (Character.isLowerCase(password.charAt(i)) && passwordChar > 'z') {
                    passwordChar = (char) (passwordChar - 26);
                } else if (Character.isUpperCase(password.charAt(i)) && passwordChar > 'Z') {
                    passwordChar = (char) (passwordChar - 26);
                } else if (Character.isLowerCase(password.charAt(i)) && passwordChar > 'y') {
                    passwordChar = (char) (passwordChar - 26);
                } else if (Character.isUpperCase(password.charAt(i)) && passwordChar > 'Y') {
                    passwordChar = (char) (passwordChar - 26);
                } else if (Character.isLowerCase(password.charAt(i)) && passwordChar > 'x') {
                    passwordChar = (char) (passwordChar - 26);
                } else if (Character.isUpperCase(password.charAt(i)) && passwordChar > 'X') {
                    passwordChar = (char) (passwordChar - 26);
                }
            } else {
                if (passwordChar >= '!' && passwordChar <= '=') {
                    passwordChar = (char) (passwordChar + 3);
                } else {
                    if (passwordChar == '>') {
                        passwordChar = (char) (33);
                    } else if (passwordChar == '?') {
                        passwordChar = (char) (34);
                    } else if (passwordChar == '@') {
                        passwordChar = (char) (35);
                    }
                }
            }
            encryptedPassword += passwordChar;
        }
        this.password = encryptedPassword;
    }

    @Override
    public boolean pay(double amount) {
        return false;
    }
}

interface Colorable {
    void paint(String color);
}

interface Rideable {
    void ride();
}

interface PaymentMethod {

    boolean pay(double amount);
}

