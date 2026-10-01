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
