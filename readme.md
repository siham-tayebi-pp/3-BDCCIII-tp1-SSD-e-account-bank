
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
import net.tayebi.tp1_ssd_e_back_service.enums.AccountType;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccount {
 @Id
 private String id;
 private Date createdAt;
 private double balance;
 private String currency;
 private AccountType type;

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

import net.tayebi.tp1_ssd_e_back_service.enums.AccountType;
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

 pusi on entre au url http://localhost:8080/h2-console
![2.png](images/2.png)


et voila on est netre a notre bd 
![3.png](images/3.png)
la on voi le type en o et 1 cad on doit le cnhager @enumerated pur que ca se sotjce en dt 
![4.png](images/4.png)


package net.tayebi.tp1_ssd_e_back_service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Enumerated(EnumType.STRING)
private  AccountType type;

}

et voila type est en sr 
![5.png](images/5.png)

on cree pakcage ou directory web avec   account rest conroller


package net.tayebi.tp1_ssd_e_back_service.web;


import lombok.AllArgsConstructor;
import net.tayebi.tp1_ssd_e_back_service.entities.BankAccount;
import net.tayebi.tp1_ssd_e_back_service.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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


}


on teste dans le site
http://localhost:8080/bankAccounts
![6.png](images/6.png)

on tes avec id 
 ![7.png](images/7.png)
put modifier tt attribue
path modifier qu attribut nvoye dans requette

@DeleteMapping("/bankAccounts/{id}")
public void deleteAccount(@PathVariable String id) {
bankAccountRepository.deleteById(id);
}

    @PostMapping("/bankAccounts")
    public BankAccount save(@RequestBody  BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }
    @PutMapping("/bankAccounts/{id}")
    public BankAccount update(@RequestBody  BankAccount bankAccount, @PathVariable String id) {
        BankAccount account = bankAccountRepository.findById(bankAccount.getId()).orElseThrow(null);
        if (account.getBalance()!=null) account.setBalance(bankAccount.getBalance());
        if(account.getCurrency() !=null) account.setCurrency(bankAccount.getCurrency());
        if(account.getType() !=null) account.setType(bankAccount.getType());
        if(account.getCreatedAt() !=null) account.setCreatedAt(new Date());
        return bankAccountRepository.save(account);
    } on a joute ces mth on apsse au test avec postman

on test la route post et   pour save ccomem pos donc headers tq conttnent type  et value quil est app json e dna sbody on met le sodnnes a envoyer
![8.png](images/8.png)
on ajoute c apour que lid se genrre auto
if(bankAccount.getId()==null) bankAccount.setId(UUID.randomUUID().toString());

comme ca voial c bank account bein ajout
![9.png](images/9.png)

on essaye de update ce bank acocunt via par exple eid de lelt crer 

e route put http://localhost:8080/bankAccounts/bd455e9b-dbf7-4ace-9ee0-eaf473656ada
test with patch ![10.png](images/10.png)
test with put
![11.png](images/11.png)


 pour  ajouter documemnt swaager on integre la depenndnace
 spring boot openapi doc maven en pom.xml
<!-- Source: https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-ui -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.6.0</version>
</dependency>

et on ouvre ce lien ss brwose 

![12.png](images/12.png)
la on notre api docs qui contneitn notre doc de notre sweb service 
![13.png](images/13.png)
ge route est  GET
/bankAccounts/{id}
![14.png](images/14.png)
PUT
/bankAccounts/{id}
![15.png](images/15.png)

DELETE
/bankAccounts/{id}
![16.png](images/16.png)

GET
/bankAccounts
![17.png](images/17.png)

POST
/bankAccounts
![18.png](images/18.png)
voila account addded
![19.png](images/19.png)
on peut aussi importer notre api -dcs auto pour teste les route via import et on siasit utrl
http://localhost:8080/v3/api-docs
![20.png](images/20.png)
on test la route ge bank account et voila tt nos bank accounts
![21.png](images/21.png)
on test aussi save avec post
![22.png](images/22.png)
pour gaphql on graphql schema
poiur grpc on a profile 
rmi c interface java
client quon veut communique avec obk disan il a beosin de ses interfaces c ad un interface qui contoejtn meth etc
si vosu avez bosin de creer rest api modifier chercher etc san spaser par couche metier la on a spring dtat rets
on ajoute donc sa depemdnndacezs  spring data restt
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-data-rest</artifactId>
</dependency>
celle ci va nous permmetter de crer de creer un web servcie genriaue uqi fc avec nimpore quell entite
et donc on va a respositioer e on ajoute annotaiton rest ressource

