package com.bj.books.mapper;

// 根据当前文件路径，推测正确的导入路径应该是com.bj.books.entity.Book
import com.bj.books.entity.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookMapper {
    
    @Select("SELECT * FROM books")
    List<Book> findAll();
    
    @Select("SELECT * FROM books WHERE id = #{id}")
    Book findById(Long id);
    
    @Insert("INSERT INTO books (title, author, isbn, price, description) VALUES (#{title}, #{author}, #{isbn}, #{price}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Book book);
    
    @Update("UPDATE books SET title=#{title}, author=#{author}, isbn=#{isbn}, price=#{price}, description=#{description} WHERE id=#{id}")
    int update(Book book);
    
    @Delete("DELETE FROM books WHERE id = #{id}")
    int delete(Long id);
} 