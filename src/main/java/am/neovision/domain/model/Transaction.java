/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package am.neovision.domain.model;

import am.neovision.domain.AbstractModel;
import am.neovision.dto.TransactionStatus;
import am.neovision.dto.TransactionType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hakob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "TRANSACTION")
public class Transaction extends AbstractModel<Long> {

    @NotBlank
    @Column(unique = true)
    private String uuid;

    @NotBlank
    @Column(unique = true, length = 16)
    private String serialNumber;

    @Column(columnDefinition = "DECIMAL(10,2)")
    private Float amount;

    @NotNull
    @Column(columnDefinition = "enum('DEPOSIT', 'WITHDRAWAL', 'TRANSFER')")
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @NotNull
    @Column(columnDefinition = "enum('PENDING', 'ACCEPTED', 'REJECTED', 'CANCELED')")
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FROM_BANK_ACCOUNT_ID", nullable = false)
    private BankAccount fromAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TO_BANK_ACCOUNT_ID", nullable = false)
    private BankAccount toAccount;
}
