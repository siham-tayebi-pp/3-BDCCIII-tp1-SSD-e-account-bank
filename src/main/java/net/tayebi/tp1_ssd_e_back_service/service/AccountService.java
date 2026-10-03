package net.tayebi.tp1_ssd_e_back_service.service;

import net.tayebi.tp1_ssd_e_back_service.dtos.BankAccountDTOResponse;
import net.tayebi.tp1_ssd_e_back_service.dtos.BankAccountRequestDTO;
import net.tayebi.tp1_ssd_e_back_service.entities.BankAccount;
import net.tayebi.tp1_ssd_e_back_service.enums.AccountType;

public interface AccountService {
    public BankAccountDTOResponse addAccount(BankAccountRequestDTO bankAccountDTO);
    public BankAccountDTOResponse updateAccount(String id,BankAccountRequestDTO bankAccountDTO);


}
