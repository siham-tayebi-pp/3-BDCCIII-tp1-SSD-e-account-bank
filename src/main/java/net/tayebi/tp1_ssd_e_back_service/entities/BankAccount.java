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
