public class Ex3_20220808011 {

}
class Author{
    private String name;
    private String surname;
    private String mail;
    Author(String name, String surname, String mail){
        this.name = name;
        this.mail = mail;
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
class Book {
    private String isbn;
    private String title;
    private Author author;
    private double price;

    Book(String isbn, String title, Author author, double price){
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = price;
    }
    Book(String isbn, String title, Author author){
        price = 15.25;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public String getIsbn() {
        return isbn;
    }

    public Author getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
class PaperBook extends Book{
private int shippingWeight;
private boolean inStock;
    PaperBook(String isbn, String title, Author author, double price, int shippingWeight, boolean inStock) {
        super(isbn, title, author, price);
        this.inStock = inStock;
        this.shippingWeight = shippingWeight;
    }

    PaperBook(String isbn, String title, Author author, boolean inStock) {
        super(isbn, title, author);
        this.inStock = inStock;
        shippingWeight = (int)(Math.random() * 11) + 5;
    }

    public int getShippingWeight() {
        return shippingWeight;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }
    public boolean getInStock(){
        return inStock;
    }
}
class EBook extends Book{
 private String downloadUrl;
 private double sizeMb;
    EBook(String isbn, String title, Author author, double price, String downloadUrl, double sizeMb) {
        super(isbn, title, author, price);
        this.downloadUrl = downloadUrl;
        this.sizeMb = sizeMb;
    }

    EBook(String isbn, String title, Author author, String downloadUrl, double sizeMb) {
        super(isbn, title, author);
        this.downloadUrl = downloadUrl;
        this.sizeMb = sizeMb;
    }

    public String getdownloadUrl() {
        return downloadUrl;
    }

    public double getSizeMb() {
        return sizeMb;
    }
}