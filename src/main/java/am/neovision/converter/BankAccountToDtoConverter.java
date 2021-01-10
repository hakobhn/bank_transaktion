package am.neovision.converter;

import am.neovision.domain.model.BankAccount;
import am.neovision.domain.model.Transaction;
import am.neovision.dto.BankAccountDto;
import am.neovision.dto.transaction.TransactionStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BankAccountToDtoConverter implements Converter<BankAccount, BankAccountDto> {
    @Override
    public BankAccountDto convert(BankAccount entity) {
        if (entity == null) {
            return null;
        }
        BankAccountDto dto = new BankAccountDto();
        BeanUtils.copyProperties(entity, dto);
        Double availableAmount = entity.getAmount() -
                entity.getOutput().stream()
                        .filter(trans -> trans.getStatus() == TransactionStatus.PENDING)
                        .collect(Collectors.summingDouble(Transaction::getFromAmount));
        dto.setAvailableAmount(availableAmount.floatValue());
        return dto;
    }
}
