package com.example.mytech.service;

import com.example.mytech.entity.Category;
import com.example.mytech.model.request.CategoryRep;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    public List<Category> getListCategory();

    public Category createCategory(CategoryRep req);

    public void updateCategory(String id, CategoryRep req);

    public void deleteCategory(String id);

    public Category getCategoryById(String id);

    Page<Category> adminGetListCategory(String name, int page);
}
