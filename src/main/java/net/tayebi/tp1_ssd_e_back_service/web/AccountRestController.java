package net.tayebi.tp1_ssd_e_back_service.web;


import lombok.AllArgsConstructor;
import net.tayebi.tp1_ssd_e_back_service.entities.BankAccount;
import net.tayebi.tp1_ssd_e_back_service.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
public class AccountRestController {
//    @Autowired
    private BankAccountRepository bankAccountRepository;
    @GetMapping("/bankAccounts")
    public List<BankAccount> bankAccounts() {
        return bankAccountRepository.findAll();
    }

    @GetMapping("/bankAccounts/{id}")
    public BankAccount bankAccount(@PathVariable String id) {
        return  bankAccountRepository.findById(id).orElseThrow(
                ()->new RuntimeException(String.format("Bank account with id %s not found", id))
        );
    }

    @DeleteMapping("/bankAccounts/{id}")
    public void deleteAccount(@PathVariable String id) {
        bankAccountRepository.deleteById(id);
    }

    @PostMapping("/bankAccountss")
    public BankAccount save(@RequestBody  BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }
    @PutMapping("/bankAccounts/update/{id}")
    public BankAccount update(@RequestBody  BankAccount bankAccount, @PathVariable String id) {
        BankAccount account = bankAccountRepository.findById(bankAccount.getId()).orElseThrow(null);
        if (account.getBalance()!=null) account.setBalance(bankAccount.getBalance());
        if(account.getCurrency() !=null) account.setCurrency(bankAccount.getCurrency());
        if(account.getType() !=null) account.setType(bankAccount.getType());
        if(account.getCreatedAt() !=null) account.setCreatedAt(new Date());
        return bankAccountRepository.save(account);
    }


}
