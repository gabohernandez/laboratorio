package com.laboratorio.myapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laboratorio.myapplication.fragment.CartFragment;
import com.laboratorio.myapplication.fragment.CategoryFragment;
import com.laboratorio.myapplication.fragment.LoginFragment;
import com.laboratorio.myapplication.fragment.ProductFragment;
import com.laboratorio.myapplication.fragment.ReportFragment;
import com.laboratorio.myapplication.model.Category;
import com.laboratorio.myapplication.model.Product;
import com.laboratorio.myapplication.model.Report;
import com.laboratorio.myapplication.service.Service;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ProgressDialog nDialog;

    private Map<Long, Product> cartProducts = new HashMap<>();
    private Long partialPrice = 0L;

    private Context context;
    private Map<Long, Report> reports = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("La Justa");
        toolbar.setSubtitle("Economía Social Solidaria");
        toolbar.setSubtitleTextColor(Color.WHITE);
        toolbar.setTitleTextColor(Color.WHITE);

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
        if (f instanceof ProductFragment || f instanceof CartFragment){
            changeFragmentToCategory();
        }

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

    public boolean onClick(MenuItem item) {
        return true;
    }

    public void changeFragmentToCart(MenuItem item) {
        nDialog.show();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        CartFragment cf = new CartFragment();
        cf.products = this.cartProducts.values().stream().collect(Collectors.toList());
        ft.replace(R.id.placeholder, cf);
        //ft.add(R.id.placeholder,f);
        ft.commit();
        nDialog.hide();
    }

    public void changeFragmentToLogin(MenuItem item) {
        nDialog.show();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        LoginFragment cf = new LoginFragment();
        ft.replace(R.id.placeholder, cf);
        //ft.add(R.id.placeholder,f);
        ft.commit();
        nDialog.hide();
    }

    public void changeFragmentToReports(MenuItem item){
        nDialog.show();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://ec2-3-235-40-183.compute-1.amazonaws.com/api/news/")
                .addConverterFactory(JacksonConverterFactory.create(mapper)).build();

        Service service = retrofit.create(Service.class);

        service.getReports().enqueue(new Callback<List<Report>>() {
            @Override
            public void onResponse(Call<List<Report>> call, Response<List<Report>> response) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ReportFragment pf = new ReportFragment();
                List<Report> reports = new ArrayList<>();

                pf.reports = reports;
                //pf.products = response.body();
                ft.replace(R.id.placeholder, pf);
                //ft.add(R.id.placeholder,pf);
                ft.commit();
                nDialog.hide();
            }

            @Override
            public void onFailure(Call<List<Report>> call, Throwable t) {
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
                for (Map.Entry<Long,Product> entry : cartProducts.entrySet()){
                    Optional<Product> currentProd = products.stream().filter(p -> p.getId().equals(entry.getKey())).findAny();
                    if (currentProd.isPresent()){
                        currentProd.get().setCount(entry.getValue().getCount());
                    }
                }

                pf.products = products;
                //pf.products = response.body();
                ft.replace(R.id.placeholder, pf);
                //ft.add(R.id.placeholder,pf);
                ft.commit();
                nDialog.hide();
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void modifyTotal(Product product){
        cartProducts.put(product.getId(), product);
        updateTotal();
    }
    private void updateTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Product p: cartProducts.values()){
            total = total.add(new BigDecimal(p.getCount()).multiply(p.getPrice()));
        }

        TextView totalText = (TextView) findViewById(R.id.valuePrice);
        totalText.setText(total.toString());
    }


    public void deleteProduct(Product product) {
        this.cartProducts.remove(product.getId());
        updateTotal();
    }
}