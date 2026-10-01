package net.tayebi.tp1_ssd_e_back_service.entities;

import net.tayebi.tp1_ssd_e_back_service.enums.AccountType;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = BankAccount.class, name="p1")
public interface BankAccountProjection {
    public String getId();
    public AccountType getType();
    public Double getBalance();
}
