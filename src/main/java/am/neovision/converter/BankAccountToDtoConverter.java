package am.neovision.converter;

import am.neovision.domain.model.BankAccount;
import am.neovision.dto.BankAccountDto;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BankAccountToDtoConverter implements Converter<BankAccount, BankAccountDto> {
    @Override
    public BankAccountDto convert(BankAccount entity) {
        if (entity == null) {
            return null;
        }
        BankAccountDto dto = new BankAccountDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
