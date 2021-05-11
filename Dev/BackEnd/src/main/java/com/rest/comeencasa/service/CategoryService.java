package com.rest.comeencasa.service;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    Category findBySlug(String slug);

    Category findCategory(String category);

    String randomColor();

    Category createCategory(String category);

    CategoryDTO makeCategoryDTO(Category category);

    List<CategoryDTO> createListCategoryDTO(List<Category> list);

    Category updateCategory(String slug, String payload);

    Boolean removeCategory(Category category);
}
