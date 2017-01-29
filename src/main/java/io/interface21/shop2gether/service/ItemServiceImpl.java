package io.interface21.shop2gether.service;

import static java.lang.String.format;

import io.interface21.shop2gether.ItemService;
import io.interface21.shop2gether.ItemVO;
import org.ameba.annotation.TxService;
import org.ameba.exception.NotFoundException;
import org.ameba.mapping.BeanMapper;

/**
 * A ItemServiceImpl is a transactional Spring managed service that deals with {@link ItemVO ItemVO} instances.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@TxService
class ItemServiceImpl implements ItemService {

    private final BeanMapper mapper;
    private final Repositories.ItemRepository repository;

    ItemServiceImpl(BeanMapper mapper, Repositories.ItemRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemVO getById(Long id) {
        Item item = repository.findOne(id);
        NotFoundException.throwIfNull(item, format("No Item with id %s exists!", id));
        return mapper.map(item, ItemVO.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {
        repository.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePartially(Long id, ItemVO itemVO) {
        Item item = repository.findOne(id);
        NotFoundException.throwIfNull(item, format("No Item with id %s exists!", id));
        item.copyFrom(mapper.map(itemVO, Item.class));
        repository.save(item);
    }
}
