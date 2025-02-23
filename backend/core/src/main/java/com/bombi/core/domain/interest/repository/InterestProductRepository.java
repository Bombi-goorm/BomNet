package com.bombi.core.domain.interest.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bombi.core.domain.interest.model.InterestProduct;

public interface InterestProductRepository extends JpaRepository<InterestProduct, Long> {

	@Query("select ip from InterestProduct ip join fetch ip.member m join fetch ip.product p where m.id = :memberId")
	List<InterestProduct> findByMemberId(UUID memberId);

	@Modifying
	@Query("delete from InterestProduct ip where ip.member.id = :memberId and ip.product.id = :productId")
	void deleteByMemberIdAndProductId(@Param("memberId") UUID memberId, @Param("productId") Long productId);
}
