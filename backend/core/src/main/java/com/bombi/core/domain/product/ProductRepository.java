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
		+ " join fetch p.cultivation culti"
		+ " join fetch culti.productionCondition"
		+ " where mc.id = :midCategoryId and sc.id = :smallCategoryId")
	Optional<Product> findProductInfoByCategoryId(Long midCategoryId, Long smallCategoryId);

	@Query("select p from Product p join fetch p.category sc"
		+ " join fetch sc.parent mc"
		+ " join fetch mc.parent lc"
		+ " join fetch p.cultivation culti"
		+ " join fetch culti.productionCondition"
		+ " where mc.name = :midCategoryName and sc.name = :smallCategoryName")
	Optional<Product> findProductInfoByItemAndVariety(String midCategoryName, String smallCategoryName);

	@Query("select p from Product p join fetch p.category sc"
		+ " join fetch sc.parent mc"
		+ " join fetch mc.parent lc")
	List<Product> findTop5ByCategoryId(Pageable pageable);
}
