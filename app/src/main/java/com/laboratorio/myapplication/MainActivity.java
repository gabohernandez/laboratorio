package com.laboratorio.myapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.Menu;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laboratorio.myapplication.model.Cart;
import com.laboratorio.myapplication.model.Category;
import com.laboratorio.myapplication.model.Product;
import com.laboratorio.myapplication.service.Service;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ProgressDialog nDialog;

    private Map<Long, Integer> cartProducts = new HashMap<>();
    private Long partialPrice = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("La Justa");
        toolbar.setSubtitle("Economía Social Solidaria");

        nDialog =  new ProgressDialog(this);
        nDialog.setMessage("Loading..");
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(false);

        changeFragmentToCategory();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        Fragment f = this.getFragmentManager().findFragmentById(R.id.placeholder);
        if (f instanceof ProductFragment){
            changeFragmentToCategory();
        }

    }

    public void changeFragmenteToCart(){
        //TODO: No pude entender como era lo de retrofit pero ya deje armados los fragments
    }



    public void changeFragmentToCategory(){
        nDialog.show();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://ec2-3-235-40-183.compute-1.amazonaws.com/api/category/")
                .addConverterFactory(JacksonConverterFactory.create(mapper)).build();

        Service service = retrofit.create(Service.class);

        service.getCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                CategoryFragment cf = new CategoryFragment();
                cf.categories = response.body();
                ft.replace(R.id.placeholder, cf);
                // ft.add(R.id.placeholder,f);
                ft.commit();
                nDialog.hide();
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void changeFragmentToProductsWithCategory(Long categoryId){
        nDialog.show();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://ec2-3-235-40-183.compute-1.amazonaws.com/api/product/")
                .addConverterFactory(JacksonConverterFactory.create(mapper)).build();

        Service service = retrofit.create(Service.class);

        service.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ProductFragment pf = new ProductFragment();
                List<Product> products = new ArrayList<>();

                for (Product product: response.body()){
                    if (product.getCategories().stream().anyMatch(c -> c.getId().equals(categoryId))){
                        products.add(product);
                    }
                }
                for (Map.Entry<Long,Integer> entry : cartProducts.entrySet()){
                    Optional<Product> currentProd = products.stream().filter(p -> p.getId().equals(entry.getKey())).findAny();
                    if (currentProd.isPresent()){
                        currentProd.get().setCount(entry.getValue());
                    }
                }

                pf.products = products;
                //pf.products = response.body();
                ft.replace(R.id.placeholder, pf);
                // ft.add(R.id.placeholder,f);
                ft.commit();
                nDialog.hide();
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void addToProduct(Long id) {
        cartProducts.put(id, cartProducts.get(id) == null? 1 : cartProducts.get(id) + 1);
    }

    public void substractToProduct(Long id) {
        if ((cartProducts.get(id) != null) && ((cartProducts.get(id) - 1) >= 0)){
            cartProducts.put(id, cartProducts.get(id) == null ? 1 : cartProducts.get(id) - 1);
        }
    }
}