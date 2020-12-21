package am.neovision.admin.toolkit.converter;

import am.neovision.admin.toolkit.domain.model.Account;
import am.neovision.admin.toolkit.dto.AccountDto;
import am.neovision.admin.toolkit.dto.Gender;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AccountToDtoConverter implements Converter<Account, AccountDto> {
    @Override
    public AccountDto convert(Account account) {
        if (account == null) {
            return null;
        }
        AccountDto accountDto = new AccountDto();
        BeanUtils.copyProperties(account, accountDto);
        if (StringUtils.isNoneBlank(account.getAvatar())) {
            accountDto.setAvatar(account.getAvatar());
        } else {
            if (account.getGender() != null && account.getGender().equals(Gender.FEMALE)) {
                accountDto.setAvatar("/dist/img/avatar-female.jpg");
            } else {
                accountDto.setAvatar("/dist/img/avatar-male.jpg");
            }
        }
        return accountDto;
    }
}
