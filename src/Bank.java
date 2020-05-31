import java.util.ArrayList;
import java.util.stream.Collectors;

public class Bank
{
    //геттеры полей класса
    public String getName() {
        return name;
    }
    public ArrayList<Acccount> getAcccounts() {
        return acccounts;
    }
    public int getID() {
        return ID;
    }

    //поля класса
    private String name;
    private ArrayList<Acccount> acccounts = new ArrayList<Acccount>();
    private int ID;

    //конструктор класса
    public Bank(String name) {
        this.name = name;
        ID = 0;
    }

    //добавить аккаунт в банк
    public void addAccount(Acccount acccount){
        acccounts.add(acccount);
        ID++;
    }

    /*
    * сделать перевод с аккаунта на аккаунт
    * synchronized блокирует аккаунты from и to
    * и ждёт результата true внутри if
    * */
    public void Make_transfer(Acccount from, Acccount to, int sum){
        if(from.get_id()>to.get_id()){
            synchronized (to){
                synchronized (from){
                if (from.Take_money(sum)) to.Make_deposit(sum);
                }
            }
        }else {
            synchronized (from){
                synchronized (to){
                    if (from.Take_money(sum)) to.Make_deposit(sum);
                }
            }
        }
    }

    //считает сумму балансов всех аккаунтов в банке
    public int GetAccountBalance(){
        int balance = 0;
        for (Acccount acccount:acccounts)
            balance+=acccount.get_balance();
        return balance;
    }

    //удаляет заблокированные аккаунты
    public void DeleteBlockedUsers(){
        acccounts = (ArrayList<Acccount>)acccounts.stream().filter(x -> !x._isBlocked()).collect(Collectors.toList());
    }
}