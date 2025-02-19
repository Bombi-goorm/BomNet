package com.bombi.core.domain.interest.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bombi.core.domain.interest.model.InterestProduct;
import com.bombi.core.domain.product.model.Product;

public interface InterestProductRepository extends JpaRepository<InterestProduct, Long> {

	@Query("select p from InterestProduct ip join fetch ip.member m join fetch ip.product p where m.id = :memberId")
	List<Product> findByMemberId(@Query("memberId") UUID uuid);
}
