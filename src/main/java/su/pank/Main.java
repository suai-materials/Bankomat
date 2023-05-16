package su.pank;

import su.pank.models.Invoice;
import su.pank.models.MyBank;
import su.pank.models.User;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static MyBank bank;
    private static Scanner scanner;

    private static HashMap<String, User> users = new HashMap<>();

    private static User userNow;

    public static void main(String[] args) {
        bank = new MyBank();
        scanner = new Scanner(System.in);

        printWelcomeMessage();
        login();
        showMainMenu();
        main(args);
    }

    private static void showMainMenu() {
        System.out.println("-------------------------");
        System.out.println("Выберите действие:");
        System.out.println("1. Проверить баланс");
        System.out.println("2. Пополнить счет");
        System.out.println("3. Снять со счета");
        System.out.println("4. Перевести деньги");
        System.out.println("0. Выйти");
        System.out.println("-------------------------");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                showBalance();
                break;
            case 2:
                depositMoney();
                break;
            case 3:
                withdrawMoney();
                break;
            case 4:
                transferMoney();
                break;
            case 0:
                exit();
                return;
            default:
                System.out.println("Неверный выбор");
                break;
        }
        showMainMenu();
    }

    private static void exit() {
        System.out.println("Были рады видеть вас в моём банке.");
    }

    private static void depositMoney() {
        try {
            System.out.println("Введите сумму для пополнения: ");
            int money = scanner.nextInt();
            scanner.nextLine();
            boolean success = bank.addMoney(userNow.invoice, money);
            if (success) {
                System.out.println("Счет успешно пополнен");
            } else {
                System.out.println("Неверная сумма для пополнения");
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void withdrawMoney() {
        try {
            System.out.println("Введите сумму для снятия: ");
            int money = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера после считывания числа
            boolean success = bank.getMoney(userNow.invoice, money);
            if (success) {
                System.out.println("Снятие прошло успешно");
            } else {
                System.out.println("Недостаточно средств или неверная сумма для снятия");
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void transferMoney() {
        try {
            int index = 0;
            for (var user :
                    users.keySet()) {
                System.out.println(index++ + ". " + user);
            }
            System.out.println("Введите имя получателя: ");
            String receiverName = scanner.nextLine();
            User receiver = users.get(receiverName);

            if (receiver == null) {
                System.out.println("Пользователь с таким именем не найден");
                showMainMenu();
                return;
            }
            System.out.println("Введите сумму для перевода: ");
            int money = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера после считывания числа
            boolean success = bank.sendMoney(userNow.invoice, money, receiver.invoice);
            if (success) {
                System.out.println("Перевод выполнен успешно");
            } else {
                System.out.println("Недостаточно средств или неверная сумма для перевода");
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        showMainMenu();
    }

    private static void showBalance() {
        try {
            int balance = bank.getBalance(userNow.invoice);
            System.out.println("Баланс: " + balance);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void printWelcomeMessage() {
        System.out.println("Добро пожаловать в мой банкомат: " + bank.bankName);
    }

    private static void login() {
        if (users.isEmpty()) {
            System.out.println("Регистрация нового пользователя...");
            System.out.println("Введите имя пользователя:");
            String name = scanner.nextLine();
            regNewUser(name);
        }
        System.out.println("Вы можете выбрать пользователя введя его имя и pin-code:");
        int index = 0;
        for (var user :
                users.keySet()) {
            System.out.println(index++ + ". " + user);
        }
        System.out.println("Введите имя пользователя:");
        String name = scanner.nextLine();
        if (users.containsKey(name)) {
            System.out.println("Авторизация пользователя " + name + " ...");
            System.out.println("Введите pin-code");
            StringBuilder pin = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                pin.append(scanner.next(Pattern.compile("\\d").pattern()));
            }
            if (users.get(name).checkPin(pin.toString())) {
                userNow = users.get(name);
                return;
            }
            System.out.println("Pin-code неверный");
            login();
            return;
        }
        System.out.println("Регистрация нового пользователя...");
        regNewUser(name);
    }

    private static void regNewUser(String name) {

        System.out.println("Введите pin-code");
        StringBuilder pin = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            pin.append(scanner.next(Pattern.compile("\\d").pattern()));
        }
        scanner.nextLine();
        userNow = new User(name, pin.toString());
        users.put(name, userNow);
        System.out.println("Создаём счёт");
        System.out.println("Введите имя счёта:");
        String invoiceName = scanner.nextLine();
        createInvoice(invoiceName);
    }

    private static void createInvoice(String invoiceName) {

        System.out.println("Вы хотите открыть счёт в нашем банке?(y/n)");
        char choice = scanner.nextLine().charAt(0);
        switch (choice) {
            case 'y':
                userNow.invoice = new Invoice(userNow, bank.bankName, invoiceName);
                break;
            case 'n':
                userNow.invoice = new Invoice(userNow, "Другой банк", invoiceName);
                break;
            default:
                System.out.println("Введите корректное значение!");
                createInvoice(invoiceName);
                break;
        }

    }
}