package com.example.ol_shop.ui.user.fragment.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ol_shop.R;
import com.example.ol_shop.adapter.CategoryAdapter;
import com.example.ol_shop.adapter.ProductAdapter;
import com.example.ol_shop.model.Category;
import com.example.ol_shop.model.Product;
import com.example.ol_shop.network.ManagerService;
import com.example.ol_shop.network.RetrofitClient;
import com.example.ol_shop.ui.user.activity.DetailCategoryActivity;
import com.example.ol_shop.ui.user.activity.DetailProductActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeUserFragment extends Fragment {
    private RecyclerView rcv_list_product,rcv_list_category;
    private ProductAdapter productAdapter;
    private CategoryAdapter categoryAdapter;
    private List<Product> productList;
    private List<Category> categoryList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv_list_category = view.findViewById(R.id.rcv_list_category);
        rcv_list_product = view.findViewById(R.id.rcv_list_product);
        productList = new ArrayList<>();
        categoryList = new ArrayList<>();

        categoryAdapter = new CategoryAdapter(categoryList, new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String _id) {
                Intent intent = new Intent(getActivity(), DetailCategoryActivity.class);
                intent.putExtra("idCategory",_id);
                getActivity().startActivity(intent);
            }
        });

        productAdapter = new ProductAdapter(productList, new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String _id) {
                Intent intent = new Intent(getActivity(), DetailProductActivity.class);
                intent.putExtra("idProduct",_id);
                getActivity().startActivity(intent);
            }
        });

        rcv_list_category.setLayoutManager(new GridLayoutManager(getActivity(),2, GridLayoutManager.HORIZONTAL,false));
        rcv_list_category.setAdapter(categoryAdapter);
        rcv_list_product.setLayoutManager(new GridLayoutManager(getActivity(),2));
        rcv_list_product.setAdapter(productAdapter);

        fetchCategory();
        fetchProduct();
    }

    private void fetchCategory(){
        ManagerService managerService = RetrofitClient.getService();
        Call<List<Category>> call = managerService.getListCategory();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){
                    List<Category> categories = response.body();
                    Log.e("TAG", "onResponse: "+response.body());
                    if(categories != null){
                        categoryList.clear();
                        categoryList.addAll(categories);
                        categoryAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void fetchProduct(){
        ManagerService managerService = RetrofitClient.getService();
        Call<List<Product>> call = managerService.getListProduct();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    List<Product> products = response.body();
                    Log.e("TAG", "onResponse: "+response.body());
                    if(products != null){
                        productList.clear();
                        productList.addAll(products);
                        productAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}