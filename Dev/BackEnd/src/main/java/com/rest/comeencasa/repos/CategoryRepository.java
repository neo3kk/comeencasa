package com.rest.comeencasa.repos;


import com.rest.comeencasa.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryBySlug(String slug);

}

