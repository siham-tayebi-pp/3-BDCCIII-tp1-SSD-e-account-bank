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
    @Override
    public BankAccountDTOResponse updateAccount(String id,BankAccountRequestDTO bankAccountDTO) {

        BankAccount bankAccount=BankAccount.builder()
                .id(id)
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
