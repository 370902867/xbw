package com.bj.books.service;

// 假设Book类的正确包路径是com.bj.books.entity
import com.bj.books.entity.Book;
// 假设BookMapper的正确包路径是com.bj.books.mapper
import com.bj.books.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    
    @Autowired
    private BookMapper bookMapper;
    
    public List<Book> findAll() {
        return bookMapper.findAll();
    }
    
    public Book findById(Long id) {
        return bookMapper.findById(id);
    }
    
    public boolean save(Book book) {
        if (book.getId() == null) {
            return bookMapper.insert(book) > 0;
        }
        return bookMapper.update(book) > 0;
    }
    
    public boolean delete(Long id) {
        return bookMapper.delete(id) > 0;
    }
} 