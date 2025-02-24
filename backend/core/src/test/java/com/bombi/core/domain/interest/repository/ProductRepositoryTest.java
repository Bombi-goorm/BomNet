package com.bombi.core.domain.interest.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.bombi.core.domain.category.model.Category;
import com.bombi.core.domain.category.repository.CategoryRepository;
import com.bombi.core.domain.cultivation.model.Cultivation;
import com.bombi.core.domain.product.ProductRepository;
import com.bombi.core.domain.product.model.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@DataJpaTest
@ActiveProfiles("test")
public class ProductRepositoryTest {

	@Autowired
	ProductRepository productRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@PersistenceContext
	EntityManager em;

	@Test
	void test() {
		Category category = Category.builder().name("소분류").code("3").level(3L).parent(null).build();
		categoryRepository.save(category);

		Product product = Product.builder()
			.category(category)
			.imageUrl("productImage")
			.build();
		productRepository.saveAndFlush(product);

		Cultivation cultivation = Cultivation.builder()
			.product(product)
			.cultivationFeatures("feature")
			.cultivationTips("ggoolTip")
			.build();
		em.persist(cultivation);

		em.flush();
		em.clear();

		Product findProduct = productRepository.findById(product.getId())
			.orElseThrow(IllegalArgumentException::new);

	}

	@DisplayName("소분류 코드와 중분류 코드로 상품 데이터와 대분류 조회")
	@Test
	void findProductAndBigCategoryByCategories() {
		//given
		Category bigCategory = Category.builder().name("대분류").code("1").level(1L).parent(null).build();
		Category midCategory = Category.builder().name("중분류").code("2").level(2L).parent(bigCategory).build();
		Category smallCategory = Category.builder().name("소분류").code("3").level(3L).parent(midCategory).build();
		categoryRepository.saveAll(List.of(bigCategory, midCategory, smallCategory));

		Product product = Product.builder()
			.category(smallCategory)
			.imageUrl("productImage")
			.build();
		productRepository.saveAndFlush(product);

		em.flush();
		em.clear();

		//when
		Product findProduct = productRepository.findOneByCategoryId(midCategory.getCode(), smallCategory.getCode())
			.orElseThrow(IllegalArgumentException::new);

		//then
		Category findSmallCategory = product.getCategory();
		assertThat(findSmallCategory.getName()).isEqualTo("소분류");

		Category findMidCategory = findSmallCategory.getParent();
		assertThat(findMidCategory.getName()).isEqualTo("중분류");

		Category findBigCategory = findMidCategory.getParent();
		assertThat(findBigCategory.getName()).isEqualTo("대분류");
	}

}
