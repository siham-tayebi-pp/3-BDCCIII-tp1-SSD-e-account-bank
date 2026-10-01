package net.tayebi.tp1_ssd_e_back_service.web;


import lombok.AllArgsConstructor;
import net.tayebi.tp1_ssd_e_back_service.dtos.BankAccountDTOResponse;
import net.tayebi.tp1_ssd_e_back_service.dtos.BankAccountRequestDTO;
import net.tayebi.tp1_ssd_e_back_service.entities.BankAccount;
import net.tayebi.tp1_ssd_e_back_service.mappers.AccountMapper;
import net.tayebi.tp1_ssd_e_back_service.repositories.BankAccountRepository;
import net.tayebi.tp1_ssd_e_back_service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class AccountRestController {
//    @Autowired
    private BankAccountRepository bankAccountRepository;
    private AccountService accountService;
    private AccountMapper accountMapper;
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
    public BankAccountDTOResponse save(@RequestBody BankAccountRequestDTO bankAccount) {

        return accountService.addAccount(bankAccount);
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
