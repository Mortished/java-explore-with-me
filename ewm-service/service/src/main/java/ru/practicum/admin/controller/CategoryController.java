package ru.practicum.admin.controller;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.admin.service.CategoryService;
import ru.practicum.model.CategoryDTO;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/categories")
@Validated
@Slf4j
public class CategoryController {

  private final CategoryService categoryService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CategoryDTO addCategory(@Valid @RequestBody final CategoryDTO body) {
    return categoryService.save(body);
  }

  @DeleteMapping("/{catId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void deleteCategory(@PathVariable final Long catId) {
    categoryService.delete(catId);
  }

  @PatchMapping("/{catId}")
  public CategoryDTO updateCategory(@PathVariable final Long catId,
      @Valid @RequestBody final CategoryDTO body) {
    return categoryService.update(catId, body);
  }

}
