package com.bj.books.controller;

// 假设正确的包路径是 com.bj.books.entity，需要根据实际情况修改
import com.bj.books.entity.Book;
// 由于原导入路径 com.example.demo.service.BookService 无法解析，推测正确路径与项目相关，修改为实际路径
import com.bj.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    @GetMapping
    public List<Book> findAll() {
        return bookService.findAll();
    }
    
    @GetMapping("/{id}")
    public Book findById(@PathVariable Long id) {
        return bookService.findById(id);
    }
    
    @PostMapping
    public boolean save(@RequestBody Book book) {
        return bookService.save(book);
    }
    
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return bookService.delete(id);
    }
} 