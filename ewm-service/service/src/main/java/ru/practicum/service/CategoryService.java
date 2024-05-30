package ru.practicum.service;

import ru.practicum.model.CategoryDTO;

public interface CategoryService {

  CategoryDTO save(CategoryDTO category);

  CategoryDTO update(Long catId, CategoryDTO category);

  void delete(Long catId);


}
