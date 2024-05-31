package ru.practicum.service.impl;

import static ru.practicum.utils.Dictionary.CATEGORY_NAME;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.practicum.entity.Category;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.dto.CategoryDTO;
import ru.practicum.repository.CategoryRepository;
import ru.practicum.service.CategoryService;

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
        .orElseThrow(() -> new NotFoundException(CATEGORY_NAME, catId.toString()));
    category.setId(catId);
    Category categoryEntity = categoryRepository.save(modelMapper.map(category, Category.class));
    return modelMapper.map(categoryEntity, CategoryDTO.class);
  }

  @Override
  public void delete(Long catId) {
    categoryRepository.findById(catId)
        .orElseThrow(() -> new NotFoundException(CATEGORY_NAME, catId.toString()));
    categoryRepository.deleteById(catId);
  }
}