package net.tayebi.tp1_ssd_e_back_service.repositories;

import net.tayebi.tp1_ssd_e_back_service.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

}


pour lui demarre de demarre au debut un server rest full qui permet de gere lentite de type bank account et ca creer tt get post et tt pr defaut
et poru tester eon va ingnere notre controlelr ou bein le meter dans autre diffen endpoitn vai request mapping

@RequestMapping("/api")
pour que lui acceeder faut crer /api/endpoints

la on test mais la  cpas mon eb servcie qui a rpeondu maia sspring data

la ya embeded avec accoun all et leur lien poru les y conculeter ca c spring dttat rest
![23.png](images/23.png)
come on peut teste pour celui avec rets controller avec /api
sauf qu avec cleui avec  spring datatrest  on voit mee likns 
laute nn ya qu dat json
![24.png](images/24.png)
on accede au detail diun bank account vai the first link
http://localhost:8080/bankAccounts/f5e0fed4-f0f4-4c69-a8f2-a2274431b1a4
![25.png](images/25.png)
spring dtata erst fait par defait pgaination cad si on fiat on peut voir bank accoutn vpage 1
http://localhost:8080/bankAccounts?page=0&size=2

la on voit
qu deux compte avec total eltmes et taotal pages
{
"_embedded": {
"bankAccounts": [
{
"_links": {
"self": {
"href": "http://localhost:8080/bankAccounts/f5e0fed4-f0f4-4c69-a8f2-a2274431b1a4"
},
"bankAccount": {
"href": "http://localhost:8080/bankAccounts/f5e0fed4-f0f4-4c69-a8f2-a2274431b1a4"
}
},
"createdAt": "2026-10-01T13:31:40.775Z",
"balance": 44933.525944423,
"currency": "MAD",
"type": "CURRENT_ACCOUNT"
},
{
"_links": {
"self": {
"href": "http://localhost:8080/bankAccounts/df2195fb-a750-4430-ba95-fb05b278b7f0"
},
"bankAccount": {
"href": "http://localhost:8080/bankAccounts/df2195fb-a750-4430-ba95-fb05b278b7f0"
}
},
"createdAt": "2026-10-01T13:31:40.989Z",
"balance": 59898.7509383373,
"currency": "MAD",
"type": "CURRENT_ACCOUNT"
}
]
},
"_links": {
"first": {
"href": "http://localhost:8080/bankAccounts?page=0&size=2"
},
"self": {
"href": "http://localhost:8080/bankAccounts?page=0&size=2"
},
"next": {
"href": "http://localhost:8080/bankAccounts?page=1&size=2"
},
"last": {
"href": "http://localhost:8080/bankAccounts?page=4&size=2"
},
"profile": {
"href": "http://localhost:8080/profile/bankAccounts"
}
},
"page": {
"number": 0,
"size": 2,
"totalElements": 10,
"totalPages": 5
}
}
![26.png](images/26.png)
     

on ezysa dajouter dans notre rpeository meth find by curency 


    List<BankAccount> findByType(AccountType type);
puis on test avce  http://localhost:8080/bankAccounts/search/findByTpe?type=CURRENT_ACCOUNT
et il va nous donnc que accoutns avc currnt saccoutn
![27.png](images/27.png)
pusi avec http://localhost:8080/bankAccounts/search/findByType?type=SAVINGS_ACCOUNT
avings account:
![28.png](images/28.png)
mais il affiche le id mais pour se fair eon doi fiare dans le sentites une interfac accoutn projection
@Projection(types = BankAccount.class)
et apres on speciife dans linerface ls ettribut id type
 on crere la projction donc
package net.tayebi.tp1_ssd_e_back_service.entities;

