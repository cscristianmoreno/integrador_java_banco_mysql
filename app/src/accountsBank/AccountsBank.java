package accountsBank;

import java.sql.ResultSet;
import java.util.ArrayList;

import mysql.Query;
import randomNum.RandomNum;

public class AccountsBank {
    private int idClient;
    private int accountType;
    
    public AccountsBank() {

    }
    
    public AccountsBank(int idClient, int accountType/*, String accountNumber, String cbu, int lastDigit, int money*/) {
        this.idClient = idClient;
        this.accountType = accountType;
    }

    public Integer createAccount() {
        ArrayList<String> accountNumber = new ArrayList<String>();

        int rand;
        int randMoney = new RandomNum(50000, 1000000).getRandom();
        int lastDigit = new RandomNum(100, 999).getRandom();
        String cbu = "0" + Integer.toString(idClient);
        int i;

        for (i = 0; i < 4; i++) {
            rand = new RandomNum(1001, 9999).getRandom();
            accountNumber.add(Integer.toString(rand));  
        }
        
        for (i = cbu.length(); i < 22; i++) {

            rand = new RandomNum(1, 9).getRandom();
            cbu += Integer.toString(rand);   
        }

        String number = accountNumber.get(0) + " " + accountNumber.get(1) + " " + accountNumber.get(2) + " " + accountNumber.get(3); 

        Integer result = new Query("INSERT INTO accountsBank (idClient, accountTypeSaving, accountNumber, cbu, lastDigit, money) VALUES (?, ?, ?, ?, ?, ?)").setQuery(
            this.idClient,
            this.accountType,
            number,
            cbu,
            lastDigit,
            randMoney
        );
        
        System.out.println();
        System.out.println();
        
        System.out.println("CUENTA BANCARIA GENERADA");
        System.out.printf("\n* Número de cuenta: %s (%d)\n* CBU: %s ", number, lastDigit, cbu);

        return result;
    }

    public ResultSet getAllAccounts() {
        ResultSet result = new Query("SELECT * FROM accountsbank AS acc LEFT JOIN clients AS cl ON cl.id = acc.idClient").getQuery();
        return result;
    }

    public Integer setAccountBankUpdateData(int operationType, int money, int moneyIncoming, int moneyRetired, int rest, int idClient) {
        if ((rest > money) && operationType == 1) {
            System.out.println();
            System.out.println("* El valor introducido supera tu límite de dinero");
            return null;
        }

        Integer result = new Query("UPDATE accountsBank SET money = ?, moneyIncoming = ?, moneyRetired = ? WHERE idClient = ?").setQuery(
            money,
            moneyIncoming,
            moneyRetired,
            idClient
        );

        return result;
    }

    public void deleteAccount(int idClient) {
        new Query("DELETE FROM accountsbank WHERE idClient = ?").setQuery(idClient);
    }
}
