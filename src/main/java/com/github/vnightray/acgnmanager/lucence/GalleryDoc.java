package com.github.vnightray.acgnmanager.lucence;

import com.github.vnightray.acgnmanager.entity.comic.Book;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.TextField;

public class GalleryDoc {
    public static Document convertBookEntity(Book book) throws Exception {
        if (null == book) {
            throw new Exception("book is null");
        }
        Document document = new Document();
        document.add(new LongPoint("book_id", book.getBookId()));
        document.add(new TextField("book_name", book.getBookName(), Field.Store.YES));
        if (null == book.getBookProfile()){
            document.add(new TextField("book_profile", "", Field.Store.YES));
        } else {
            document.add(new TextField("book_profile", book.getBookProfile(), Field.Store.YES));
        }
        if (null == book.getTags()){
            document.add(new TextField("tag", "", Field.Store.YES));
        } else {
            document.add(new TextField("tag", book.getTags(), Field.Store.YES));
        }
        return document;
    }
}
