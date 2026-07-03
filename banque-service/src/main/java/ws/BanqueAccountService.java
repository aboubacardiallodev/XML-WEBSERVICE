package ws;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebService(name = "BankWS")
public class BanqueAccountService {

    @WebMethod(operationName = "ConversionEuroToDh")
    public double convertToDh(@WebParam(name = "montant") double amount) {
        return amount * 11;
    }

    @WebMethod
    public Account getAccount(@WebParam(name = "code") int code) {
        return new Account(code, 15000.50, new Date());
    }

    @WebMethod
    public List<Account> getListAccounts() {
        List<Account> listAccount = new ArrayList<>();
        listAccount.add(new Account(1, 15000.50, new Date()));
        listAccount.add(new Account(2, 23000.00, new Date()));
        listAccount.add(new Account(3, 500.75, new Date()));
        return listAccount;
    }
}