import net.tayebi.tp1_ssd_e_back_service.enums.AccountType;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = BankAccount.class, name="p1")
public interface BankAccountProjection {
public String getId();
public AccountType getType();
}
ca c du sp[ring datat rest]
on ttest nomrmamlemnt il ne donne pas id et type anais en utsnansn tporjection il va els rtrner aussi  
la il affiche que id e type 
![29.png](images/29.png) 
pour que il affiche l tt on doi mete tt atibut dna sprojection rest data clase
pour quil affiche que mes attr dans projtion
mais dan sgrpahql had les champs sotn speciife dans la requete

on met aussi
package net.tayebi.tp1_ssd_e_back_service.repositories;

import net.tayebi.tp1_ssd_e_back_service.entities.BankAccount;
import net.tayebi.tp1_ssd_e_back_service.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
@RestResource(path = "/byType")
List<BankAccount> findByType(AccountType type);


}

la on va teser avec byType au lieu de findByType et t au lieu de tupe comem ca
package net.tayebi.tp1_ssd_e_back_service.repositories;

import net.tayebi.tp1_ssd_e_back_service.entities.BankAccount;
import net.tayebi.tp1_ssd_e_back_service.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
@RestResource(path = "/byType")
List<BankAccount> findByType(@Param("t")AccountType type);


}
au lieu de http://localhost:8080/bankAccounts/search/findByType?type=SAVINGS_ACCOUNT on va faire
http://localhost:8080/bankAccounts/search/byType?t=SAVINGS_ACCOUNT
et voila ca marhce on cree lais pour meth et pour param

![30.png](images/30.png)
mas jusque maintnen on a pas respecter le snormes e pour se faire on doi utilsie rles dtpos et la couche service
on cree diretcory service et interface et implemntneations



package net.tayebi.tp1_ssd_e_back_service.service;

import net.tayebi.tp1_ssd_e_back_service.dtos.BankAccountDTOResponse;
import net.tayebi.tp1_ssd_e_back_service.dtos.BankAccountRequestDTO;
import net.tayebi.tp1_ssd_e_back_service.entities.BankAccount;
import net.tayebi.tp1_ssd_e_back_service.enums.AccountType;

public interface AccountService {
public BankAccountDTOResponse addAccount(BankAccountRequestDTO bankAccountDTO);

}
 et on cree les due xlcasse
package net.tayebi.tp1_ssd_e_back_service.dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.tayebi.tp1_ssd_e_back_service.enums.AccountType;

import java.util.Date;
@Data @AllArgsConstructor
@NoArgsConstructor @Builder
public class BankAccountRequestDTO {

    private Double balance;
    private String currency;
    private AccountType type;
}

  pas besoin denvoyer ni id lni crate att
  package net.tayebi.tp1_ssd_e_back_service.dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.tayebi.tp1_ssd_e_back_service.enums.AccountType;

import java.util.Date;
@Data @AllArgsConstructor @NoArgsConstructor
@Builder
public class BankAccountDTOResponse {
private  String id;
private Date createdAt;
private Double balance;
private String currency;
private AccountType type;
}
 on impelmtmnet notre interface de service

on doi mettre tansactionnael de spring pour ue tt mth soit transactionel
package net.tayebi.tp1_ssd_e_back_service.service;

import net.tayebi.tp1_ssd_e_back_service.dtos.BankAccountDTOResponse;
import net.tayebi.tp1_ssd_e_back_service.dtos.BankAccountRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
@Override
public BankAccountDTOResponse addAccount(BankAccountRequestDTO bankAccountDTO) {
return null;
}
}
on etnnrasnfer entit ne dto
package net.tayebi.tp1_ssd_e_back_service.service;

import net.tayebi.tp1_ssd_e_back_service.dtos.BankAccountDTOResponse;
import net.tayebi.tp1_ssd_e_back_service.dtos.BankAccountRequestDTO;
import net.tayebi.tp1_ssd_e_back_service.entities.BankAccount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
@Override
public BankAccountDTOResponse addAccount(BankAccountRequestDTO bankAccountDTO) {
BankAccount bankAccount=BankAccount.builder()
.id(UUID.randomUUID().toString())
.createdAt(new Date())
.balance(bankAccountDTO.getBalance())
.type(bankAccountDTO.getType())

                .build();
        return null;
    }
}

package net.tayebi.tp1_ssd_e_back_service.service;

