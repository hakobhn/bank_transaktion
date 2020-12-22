/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package am.neovision.admin.toolkit.domain.model;

import am.neovision.admin.toolkit.domain.AbstractModel;
import am.neovision.admin.toolkit.dto.Currency;
import am.neovision.admin.toolkit.dto.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @author hakob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "BANK_ACCOUNT")
public class BankAccount extends AbstractModel<Long> {

    @NotBlank
    @Column(unique = true)
    private String uuid;

    @NotBlank
    @Column(unique = true, length=16)
    private String number;

    private Double amount;

    @NotNull
    @Column(columnDefinition = "enum('EURO','USD', 'GPB')")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ACCOUNT_ID", nullable=false)
    private Account owner;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fromAccount", cascade = {CascadeType.ALL})
    private Set<Transaction> output;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "toAccount", cascade = {CascadeType.ALL})
    private Set<Transaction> input;
}
