package ru.practicum.service;

import java.util.List;
import ru.practicum.model.dto.CategoryDTO;

public interface CategoryService {

  CategoryDTO save(CategoryDTO category);

  CategoryDTO update(Long catId, CategoryDTO category);

  void delete(Long catId);

  List<CategoryDTO> findAll(Integer from, Integer size);

  CategoryDTO findById(Long catId);

}