import net.tayebi.tp1_ssd_e_back_service.dtos.BankAccountDTOResponse;
import net.tayebi.tp1_ssd_e_back_service.dtos.BankAccountRequestDTO;
import net.tayebi.tp1_ssd_e_back_service.entities.BankAccount;
import net.tayebi.tp1_ssd_e_back_service.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
@Autowired
BankAccountRepository bankAccountRepository;
@Override
public BankAccountDTOResponse addAccount(BankAccountRequestDTO bankAccountDTO) {

        BankAccount bankAccount=BankAccount.builder()
                .id(UUID.randomUUID().toString())
                .createdAt(new Date())
                .balance(bankAccountDTO.getBalance())
                .type(bankAccountDTO.getType())
                .currency(bankAccountDTO.getCurrency())

                .build();
        BankAccount savedBankAccount=bankAccountRepository.save(bankAccount);
        BankAccountDTOResponse bankAccountDTOResponse=new BankAccountDTOResponse();
        bankAccountDTOResponse.setId(savedBankAccount.getId());
        bankAccountDTOResponse.setBalance(savedBankAccount.getBalance());
        bankAccountDTOResponse.setType(savedBankAccount.getType());
        bankAccountDTOResponse.setCurrency(savedBankAccount.getCurrency());
        bankAccountDTOResponse.setCreatedAt(savedBankAccount.getCreatedAt());

        return bankAccountDTOResponse;
    }
}
on pase anotre notnrolelr pour mdoifier

comme ca
package net.tayebi.tp1_ssd_e_back_service.web;


import lombok.AllArgsConstructor;
import net.tayebi.tp1_ssd_e_back_service.dtos.BankAccountDTOResponse;
import net.tayebi.tp1_ssd_e_back_service.dtos.BankAccountRequestDTO;
import net.tayebi.tp1_ssd_e_back_service.entities.BankAccount;
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
 on test
via swagger

pusi on ajoute mappers
package net.tayebi.tp1_ssd_e_back_service.mappers;

import net.tayebi.tp1_ssd_e_back_service.dtos.BankAccountDTOResponse;
import net.tayebi.tp1_ssd_e_back_service.entities.BankAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
public BankAccountDTOResponse fromBankAccount(BankAccount bankAccount) {
BankAccountDTOResponse bankAccountDTOResponse = new BankAccountDTOResponse();
BeanUtils.copyProperties(bankAccount, bankAccountDTOResponse);

        return bankAccountDTOResponse;
    }
}


on pas ua rest cotroler et on fait pour ajouter le mapper mmepour service 

package net.tayebi.tp1_ssd_e_back_service.service;

import lombok.AllArgsConstructor;
import net.tayebi.tp1_ssd_e_back_service.dtos.BankAccountDTOResponse;
import net.tayebi.tp1_ssd_e_back_service.dtos.BankAccountRequestDTO;
import net.tayebi.tp1_ssd_e_back_service.entities.BankAccount;
import net.tayebi.tp1_ssd_e_back_service.mappers.AccountMapper;
import net.tayebi.tp1_ssd_e_back_service.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
//    @Autowired
BankAccountRepository bankAccountRepository;
AccountMapper accountMapper;

    @Override
    public BankAccountDTOResponse addAccount(BankAccountRequestDTO bankAccountDTO) {

        BankAccount bankAccount=BankAccount.builder()
                .id(UUID.randomUUID().toString())
                .createdAt(new Date())
                .balance(bankAccountDTO.getBalance())
                .type(bankAccountDTO.getType())
                .currency(bankAccountDTO.getCurrency())

                .build();
        BankAccount savedBankAccount=bankAccountRepository.save(bankAccount);
//        BankAccountDTOResponse bankAccountDTOResponse=new BankAccountDTOResponse();
//        bankAccountDTOResponse.setId(savedBankAccount.getId());
//        bankAccountDTOResponse.setBalance(savedBankAccount.getBalance());
//        bankAccountDTOResponse.setType(savedBankAccount.getType());
//        bankAccountDTOResponse.setCurrency(savedBankAccount.getCurrency());
//        bankAccountDTOResponse.setCreatedAt(savedBankAccount.getCreatedAt());


        return accountMapper.fromBankAccount(savedBankAccount);
    }
}


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
on teste donc 

comemnt creerr rest api  soit via spirng data rest ou via re contorller
on put m,aitnnen a uiliser graphql
 on pase a cree run controlelr qui comemunique abvec web service en utilsianst graph ql