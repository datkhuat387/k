package com.example.ol_shop.ui.admin.activity.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ol_shop.R;
import com.example.ol_shop.model.Category;
import com.example.ol_shop.network.ManagerService;
import com.example.ol_shop.network.RetrofitClient;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCategoryActivity extends AppCompatActivity {
    TextInputLayout til_name_category;
    AppCompatButton btn_add;
    ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        til_name_category = findViewById(R.id.til_name_category);
        img_back = findViewById(R.id.img_back);
        btn_add = findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCategory();
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void saveCategory(){
        String name = til_name_category.getEditText().getText().toString().trim();
        if(name.isEmpty()){
            Toast.makeText(AddCategoryActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        Category category = new Category();
        category.setName(name);
        fetchCategory(category);
    }
    private void fetchCategory(final Category category){
        ManagerService managerService = RetrofitClient.getService();
        Call<Category> call = managerService.postCategory(category);
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AddCategoryActivity.this, "Thêm thể loại thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}