package Bank;
import accountsBank.AccountsBank;
import clients.Clients;
import founds.Founds;
import investments.Investments;

public class Bank {
    Founds founds;
    Investments investments;
    Clients clients;
    AccountsBank accountsBank;

    public Bank(Founds founds, Investments investments, Clients clients, AccountsBank accountsBank) {
        this.founds = founds;
        this.investments = investments;
        this.clients = clients;
        this.accountsBank = accountsBank;
    }

    public Founds getFounds() {
        return this.founds;
    }

    public Investments getInvestments() {
        return this.investments;
    }

    public Clients getClients() {
        return this.clients;
    }

    public AccountsBank getAccountsBank() {
        return this.accountsBank;
    }
}
