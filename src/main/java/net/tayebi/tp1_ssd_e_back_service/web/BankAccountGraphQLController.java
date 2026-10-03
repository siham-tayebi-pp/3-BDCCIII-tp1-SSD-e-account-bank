package net.tayebi.tp1_ssd_e_back_service.web;

import net.tayebi.tp1_ssd_e_back_service.dtos.BankAccountDTOResponse;
import net.tayebi.tp1_ssd_e_back_service.dtos.BankAccountRequestDTO;
import net.tayebi.tp1_ssd_e_back_service.entities.BankAccount;
import net.tayebi.tp1_ssd_e_back_service.entities.Customer;
import net.tayebi.tp1_ssd_e_back_service.repositories.BankAccountRepository;
import net.tayebi.tp1_ssd_e_back_service.repositories.CustomerRepository;
import net.tayebi.tp1_ssd_e_back_service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class BankAccountGraphQLController {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CustomerRepository customerRepository;

    @QueryMapping
    public List<BankAccount> accountsList(){
        return 
                bankAccountRepository.findAll();

}
    @QueryMapping
    public BankAccount accountById(@Argument  String id){
        return   bankAccountRepository.findById(id).orElseThrow(
                ()->new RuntimeException(String.format("Bank Account %s not found",id))
        );

    }
    @MutationMapping
    public BankAccountDTOResponse addAccount(@Argument BankAccountRequestDTO bankAccount){
        return accountService.addAccount(bankAccount);
    }
    @MutationMapping
    public BankAccountDTOResponse updateAccount(@Argument String id,@Argument BankAccountRequestDTO bankAccount){
        return accountService.updateAccount(id,bankAccount);
    }
    @MutationMapping
    public Boolean deleteAccount(@Argument String id){
        bankAccountRepository.deleteById(id);
        return  true;
    }
    @QueryMapping
    public List<Customer> customers(){
        return customerRepository.findAll();
    }



}
record  BankAccountDTO(Double balance,String type, String currency){

}
