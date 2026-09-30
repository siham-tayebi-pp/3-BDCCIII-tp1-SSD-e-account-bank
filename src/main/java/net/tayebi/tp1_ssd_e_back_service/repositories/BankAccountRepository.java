package net.tayebi.tp1_ssd_e_back_service.repositories;

import net.tayebi.tp1_ssd_e_back_service.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

}
