package su.pank.models;

public class User {
    public String name;
    public String lastName;

    public Integer id;
    public Invoice invoice;

    private String _pin;

    public User(String username, String pin) {
        name = username;
        _pin = pin;
    }

    public boolean checkPin(String pin){
        return pin.equals(_pin);
    }
}
