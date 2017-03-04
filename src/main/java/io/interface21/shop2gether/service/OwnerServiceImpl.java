package io.interface21.shop2gether.service;

import io.interface21.shop2gether.ItemVO;
import io.interface21.shop2gether.OwnerService;
import io.interface21.shop2gether.OwnerVO;
import org.ameba.annotation.TxService;
import org.ameba.exception.NotFoundException;
import org.ameba.mapping.BeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.List;

import static java.lang.String.format;

/**
 * A OwnerServiceImpl is a Spring managed transactional service that deals with OwnerVO instances.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@TxService
class OwnerServiceImpl implements OwnerService<ItemVO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OwnerServiceImpl.class);
    private final BeanMapper mapper;
    private final Repositories.OwnerRepository repository;

    OwnerServiceImpl(BeanMapper mapper, Repositories.OwnerRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OwnerVO<ItemVO> findByPKey(String pKey) {
        return mapper.map(findOrDie(pKey), OwnerVO.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OwnerVO<ItemVO> save(String pKey, OwnerVO<ItemVO> toSave) {
        return mapper.map(repository.save(findOrDie(pKey).copyFrom(toSave)), OwnerVO.class);
    }

    private Owner findOrDie(String pKey) {
        return repository.findByPKey(pKey).orElseThrow(() -> new NotFoundException(format("Owner with id [%s] does not exist", pKey)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OwnerVO<ItemVO> save(@NotNull String pKey, @NotNull ItemVO item) {
        Owner saved = findOrDie(pKey);
        Item toSave = mapper.map(item, Item.class);
        if (toSave.isNew()) {
            LOGGER.debug(format("Add new item to Owner [%s], Item [%s]", pKey, toSave));
            saved.getItems().add(toSave);
        } else {
            LOGGER.debug(format("Update existing item of Owner [%s], Item [%s]", pKey, toSave));
            saved.updateItem(toSave);
        }
        saved = repository.save(saved);
        return mapper.map(saved, OwnerVO.class);
    }

    @Override
    public List<OwnerVO> findAll() {
        List<Owner> owners = repository.findAll();
        return mapper.map(owners, OwnerVO.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String delete(String pKey) {
        Owner existing = repository.findByPKey(pKey).orElseThrow
                (NotFoundException::new);
        repository.delete(existing);
        return pKey;
    }
}
