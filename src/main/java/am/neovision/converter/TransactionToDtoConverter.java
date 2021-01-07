package am.neovision.converter;

import am.neovision.domain.model.Transaction;
import am.neovision.dto.TransactionDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TransactionToDtoConverter implements Converter<Transaction, TransactionDto> {

    @Autowired
    BankAccountToDtoConverter bankAccountToDtoConverter;

    @Override
    public TransactionDto convert(Transaction entity) {
        if (entity == null) {
            return null;
        }
        TransactionDto dto = new TransactionDto();
        BeanUtils.copyProperties(entity, dto);
        dto.setFrom(bankAccountToDtoConverter.convert(entity.getFromAccount()));
        dto.setTo(bankAccountToDtoConverter.convert(entity.getToAccount()));
        return dto;
    }
}
