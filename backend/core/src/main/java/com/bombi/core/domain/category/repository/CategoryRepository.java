package com.bombi.core.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bombi.core.domain.category.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
