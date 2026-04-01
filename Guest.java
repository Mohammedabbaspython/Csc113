public class Guest extends Person{
    private String phoneNumber;

    // constructor
    public Guest(String name, int id, String phoneNumber){
         super(name, id);
         this.phoneNumber = phoneNumber;
    }

    // getter
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // prints the role
    public void printRole(){
          System.out.println("Guest");
    }
}
