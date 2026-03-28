public class Guest extends Person{
    private String phoneNumber;

    Guest(String name, int id, String phoneNumber){
         super(name, id);
         this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void printRole(){
          System.out.println("Guest");
    }
}
