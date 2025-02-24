package com.bombi.core.domain.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bombi.core.domain.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("select p from Product p join fetch p.category sc"
		+ " join fetch sc.parent mc"
		+ " join fetch mc.parent lc"
		+ " where sc.code = :smallCategoryCode and mc.code = :midCategoryCode")
	Optional<Product> findOneByCategoryId(String midCategoryCode, String smallCategoryCode);

	@Query("select p from Product p join fetch p.category sc"
		+ " join fetch sc.parent mc"
		+ " join fetch mc.parent lc"
		+ " where sc.id = :smallCategoryId and mc.id = :midCategoryId")
	Optional<Product> findOneByCategoryId(Long midCategoryId, Long smallCategoryId);

	@Query("select p from Product p join fetch p.category sc"
		+ " join fetch sc.parent mc"
		+ " join fetch mc.parent lc")
	List<Product> findTop5ByCategoryId(Pageable pageable);
}
