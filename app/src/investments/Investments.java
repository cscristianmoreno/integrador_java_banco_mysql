package investments;

import java.sql.ResultSet;

import mysql.Query;
import randomNum.RandomNum;

public class Investments {
    private String nameValue;
    private int invertion;

    public Investments() {

    }

    public Investments(String nameValue, int invertion) {
        this.nameValue = nameValue;
        this.invertion = invertion;
    }

    public void createInvertion() {
        int priceCotization = new RandomNum(100, 2000).getRandom();
        int numberTitles = (int) Math.round(invertion / priceCotization);

        new Query("INSERT INTO investments (nameValue, numberTitles, priceCotization) VALUES (?, ?, ?)")
        .setQuery(
            this.nameValue,
            numberTitles,
            priceCotization
        );
    }

    public ResultSet getInvertion(int idClient) {
        ResultSet result = new Query("SELECT * FROM investments WHERE idClient = ?").getQuery(idClient);
        return result;
    }
}