public abstract class Person {
    protected String name;
    protected int id;

    // abstract class to print the role
    public abstract void printRole();

    // constructor
    public Person(String name, int id){
        this.name=name;
        this.id = id;
    }
    // getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    //overriding the default to string method
    public String toString() {
        return "Name: " + name + " ID: " + id;
    }
}
