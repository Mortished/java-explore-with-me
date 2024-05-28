package ru.practicum.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.admin.entity.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {

}
