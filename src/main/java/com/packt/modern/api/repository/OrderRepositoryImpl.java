package com.packt.modern.api.repository;

import com.packt.modern.api.entity.ItemEntity;
import com.packt.modern.api.entity.OrderEntity;
import com.packt.modern.api.exceptions.ResourceNotFoundException;
import com.packt.modern.api.model.NewOrder;
import com.packt.modern.api.model.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Transactional
@Repository
public class OrderRepositoryImpl implements OrderRepositoryExt {

    @PersistenceContext private final EntityManager em;

    private final ItemRepository itemRepository;

    private final CartRepository cartRepository;

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderRepositoryImpl(final EntityManager em, final ItemRepository itemRepository,
                               final CartRepository cartRepository,
                               final OrderItemRepository orderItemRepository) {
        this.em = em;
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public Optional<OrderEntity> insert(NewOrder m) {
        final Iterable<ItemEntity> itemsIterable =
                itemRepository.findByCustomerId(m.getCustomerId());
        final List<ItemEntity> items = StreamSupport.stream(itemsIterable.spliterator(),
                false).toList();
        if (items.size() == 0) {
            throw new ResourceNotFoundException(String.format("There are no items found in customer's cart " +
                    "for customer %s", m.getCustomerId()));
        }
        final BigDecimal total =  items.stream()
                .map(item -> BigDecimal.valueOf(item.getQuantity()).multiply(item.getPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        final Timestamp orderDate = Timestamp.from(Instant.now());
        em.createNativeQuery(
                "insert into ecomm.orders (address_id, card_id, customer_id, " +
                        "order_date, total, status) " +
                        "values (?, ?, ?, ?, ?, ?)")
                .setParameter(1, m.getAddress().getId())
                .setParameter(2, m.getCard().getId())
                .setParameter(3, m.getCustomerId())
                .setParameter(4, orderDate)
                .setParameter(5, total)
                .setParameter(6, Order.StatusEnum.CREATED.getValue())
                .executeUpdate();

}
