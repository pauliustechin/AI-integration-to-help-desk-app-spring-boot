package com.psem.Spring.boot.with.Ollama.service;

import com.psem.Spring.boot.with.Ollama.model.Category;

public interface CategoryService {
    Category checkIfCategoryExists(String categoryName);
}
