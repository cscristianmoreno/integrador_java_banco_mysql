package investments;

import java.sql.ResultSet;

import mysql.Query;
import randomNum.RandomNum;

public class Investments {

    private int idClient; 
    private String nameValue;
    private int invertion;

    public Investments() {

    }

    public Investments(int idClient, String nameValue, int invertion) {
        this.idClient = idClient;
        this.nameValue = nameValue;
        this.invertion = invertion;
    }

    public void createInvertion() {
        int rand = new RandomNum(1, 25).getRandom();
        int priceCotization = (int) Math.round(this.invertion * (rand / 100f));
        int numberTitles = (int) Math.round(invertion / priceCotization);
        int percentCotization = (int) Math.floor((priceCotization * 100) / this.invertion);

        new Query("INSERT INTO investments (idClient, nameValue, numberTitles, priceCotization, percentCotization) VALUES (?, ?, ?, ?, ?)")
        .setQuery(
            this.idClient,
            this.nameValue,
            numberTitles,
            priceCotization,
            percentCotization
        );
    }

    public ResultSet getInvertion(int idClient) {
        ResultSet result = new Query("SELECT * FROM investments AS inv LEFT JOIN founds AS fo ON fo.idClient = inv.idClient WHERE inv.id = ? GROUP BY inv.numberTitles").getQuery(idClient);
        return result;
    }
}