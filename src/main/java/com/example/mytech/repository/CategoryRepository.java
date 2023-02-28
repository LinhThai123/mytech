package com.example.mytech.repository;

import com.example.mytech.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category , String> {

    boolean existsByName (String name ) ;

    Category findByName(String name) ;

    Category getCategoryById (String id);

    Page<Category> findByNameContaining (String name, Pageable pageable);
}
