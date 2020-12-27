package am.neovision.service;

import am.neovision.domain.AbstractModel;
import am.neovision.domain.AbstractRepository;
import am.neovision.dto.AbstractDto;
import am.neovision.exceptions.NotFoundException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public abstract class AbstractService<E extends AbstractModel<Long>, D extends AbstractDto> {

    protected abstract AbstractRepository<E, Long> getRepository();

    protected abstract Converter<E, D> getToDtoConverter();

    public Page<D> list(String search, Pageable pageable) {
        return getRepository().findBySearch(search, pageable)
                .map(account -> getToDtoConverter().convert(account));
    }

    public D get(Long id) {
        return getRepository().findById(id)
                .map(ent -> getToDtoConverter().convert(ent))
                .orElseThrow(NotFoundException::new);
    }

    protected D save(E entity) {
        return getToDtoConverter().convert(getRepository().save(entity));
    }

    protected void update(E entity) {
        getRepository().findById(entity.getId())
                .map(found -> getRepository().save(entity))
                .orElseThrow(NotFoundException::new);

    }

    public void delete(Long id) {
        getRepository().deleteById(id);
    }

}