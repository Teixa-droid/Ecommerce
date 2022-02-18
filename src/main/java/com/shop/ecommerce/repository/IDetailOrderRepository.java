package com.shop.ecommerce.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.shop.ecommerce.model.OrderDetail;

@Repository
public interface IDetailOrderRepository extends JpaRepository<OrderDetail, Integer>{

}
