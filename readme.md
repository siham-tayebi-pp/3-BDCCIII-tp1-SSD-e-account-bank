
# Activité Pratique N°1 - Implémentation d'un micro service avec spring boot
![1.png](images/1.png)

pis on a cree entities et rpositories pacjages
 et apres bank account clase et accoutn type
```java
package net.tayebi.tp1_ssd_e_back_service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccount {
    @Id
    private  String id;
    private Date createdAt;
    private double balance;
    private String currency;
    private  AccountType type;

}


```


```java
package net.tayebi.tp1_ssd_e_back_service.entities;

public enum AccountType {
    CURRENT_ACCOUNT, SAVINGS_ACCOUNT
}

```
 puis repositories 

package net.tayebi.tp1_ssd_e_back_service.repositories;

import net.tayebi.tp1_ssd_e_back_service.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

}


puis notr ebean dans le programme main pour quil sexecute un fois lapp demare
auto 



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


et dna sfich config en pase a fair
la on va speciifer url port active h2
spring.application.name=tp1_ssd_e_back_service
spring.datasource.url=jdbc:h2:mem:account-db
spring.h2.console.enabled=true
server.port=8080



puis on execute 



pour graalvm 
une fois jar genrer on le copie il devient excutable native pour que demarage soit tres rapide