import java.util.Date;
public class Ex2_20220808011 {
    public static void main(String[] args) {
       
    }
}

    class Ticket {
        private City from;
        private City to;
        private Date date;
        private int seat;

        Ticket(City from, City to, Date date, int seat) {
            this.from = from;
            this.to = to;
            this.date = date;
            this.seat = seat;
        }

        Ticket(City from, City to, int seat) {
            this.from = from;
            this.to = to;
            this.seat = seat;
            Date dateToday= new Date();
            long oneDayButMili = 24 * 60 * 60 * 1000;
            this.date=new Date(dateToday.getTime() + oneDayButMili);
        }

        public City getFrom() {
            return from;
        }

        public City getTo() {
            return to;
        }

        public Date getDate() {
            return date;
        }

        public int getSeat() {
            return seat;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public void setFrom(City from) {
            this.from = from;
        }

        public void setSeat(int seat) {
            this.seat = seat;
        }

        public void setTo(City to) {
            this.to = to;
        }
    }

    class City {
    private String postalCode;
    private String name;
    City(String postalCode, String name){
        this.postalCode = postalCode;
        this.name = name;
     }

        public String getName() {
            return name;
        }

        public String getPostalCode() {
            return postalCode;
        }
    }
    class Person{
    private String name;
    private String surname;
    private String phoneNumber;
   Person(String name, String surname, String phoneNumber){
       this.phoneNumber = phoneNumber;
       this.name = name;
       this.surname = surname;
   }

        public String getName() {
            return name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getSurname() {
            return surname;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }
    }

