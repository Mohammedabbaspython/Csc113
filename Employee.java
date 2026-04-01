public class Employee extends Person {
   private double salary;
   // constructor
   public Employee(String name, int id, double salary){
             super(name, id);
             this.salary=salary;
   }
   
   // getter
   public double getSalary() {
       return salary;
   }
    
  // prints the role
  public void printRole(){
    System.out.println("Employee");
  }
}