package model;


public class Notification {
    Integer id;
    String email, message;

    public Notification(){
        id = 0;
        email = "necunoscut";
        message = "necunsocut";
    }

    public Notification(Integer i, String e, String mes){
        id = i;
        email = e;
        message = mes;
    }

    public String getEmail(){
        return email;
    }

    public String getMessage(){
        return message;
    }

    @Override
    public String toString(){
        return "Utilizatorul cu email-ul: " + email 
            + ", a primit mesajul: " + '\n' 
            + message + '\n';
    }
}
