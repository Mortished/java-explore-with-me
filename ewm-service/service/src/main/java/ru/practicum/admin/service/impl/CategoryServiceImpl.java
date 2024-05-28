package ru.practicum.admin.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.practicum.admin.entity.Category;
import ru.practicum.admin.repository.CategoryRepository;
import ru.practicum.admin.service.CategoryService;
import ru.practicum.exception.CategoryNotFoundException;
import ru.practicum.model.CategoryDTO;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final ModelMapper modelMapper = new ModelMapper();

  @Override
  public CategoryDTO save(CategoryDTO category) {
    Category categoryEntity = categoryRepository.save(modelMapper.map(category, Category.class));
    return modelMapper.map(categoryEntity, CategoryDTO.class);
  }

  @Override
  public CategoryDTO update(Long catId, CategoryDTO category) {
    categoryRepository.findById(catId)
        .orElseThrow(() -> new CategoryNotFoundException(catId.toString()));
    category.setId(catId);
    Category categoryEntity = categoryRepository.save(modelMapper.map(category, Category.class));
    return modelMapper.map(categoryEntity, CategoryDTO.class);
  }

  @Override
  public void delete(Long catId) {
    categoryRepository.findById(catId)
        .orElseThrow(() -> new CategoryNotFoundException(catId.toString()));
    categoryRepository.deleteById(catId);
  }
}
