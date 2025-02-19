package com.bombi.core.domain.interest.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.bombi.core.domain.category.model.Category;
import com.bombi.core.domain.interest.model.InterestProduct;
import com.bombi.core.domain.member.model.Member;
import com.bombi.core.domain.member.model.Role;
import com.bombi.core.domain.member.repository.MemberRepository;
import com.bombi.core.domain.product.ProductRepository;
import com.bombi.core.domain.product.model.Product;

import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional
public class InterestProductRepositoryTest {

	@Autowired
	InterestProductRepository interestProductRepository;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	EntityManager em;

	@DisplayName("관심상품 등록")
	@Test
	void registerInterest() {
		//given
		Role userRole = Role.ofUser();
		em.persist(userRole);

		Member member = Member.builder().authEmail("test").platform("platform").role(userRole).build();
		memberRepository.save(member);

		Category category = new Category(1L, "카테고리");
		em.persist(category);

		Product product = Product.builder().description("product1").imageUrl("url").category(category).build();
		em.persist(product);

		InterestProduct interestProduct = new InterestProduct(member, product);
		interestProductRepository.save(interestProduct);

		em.flush();
		em.clear();

		//when
		List<InterestProduct> interestProducts = interestProductRepository.findByMemberId(member.getId());

		//then
		assertThat(interestProducts).hasSize(1)
			.extracting(InterestProduct::getProduct)
			.extracting(Product::getDescription).contains("product1");
	}

	@DisplayName("관심 상품 삭제")
	@Test
	void deleteByMemberIdAndProductId() {
		//given
		Role userRole = Role.ofUser();
		em.persist(userRole);

		Member member = Member.builder().authEmail("test").platform("platform").role(userRole).build();
		memberRepository.save(member);

		Category category = new Category(1L, "카테고리");
		em.persist(category);

		Product product = Product.builder().description("product1").imageUrl("url").category(category).build();
		em.persist(product);

		InterestProduct interestProduct = new InterestProduct(member, product);
		interestProductRepository.save(interestProduct);

		em.flush();
		em.clear();

		//when
		interestProductRepository.deleteByMemberIdAndProductId(member.getId(), product.getId());

		//then
		List<InterestProduct> interestProducts = interestProductRepository.findAll();
		assertThat(interestProducts).isEmpty();
	}
}
