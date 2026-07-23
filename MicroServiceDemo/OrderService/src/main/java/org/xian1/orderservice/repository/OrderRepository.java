package org.xian1.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.xian1.orderservice.model.OrderRecord;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderRecord, Long> {
    Optional<OrderRecord> findByInvoiceNumber(String invoiceNumber);

    List<OrderRecord> findByUsername(String username);

    @Query("select coalesce(sum(o.totalAmount), 0) from OrderRecord o where o.username = :username")
    BigDecimal sumTotalAmountByUsername(@Param("username") String username);
}
