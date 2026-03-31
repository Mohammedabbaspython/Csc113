public abstract class Person {
    protected String name;
    protected int id;

    public abstract void printRole();

    public Person(String name, int id){
        this.name=name;
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "name: " + name + "id: " + id;
    }
}
