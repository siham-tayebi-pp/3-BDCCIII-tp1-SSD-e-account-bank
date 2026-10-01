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
