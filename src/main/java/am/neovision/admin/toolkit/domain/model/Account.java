/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package am.neovision.admin.toolkit.domain.model;

import am.neovision.admin.toolkit.domain.AbstractModel;
import am.neovision.admin.toolkit.dto.GenderEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author hakob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ACCOUNT")
public class Account extends AbstractModel<Long> {

    @NotBlank
    @Column(unique = true)
    private String uuid;

    @NotBlank
    @Column(unique = true)
    @Size(min = 3, max = 200)
    private String email;
    @NotBlank
    private String password;

    @NotBlank
    @Size(min = 3, max = 32)
    private String firstName;
    @NotBlank
    @Size(min = 3, max = 32)
    private String lastName;
    private String avatar;

    @Enumerated(EnumType.STRING)
    @Column(name = "GENDER", columnDefinition="ENUM('M','F')")
    private GenderEnum gender;
}
