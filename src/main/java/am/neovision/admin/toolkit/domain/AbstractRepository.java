package am.neovision.admin.toolkit.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface AbstractRepository<E, S extends Serializable> extends JpaRepository<E, S> {

    Page<E> findBySearch(String search, Pageable pageable);
}
