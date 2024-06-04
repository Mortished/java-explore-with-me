package ru.practicum.controller.pub;

import java.util.List;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.model.dto.CategoryDTO;
import ru.practicum.service.CategoryService;

@RestController
@AllArgsConstructor
@RequestMapping("/categories")
@Validated
@Slf4j
public class CategoryPublicController {

  private final CategoryService categoryService;

  @GetMapping
  public List<CategoryDTO> getCategories(
      @RequestParam(required = false, defaultValue = "0") @Min(0) Integer from,
      @RequestParam(required = false, defaultValue = "10") @Min(1) Integer size
  ) {
    log.info("GET /categories");
    return categoryService.findAll(from, size);
  }

  @GetMapping("/{catId}")
  public CategoryDTO getCategory(@PathVariable Long catId) {
    log.info("GET /categories/{}", catId);
    return categoryService.findById(catId);
  }

}
