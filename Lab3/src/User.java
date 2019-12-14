import java.util.Date;

public class User {
    String first_name;
    String last_name;
    String login;
    String passwd;
    enum Sex {male, female}
    Sex sex;
    Date reg_date;

    public User(String first_name, String last_name, char s, String login, String passwd) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.login = login;
        this.passwd = passwd;
        if (s == 'm') this.sex = Sex.male;
        else if (s == 'f') this.sex = Sex.female;
        else throw new RuntimeException();
        this.reg_date = new Date();
    }
}
