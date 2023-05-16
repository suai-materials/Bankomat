package su.pank.models;

import java.util.Scanner;
import java.util.regex.Pattern;

public class MyBank implements ATM {
    public String bankName = "Банк Васи Панкова";

    public boolean checkPin(User user) {
        System.out.println("Введите ваш pin-code:");
        Scanner scan = new Scanner(System.in);
        StringBuilder pin = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            pin.append(scan.next(Pattern.compile("\\d").pattern()));
        }
        return user.checkPin(pin.toString());
    }

    @Override
    public Integer getBalance(Invoice invoice) throws Exception {
        if (!checkPin(invoice.user)) throw new Exception("Incorrect pin");
        return invoice.balance;
    }

    @Override
    public boolean addMoney(Invoice invoice, Integer money) throws Exception {
        if (!checkPin(invoice.user)) throw new Exception("Incorrect pin");
        if (money <= 0) return false;
        invoice.balance += money;
        return true;
    }

    @Override
    public boolean getMoney(Invoice invoice, Integer money) throws Exception {
        if (!checkPin(invoice.user)) throw new Exception("Incorrect pin");
        if (money <= 0 || money > invoice.balance) return false;
        if (!invoice.bankName.equals(bankName)) {
            if (money * 1.02 > invoice.balance) return false;
            invoice.balance -= (int) (money * 1.02);

            return true;
        }
        invoice.balance -= money;
        return true;
    }

    @Override
    public boolean sendMoney(Invoice invoiceSender, Integer money, Invoice invoiceUser) throws Exception {
        if (!checkPin(invoiceSender.user)) throw new Exception("Incorrect pin");
        if (money <= 0 || money > invoiceSender.balance) return false;
        invoiceSender.balance -= money;
        invoiceUser.balance += money;
        return true;
    }
}
