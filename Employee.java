public class Employee extends Person {
   private double salary;
   
   Employee(String name, int id, double salary){
             super(name, id);
             this.salary=salary;
   }
   
   public double getSalary() {
       return salary;
   }
    
  public void printRole(){
    System.out.print("Employee");
  }
}