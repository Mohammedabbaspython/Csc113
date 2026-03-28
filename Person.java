public abstract class Person {
    protected String name;
    protected int id;

    public abstract void printRole();

    Person(String name, int id){
        this.name=name;
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
