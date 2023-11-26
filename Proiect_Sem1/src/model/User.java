package model;

public class User {
    private Integer id;
    private String username, email, password;
    double balance;

    /**
     * Constructor implicit
     */
    public User(){
        id = 0;
        username = "necunoscut";
        email = "necunoscut";
        password = "nesetat";
        balance = (double) 0;
    }

    /**
     * Constructorul de copiere
     * @param id id-ul unic al utilizatorului
     * @param username 
     * @param email
     * @param password
     */
    public User(Integer id, String username, String email, String password){
        this.id =id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

     public User(Integer id, String username, String email, String password, double b){
        this.id =id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.balance = b;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public void setUsername(String name){
        this.username = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String pas){
        this.password = pas;
    }

    public void setBalance(Double b){
        balance = b;
    }


    @Override
    public String toString(){
        return "Utilizatorul: " + username + ", are id ul =" + id + '\n';
    }

    public  String getUsername() {
        return username;
    }

    public  String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Integer getId() {
        return id;
    }

    public Double getBalance(){
        return balance;
    }
}
