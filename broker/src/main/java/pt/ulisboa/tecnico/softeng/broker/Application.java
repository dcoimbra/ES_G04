package pt.ulisboa.tecnico.softeng.broker;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.bank.domain.Account;
import pt.ulisboa.tecnico.softeng.bank.domain.Bank;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure;
import pt.ulisboa.tecnico.softeng.broker.domain.Broker;
import pt.ulisboa.tecnico.softeng.broker.domain.Client;
import pt.ulisboa.tecnico.softeng.tax.domain.Buyer;
import pt.ulisboa.tecnico.softeng.tax.domain.IRS;
import pt.ulisboa.tecnico.softeng.tax.domain.Seller;

public class Application {

	public static void main(String[] args) {
		System.out.println("Adventures!");
		Bank bank = new Bank("MoneyPlus", "BK01");
		Account account = new Account(bank, new pt.ulisboa.tecnico.softeng.bank.domain.Client(bank, "José dos Anzóis"));
		Account accountBroker = new Account(bank, new pt.ulisboa.tecnico.softeng.bank.domain.Client(bank, "Broker"));
		account.deposit(1000);

        String sellerAddress = "Somewhere";
        String sellerName = "José Vendido";
        String sellerNif = "123456789";

        String buyerAddress = "Narnia";
        String buyerName = "Jacinto Costa";
        String buyerNif = "987654321";

        Seller seller = new Seller(IRS.getIRS(), sellerNif, sellerName, sellerAddress);
        Buyer buyer = new Buyer(IRS.getIRS(), buyerNif, buyerName, buyerAddress);

		Broker broker = new Broker("BR01", "Fun", sellerNif, buyerNif, accountBroker.getIBAN());
		Adventure adventure = new Adventure(broker, new LocalDate(), new LocalDate(), new Client(broker, account.getIBAN(), "123456789", "IMT1234",33), 50, true);

		adventure.process();

		System.out.println("Your payment reference is " + adventure.getPaymentConfirmation() + " and you have "
				+ account.getBalance() + " euros left in your account");
	}

}
