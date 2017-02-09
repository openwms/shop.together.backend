package io.interface21.shop2gether.service;

import io.interface21.shop2gether.ItemService;
import io.interface21.shop2gether.ItemVO;
import org.ameba.annotation.TxService;
import org.ameba.exception.NotFoundException;
import org.ameba.mapping.BeanMapper;

import static java.lang.String.format;

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
    public ItemVO getByPKey(String pKey) {
        Item item = findOrDie(pKey);
        return mapper.map(item, ItemVO.class);
    }

    private Item findOrDie(String pKey) {
        return repository.findByPKey(pKey).orElseThrow(() -> new NotFoundException(format("Item with id [%s] does not exist", pKey)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(String pKey) {
        Item item = findOrDie(pKey);
        repository.delete(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePartially(String pKey, ItemVO itemVO) {
        Item item = findOrDie(pKey);
        item.copyFrom(mapper.map(itemVO, Item.class));
        repository.save(item);
    }
}
