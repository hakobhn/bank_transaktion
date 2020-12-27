/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package am.neovision.domain.model;

import am.neovision.domain.AbstractModel;
import am.neovision.dto.Currency;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author hakob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "CURRENCY_RATE")
public class CurrencyRate extends AbstractModel<Long> {

    @NotNull
    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double rate;

    @NotNull
    @Column(columnDefinition = "enum('EURO', 'GPB')")
    @Enumerated(EnumType.STRING)
    private Currency currency;
}
