package com.example.ol_shop.ui.admin.activity.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ol_shop.R;
import com.example.ol_shop.adapter.SpinnerCategoryAdapter;
import com.example.ol_shop.model.Category;
import com.example.ol_shop.model.Product;
import com.example.ol_shop.model.ProductPost;
import com.example.ol_shop.network.ManagerService;
import com.example.ol_shop.network.RetrofitClient;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {
    TextInputLayout til_name_product,til_price,til_quantity,til_image,til_description;
    private Spinner spinner;
    AppCompatButton btn_add;
    private List<Category> categoryList;
    ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        til_name_product = findViewById(R.id.til_name_product);
        til_price = findViewById(R.id.til_price);
        til_quantity = findViewById(R.id.til_quantity);
        til_image = findViewById(R.id.til_image);
        til_description = findViewById(R.id.til_description);
        spinner = findViewById(R.id.spin);
        btn_add = findViewById(R.id.btn_add);
        img_back = findViewById(R.id.img_back);

        fetchCategory();
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProduct();
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void fetchCategory(){
        ManagerService managerService = RetrofitClient.getService();
        Call<List<Category>> call = managerService.getListCategory();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){
                    categoryList = response.body();
                    setupSpinner();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    private void setupSpinner() {
        SpinnerCategoryAdapter spinnerCategoryAdapter = new SpinnerCategoryAdapter(categoryList);
        spinner.setAdapter(spinnerCategoryAdapter);
    }
    private void saveProduct(){
        String name = til_name_product.getEditText().getText().toString().trim();
        String price = til_price.getEditText().getText().toString().trim();
        String quantity = til_quantity.getEditText().getText().toString().trim();
        String linkImage = til_image.getEditText().getText().toString().trim();
        String description = til_description.getEditText().getText().toString().trim();
        Category selectCategory = (Category) spinner.getSelectedItem();

        if(name.isEmpty()||price.isEmpty()||quantity.isEmpty()){
            Toast.makeText(AddProductActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        ProductPost product = new ProductPost();
        product.setName(name);
        product.setPrice(Double.valueOf(price));
        product.setQuantity(quantity);
        product.setImageProduct(linkImage);
        product.setDescription(description);
        if(selectCategory != null){
            product.setCategoryId(selectCategory.get_id());
        }
        fetchProduct(product);
    }
    private void fetchProduct(final ProductPost productPost){
        ManagerService managerService = RetrofitClient.getService();
        Call<ProductPost> call = managerService.postProduct(productPost);
        call.enqueue(new Callback<ProductPost>() {
            @Override
            public void onResponse(Call<ProductPost> call, Response<ProductPost> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AddProductActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ProductPost> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}