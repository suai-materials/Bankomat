package su.pank.models;

public class Invoice {
    String name;

    User user;

    String bankName = "";
    public Integer balance = 0;

    public Invoice(User receiver, String bankName, String name) {
        user = receiver;
        this.bankName = bankName;
    }
}
