package su.pank.models;

public interface ATM {

    /**
     * Получить баланс
     *
     * @param invoice счёт пользователя
     * @return количество денег на балансе
     */
    Integer getBalance(Invoice invoice) throws Exception;

    /**
     * Пополнить счёт
     *
     * @param invoice счёт пользователя
     * @param money   количество добовляемых денег
     * @return успешно ли прошла операция
     */
    boolean addMoney(Invoice invoice, Integer money) throws Exception;

    /**
     * Снять деньги со счета
     *
     * @param invoice счёт пользователя
     * @param money   количество снимаемых денег
     * @return успешно ли прошла операция
     */
    boolean getMoney(Invoice invoice, Integer money) throws Exception;


    /**
     * Перевести деньги с одного счета на другой
     *
     * @param invoiceSender счёт отправителя
     * @param money         количество отправляемых денег
     * @param invoiceUser   счёт получателя
     * @return успешно ли прошла операция
     */
    boolean sendMoney(Invoice invoiceSender, Integer money, Invoice invoiceUser) throws Exception;
}
