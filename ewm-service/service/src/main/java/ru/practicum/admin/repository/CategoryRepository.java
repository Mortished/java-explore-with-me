package ru.practicum.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.admin.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
