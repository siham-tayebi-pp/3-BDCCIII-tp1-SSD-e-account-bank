package net.tayebi.tp1_ssd_e_back_service.web;


import lombok.AllArgsConstructor;
import net.tayebi.tp1_ssd_e_back_service.entities.BankAccount;
import net.tayebi.tp1_ssd_e_back_service.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    @PostMapping("/bankAccounts")
    public BankAccount save(@RequestBody  BankAccount bankAccount) {

        if(bankAccount.getId()==null) bankAccount.setId(UUID.randomUUID().toString());
        if(bankAccount.getCreatedAt()==null) bankAccount.setCreatedAt(new Date());
        return bankAccountRepository.save(bankAccount);
    }
//    @PatchMapping("/bankAccounts/{id}")
//    public BankAccount update(@PathVariable String id, @RequestBody BankAccount bankAccount) {
//        BankAccount account = bankAccountRepository.findById(id).orElseThrow();
//
//        // Vérifier les champs de bankAccount (la requête JSON)
//        if (bankAccount.getBalance() != null) account.setBalance(bankAccount.getBalance());
//        if (bankAccount.getCurrency() != null) account.setCurrency(bankAccount.getCurrency());
//        if (bankAccount.getType() != null) account.setType(bankAccount.getType());
//
//        return bankAccountRepository.save(account);
//    }
@PutMapping("/bankAccounts/{id}")
public BankAccount update(@PathVariable String id, @RequestBody BankAccount bankAccount) {
    // 1. Charger l'entité existante depuis la BDD (état 'Managed' par JPA)
    BankAccount account = bankAccountRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(String.format("Account %s not found", id)));

    // 2. Modifier UNIQUEMENT si la valeur reçue dans la requête n'est pas null
    if (bankAccount.getBalance() != null) {
        account.setBalance(bankAccount.getBalance());
    }
    if (bankAccount.getCurrency() != null) {
        account.setCurrency(bankAccount.getCurrency());
    }
    if (bankAccount.getType() != null) {
        account.setType(bankAccount.getType());
    }

    // 3. Sauvegarder
    return bankAccountRepository.save(account);
}


}
