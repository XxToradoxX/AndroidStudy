package com.example.ttst;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 数据源：书名、封面图资源 id、作者
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(R.drawable.cover_book_1, "红楼梦", "曹雪芹"));
        bookList.add(new Book(R.drawable.cover_book_2, "西游记", "吴承恩"));
        bookList.add(new Book(R.drawable.cover_book_3, "水浒传", "施耐庵"));
        bookList.add(new Book(R.drawable.cover_book_4, "三国演义", "罗贯中"));
        bookList.add(new Book(R.drawable.cover_book_5, "聊斋志异", "蒲松龄"));

        BookAdapter adapter = new BookAdapter(bookList);
        recyclerView.setAdapter(adapter);

        // 条目点击监听
        adapter.setOnItemClickListener(position -> {
            Book book = bookList.get(position);
            Toast.makeText(MainActivity.this,
                    book.getBookName() + " · " + book.getAuthor(),
                    Toast.LENGTH_SHORT).show();
        });
    }
}
