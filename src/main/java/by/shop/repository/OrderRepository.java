package by.shop.repository;

import by.shop.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    Iterable<Order> getAllByUserProfileId(Long id);
}
