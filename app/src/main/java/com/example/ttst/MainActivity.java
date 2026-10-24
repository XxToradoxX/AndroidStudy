package com.example.ttst;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        // 数据源
        String[] nameArray = {"张三", "李四", "王五", "赵六", "孙七"};

        // Array适配器，使用系统自带条目布局 simple_list_item_1
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                nameArray
        );
        listView.setAdapter(adapter);

        // 条目点击监听
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedName = nameArray[position];
            Toast.makeText(MainActivity.this, selectedName, Toast.LENGTH_SHORT).show();
        });
    }
}
