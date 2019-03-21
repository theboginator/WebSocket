public class Contact {
    public String name;
    public String number;

    public Contact (String name, String number){
        this.name = name;
        this.number = number;
    }
    public void addEntry(String name, String number){
        this.name = name;
        this.number = number;
    }

    public String getName(){
        return this.name;
    }

    public String getNumber(){
        return this.number;
    }

}
