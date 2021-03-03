package com.laboratorio.myapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.Touch;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laboratorio.myapplication.model.BodyLoginRequest;
import com.laboratorio.myapplication.model.Category;
import com.laboratorio.myapplication.model.LoginResponse;
import com.laboratorio.myapplication.model.ReportPage;
import com.laboratorio.myapplication.model.Producer;
import com.laboratorio.myapplication.model.Product;
import com.laboratorio.myapplication.model.Report;
import com.laboratorio.myapplication.model.User;
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

    private LoginResponse loggedUser;

    private Menu mOptionsMenu;

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
        this.mOptionsMenu = menu;
        menu.findItem(R.id.logoutid).setVisible(false);
        menu.findItem(R.id.perfilid).setVisible(false);
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

    public boolean onClick(MenuItem item) {
        return true;
    }

    //CART
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


    //PRODUCT
    public void changeFragmentToProductsWithCategory(Long categoryId){
        nDialog.show();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://ec2-3-227-239-131.compute-1.amazonaws.com/api/product/")
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
                //TODO
    public void changeFragmentToSingleProduct(Long id){
        nDialog.show();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String url = "http://ec2-3-227-239-131.compute-1.amazonaws.com";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(JacksonConverterFactory.create(mapper)).build();

        Service service = retrofit.create(Service.class);

        service.getProduct(id).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                nDialog.show();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ProductSingleItemFragment cf = new ProductSingleItemFragment();
                cf.product = response.body();
                ft.replace(R.id.placeholder, cf);
                //ft.add(R.id.placeholder,f);
                ft.commit();
                nDialog.hide();
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void logout(MenuItem item){
        this.loggedUser = null;
        mOptionsMenu.findItem(R.id.loginid).setVisible(true);
        mOptionsMenu.findItem(R.id.logoutid).setVisible(false);
        mOptionsMenu.findItem(R.id.perfilid).setVisible(false);
        showToast(false, "Logout exitoso");
    }

    public void changeFragmentToProfile(MenuItem item){

    }

    //CATEGORY
    public void changeFragmentToCategory(){
        nDialog.show();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://ec2-3-227-239-131.compute-1.amazonaws.com/api/category/")
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

    //CHECKOUT
                //TODO
    public void changeFragmentToCheckout() {

    }

    //REPORT
    public void changeFragmentToReports(MenuItem item){
        nDialog.show();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://ec2-3-227-239-131.compute-1.amazonaws.com/api/news/")
                .addConverterFactory(JacksonConverterFactory.create(mapper)).build();

        Service service = retrofit.create(Service.class);

        service.getReports().enqueue(new Callback<ReportPage>() {
            @Override
            public void onResponse(Call<ReportPage> call, Response<ReportPage> response) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ReportFragment pf = new ReportFragment();
                List<Report> reports = response.body().getPage();

                pf.report = reports;
                //pf.products = response.body();
                ft.replace(R.id.placeholder, pf);
                //ft.add(R.id.placeholder,pf);
                ft.commit();
                nDialog.hide();
            }

            @Override
            public void onFailure(Call<ReportPage> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

                //TODO
    public void changeFragmenteToSingleReport(){

    }

    //LOGIN
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

    public  void login(String user, String password){
        if (user == null || user.isEmpty() || password == null || password.isEmpty()){
            showToast(false, "Ingrese usuario y contraseña");
            return;
        }

        nDialog.show();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        BodyLoginRequest body = new BodyLoginRequest();
        body.setUserName(user);
        body.setUserPassword(password);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://ec2-3-227-239-131.compute-1.amazonaws.com/api/producer/")
                .addConverterFactory(JacksonConverterFactory.create(mapper)).build();

        Service service = retrofit.create(Service.class);

        service.login(body).enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                nDialog.hide();

                if (response.code() != 200){
                    showToast(true, response.message());
                }else {
                    showToast(false, "Login exitoso");
                    loggedUser = response.body();
                    mOptionsMenu.findItem(R.id.loginid).setVisible(false);
                    mOptionsMenu.findItem(R.id.logoutid).setVisible(true);
                    mOptionsMenu.findItem(R.id.perfilid).setVisible(true);
                    changeFragmentToCategory();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                showToast(true, t.getMessage());
            }
        });
    }

    public void showToast(boolean error, String message){
        if (error) {
            message = message != null && !message.isEmpty() ? ": " + message : "";
        }
        Toast.makeText(getApplicationContext(),error? "Se ha producido un error" + message : message,Toast.LENGTH_SHORT).show();
    }

    //PRODUCER
                //TODO
    public void changeFragmentToSingleProducer(){

    }

    public void changeFragmentToProducers(MenuItem item){
        nDialog.show();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://ec2-3-227-239-131.compute-1.amazonaws.com/api/producer/")
                .addConverterFactory(JacksonConverterFactory.create(mapper)).build();

        Service service = retrofit.create(Service.class);

        service.getProducers().enqueue(new Callback<List<Producer>>() {
            @Override
            public void onResponse(Call<List<Producer>> call, Response<List<Producer>> response) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ProducerFragment pf = new ProducerFragment();
                List<Producer> producers = new ArrayList<>();

                pf.producers = response.body();
                //pf.products = response.body();
                ft.replace(R.id.placeholder, pf);
                //ft.add(R.id.placeholder,pf);
                ft.commit();
                nDialog.hide();
            }

            @Override
            public void onFailure(Call<List<Producer>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    //ABOUT US
    public void changeFragmentToAboutUs(MenuItem item) {
        nDialog.show();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        AboutUsFragment cf = new AboutUsFragment();
        ft.replace(R.id.placeholder, cf);
        //ft.add(R.id.placeholder,f);
        ft.commit();
        nDialog.hide();
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