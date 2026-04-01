public class Manager extends Employee{
    private double bonus;

    // constructor
    public Manager(String name, int id, double salary, double bonus){
        super(name, id, salary);
        this.bonus = bonus;
    }
    
    // getter
    public double getBonus(){
        return bonus;
    }

    // prints the role
    public void printRole() {
        System.out.println("Manager");
    }
}
