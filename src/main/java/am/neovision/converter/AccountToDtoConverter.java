package am.neovision.converter;

import am.neovision.domain.model.Account;
import am.neovision.dto.AccountDto;
import am.neovision.dto.Gender;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AccountToDtoConverter implements Converter<Account, AccountDto> {
    @Override
    public AccountDto convert(Account entity) {
        if (entity == null) {
            return null;
        }
        AccountDto dto = new AccountDto();
        BeanUtils.copyProperties(entity, dto);
        if (StringUtils.isNoneBlank(entity.getAvatar())) {
            dto.setAvatar(entity.getAvatar());
        } else {
            if (entity.getGender() != null && entity.getGender().equals(Gender.FEMALE)) {
                dto.setAvatar("/dist/img/avatar-female.jpg");
            } else {
                dto.setAvatar("/dist/img/avatar-male.jpg");
            }
        }
        return dto;
    }
}
