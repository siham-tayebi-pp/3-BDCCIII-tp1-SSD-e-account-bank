package net.tayebi.tp1_ssd_e_back_service;

import net.tayebi.tp1_ssd_e_back_service.entities.AccountType;
import net.tayebi.tp1_ssd_e_back_service.entities.BankAccount;
import net.tayebi.tp1_ssd_e_back_service.repositories.BankAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;

@SpringBootApplication
public class Tp1SsdEBackServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Tp1SsdEBackServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner start( BankAccountRepository bankAccountRepository) {
        return args -> {
          for(int i=1; i<=10; i++) {
             BankAccount bankAccount =  BankAccount.builder()
                     .id(UUID.randomUUID().toString())
                     .type(Math.random()>0.5? AccountType.CURRENT_ACCOUNT:AccountType.SAVINGS_ACCOUNT)
                     .balance(10000+Math.random()*90000)
                     .createdAt(new Date())
                     .currency("MAD")
                     .build();
             bankAccountRepository.save(bankAccount);
          }
        };
    }

}
