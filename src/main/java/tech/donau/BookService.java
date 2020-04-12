package tech.donau;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import tech.donau.data.Book;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class BookService {
    @Inject
    MongoClient mongoClient;

    public void add(Book book) {
        final MongoCollection<Document> collection = getCollection();
        final Document document = new Document();
        document.put("title", book.getTitle());
        document.put("pages", book.getPages());
        collection.insertOne(document);
    }



    public List<Book> getBooks() {
        final FindIterable<Document> documents = getCollection().find();
        final ArrayList<Book> books = new ArrayList<>();
        for (Document document : documents) {
            final Book book = new Book();
            book.setTitle(document.get("title", String.class));
            book.setPages(document.get("pages", Integer.class));

            books.add(book);
        }
        return books;
    }

    private MongoCollection<Document> getCollection() {
        return mongoClient.getDatabase("books").getCollection("books");
    }
}
