package ru.practicum.admin.service;

import ru.practicum.model.CategoryDTO;

public interface CategoryService {

  CategoryDTO save(CategoryDTO category);

  CategoryDTO update(Long catId, CategoryDTO category);

  void delete(Long catId);


}
