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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laboratorio.myapplication.model.BodyLoginRequest;
import com.laboratorio.myapplication.model.CartBodyRequest;
import com.laboratorio.myapplication.model.CartGeneral;
import com.laboratorio.myapplication.model.CartNode;
import com.laboratorio.myapplication.model.CartNodeDate;
import com.laboratorio.myapplication.model.CartProduct;
import com.laboratorio.myapplication.model.CartProductWrapper;
import com.laboratorio.myapplication.model.Category;
import com.laboratorio.myapplication.model.General;
import com.laboratorio.myapplication.model.LoginResponse;
import com.laboratorio.myapplication.model.Node;
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
        this.visibleTotal();

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
                if (response.code() != 200){
                    showToast(true, "Se ha producido un error al buscar los productos", response.message());
                }else {
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ProductFragment pf = new ProductFragment();
                        List<Product> products = new ArrayList<>();

                        for (Product product : response.body()) {
                            if (product.getCategories().stream().anyMatch(c -> c.getId().equals(categoryId)) && (product.getStock() > 0)) {
                                products.add(product);
                            }
                        }
                        for (Map.Entry<Long, Product> entry : cartProducts.entrySet()) {
                            Optional<Product> currentProd = products.stream().filter(p -> p.getId().equals(entry.getKey())).findAny();
                            if (currentProd.isPresent()) {
                                currentProd.get().setCount(entry.getValue().getCount());
                            }
                        }
                        if (products.size() > 0) {
                            pf.products = products;
                            //pf.products = response.body();
                            ft.replace(R.id.placeholder, pf);
                            //ft.add(R.id.placeholder,pf);
                            ft.commit();
                        } else {
                            showToast(false, "Esta categoria no cuenta con productos con stock disponible", null);
                        }

                }
                nDialog.hide();
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                showToast(true, "Se ha producido un error al buscar los productos", t.getMessage());
                nDialog.hide();
            }
        });
        this.visibleTotal();
    }

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
                if (response.code() != 200){
                    showToast(true, "Se ha producido un error al buscar el producto", response.message());
                }else {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ProductSingleItemFragment cf = new ProductSingleItemFragment();
                    cf.product = response.body();
                    ft.replace(R.id.placeholder, cf);
                    //ft.add(R.id.placeholder,f);
                    ft.commit();
                }
                nDialog.hide();
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                showToast(true, "Se ha producido un error al buscar el producto", t.getMessage());
                nDialog.hide();
            }
        });
        this.invisibleTotal();
    }

    public void logout(MenuItem item){
        this.loggedUser = null;
        mOptionsMenu.findItem(R.id.loginid).setVisible(true);
        mOptionsMenu.findItem(R.id.logoutid).setVisible(false);
        mOptionsMenu.findItem(R.id.perfilid).setVisible(false);
        showToast(false, "Logout exitoso", null);
    }

    public void changeFragmentToProfile(MenuItem item) {

        changeFragmentToProfile();

    }

    public void changeFragmentToProfile(){
        nDialog.show();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ProfileFragment cf = new ProfileFragment();
        cf.user = this.loggedUser.getUser();
        ft.replace(R.id.placeholder, cf);
        this.invisibleTotal();
        //ft.add(R.id.placeholder,f);
        ft.commit();
        nDialog.hide();
    }
    public void changeFragmentToCategory(MenuItem item){
        this.changeFragmentToCategory();
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
                if (response.code() != 200){
                    showToast(true, "Se ha producido un error al buscar las categorias", response.message());
                }else {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    CategoryFragment cf = new CategoryFragment();
                    cf.categories = response.body();
                    ft.replace(R.id.placeholder, cf);
                    // ft.add(R.id.placeholder,f);
                    ft.commit();
                }
                nDialog.hide();
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                showToast(true, "Se ha producido un error al buscar las categorias", t.getMessage());
                nDialog.hide();
            }
        });
        this.invisibleTotal();
    }

    //CHECKOUT
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
                if (response.code() != 200){
                    showToast(true, "Se ha producido un error al buscar las noticias", response.message());
                }else {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ReportFragment pf = new ReportFragment();
                    List<Report> reports = response.body().getPage();

                    pf.report = reports;
                    //pf.products = response.body();
                    ft.replace(R.id.placeholder, pf);
                    //ft.add(R.id.placeholder,pf);
                    ft.commit();
                }
                nDialog.hide();
            }

            @Override
            public void onFailure(Call<ReportPage> call, Throwable t) {
                showToast(true, "Se ha producido un error al buscar las noticias", t.getMessage());
                nDialog.hide();
            }
        });
        this.invisibleTotal();
    }

    public void changeFragmentToSingleReport(Long id){
        nDialog.show();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String url = "http://ec2-3-227-239-131.compute-1.amazonaws.com";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(JacksonConverterFactory.create(mapper)).build();

        Service service = retrofit.create(Service.class);

        service.getReport(id).enqueue(new Callback<Report>() {
            @Override
            public void onResponse(Call<Report> call, Response<Report> response) {
                if (response.code() != 200){
                    showToast(true, "Se ha producido un error al buscar la noticia", response.message());
                }else {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    SingleReportFragment cf = new SingleReportFragment();
                    cf.report = response.body();
                    ft.replace(R.id.placeholder, cf);
                    //ft.add(R.id.placeholder,f);
                    ft.commit();
                }
                nDialog.hide();
            }

            @Override
            public void onFailure(Call<Report> call, Throwable t) {
                showToast(true, "Se ha producido un error al buscar la noticia", t.getMessage());
                nDialog.hide();
            }
        });
        this.invisibleTotal();
    }

    //LOGIN
    public void changeFragmentToLogin(MenuItem item) {
        changeFragmentToLogin();
    }

    //LOGIN
    public void changeFragmentToLogin() {
        nDialog.show();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        LoginFragment cf = new LoginFragment();
        ft.replace(R.id.placeholder, cf);
        //ft.add(R.id.placeholder,f);
        this.invisibleTotal();
        ft.commit();
        nDialog.hide();
    }

    public  void login(String user, String password){
        if (user == null || user.isEmpty() || password == null || password.isEmpty()){
            showToast(false, "Ingrese usuario y contraseña", null);
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
                    showToast(true, "Se ha producido un error en el login", response.message());
                }else {
                    showToast(false, "Login exitoso", null);
                    loggedUser = response.body();
                    mOptionsMenu.findItem(R.id.loginid).setVisible(false);
                    mOptionsMenu.findItem(R.id.logoutid).setVisible(true);
                    mOptionsMenu.findItem(R.id.perfilid).setVisible(true);
                    changeFragmentToCategory();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                showToast(true, "Se ha producido un error en el login", t.getMessage());
                nDialog.hide();
            }
        });
        this.invisibleTotal();
    }

    public void showToast(boolean error, String message, String messageException){
        if (error) {
            messageException = messageException != null && !messageException.isEmpty() ? ": " + messageException : "";
        }
        Toast.makeText(getApplicationContext(),error?  message + messageException : message,Toast.LENGTH_SHORT).show();
    }

    //PRODUCER
    public void changeFragmentToSingleProducer(Long id){
        nDialog.show();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String url = "http://ec2-3-227-239-131.compute-1.amazonaws.com";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(JacksonConverterFactory.create(mapper)).build();

        Service service = retrofit.create(Service.class);

        service.getProducer(id).enqueue(new Callback<Producer>() {
            @Override
            public void onResponse(Call<Producer> call, Response<Producer> response) {
                if (response.code() != 200){
                    showToast(true, "Se ha producido al buscar al productor", response.message());
                }else {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ProducerSingleFragment cf = new ProducerSingleFragment();
                    cf.producer = response.body();
                    ft.replace(R.id.placeholder, cf);
                    //ft.add(R.id.placeholder,f);
                    ft.commit();
                }
                nDialog.hide();
            }

            @Override
            public void onFailure(Call<Producer> call, Throwable t) {
                showToast(true, "Se ha producido al buscar al productor", t.getMessage());
                nDialog.hide();
            }
        });
        this.invisibleTotal();
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
                if (response.code() != 200){
                    showToast(true, "Se ha producido al buscar los productores", response.message());
                }else {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ProducerFragment pf = new ProducerFragment();
                    List<Producer> producers = new ArrayList<>();

                    pf.producers = response.body();
                    //pf.products = response.body();
                    ft.replace(R.id.placeholder, pf);
                    //ft.add(R.id.placeholder,pf);
                    ft.commit();
                }
                nDialog.hide();
            }

            @Override
            public void onFailure(Call<List<Producer>> call, Throwable t) {
                showToast(true, "Se ha producido al buscar los productores", t.getMessage());
                nDialog.hide();
            }
        });
        this.invisibleTotal();
    }

    //ABOUT US
    public void changeFragmentToAboutUs(MenuItem item) {
        nDialog.show();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        AboutUsFragment cf = new AboutUsFragment();
        ft.replace(R.id.placeholder, cf);
        this.invisibleTotal();
        //ft.add(R.id.placeholder,f);
        ft.commit();
        nDialog.hide();
    }

    public void modifyTotal(Product product){
        cartProducts.put(product.getId(), product);
        updateTotal();
    }

    private void updateTotal() {
        BigDecimal total = getTotal();

        TextView totalText = (TextView) findViewById(R.id.valuePrice);
        totalText.setText(total.toString());
    }

    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Product p: cartProducts.values()){
            total = total.add(new BigDecimal(p.getCount()).multiply(p.getPrice()));
        }
        return total;
    }

    public void deleteProduct(Product product) {
        this.cartProducts.remove(product.getId());
        updateTotal();
    }

    public void updateProfile(String name, String lastName) {
        if (name == null || name.isEmpty() || lastName == null || lastName.isEmpty()){
            showToast(false, "Ingrese nombre y apellido", null);
            return;
        }

        nDialog.show();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        loggedUser.getUser().setFirstName(name);
        loggedUser.getUser().setFirstName(lastName);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://ec2-3-227-239-131.compute-1.amazonaws.com")
                .addConverterFactory(JacksonConverterFactory.create(mapper)).build();

        Service service = retrofit.create(Service.class);

        service.updateProfile("Bearer " + loggedUser.getValue(), loggedUser.getUser(), loggedUser.getUser().getId()).enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                nDialog.hide();
                if (response.code() != 200){
                    showToast(true, "Se ha producido un al actualizar el perfil", response.message());
                }else {
                    showToast(false, "Perfil actualizado con éxito", null);
                    loggedUser.setUser(response.body());
                    cartProducts= new HashMap<>();
                    changeFragmentToCategory();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                showToast(true, "Se ha producido un error al actualizar el perfil", t.getMessage());
                nDialog.hide();
            }
        });
        this.invisibleTotal();
    }

    public void showLastStep() {

        if (loggedUser == null){
            changeFragmentToLogin();
            return;
        }

        nDialog.show();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://ec2-3-227-239-131.compute-1.amazonaws.com/api/general/active/")
                .addConverterFactory(JacksonConverterFactory.create(mapper)).build();

        Service service = retrofit.create(Service.class);

        service.getGeneralActive().enqueue(new Callback<General>() {
            @Override
            public void onResponse(Call<General> call, Response<General> response) {
                if (response.code() != 200) {
                    showToast(true, "Se ha producido al buscar los nodos activos", response.message());
                } else {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    CheckoutFragment pf = new CheckoutFragment();

                    pf.total = getTotal();
                    pf.general = response.body();
                    //pf.products = response.body();
                    ft.replace(R.id.placeholder, pf);
                    //ft.add(R.id.placeholder,pf);
                    ft.commit();
                }
                nDialog.hide();
            }
            @Override
            public void onFailure(Call<General> call, Throwable t) {
                showToast(true, "Se ha producido al buscar los nodos activos", t.getMessage());
                nDialog.hide();
            }
        });
    }

    public void invisibleTotal(){
        this.findViewById(R.id.valuePrice).setVisibility(View.INVISIBLE);
        this.findViewById(R.id.textView6).setVisibility(View.INVISIBLE);
    }

    public void visibleTotal(){
        this.findViewById(R.id.valuePrice).setVisibility(View.VISIBLE);
        this.findViewById(R.id.textView6).setVisibility(View.VISIBLE);
    }

    public void nodeSelected(View menu){
        System.out.println(menu);
    }

    public void buy(General general, Node nodeSelected, String medioPago){

        nDialog.show();

        CartBodyRequest body = new CartBodyRequest();

        //Productos
        List<CartProductWrapper> products = new ArrayList<>();
        this.cartProducts.forEach((k,v) -> {
            CartProduct cp = new CartProduct();
            cp.setId(v.getId());
            CartProductWrapper cpw = new CartProductWrapper();
            cpw.setProduct(cp);
            cpw.setQuantity(v.getCount());
            products.add(cpw);
        });
        body.setCartProducts(products);

        //General
        CartGeneral cartGeneral = new CartGeneral();
        cartGeneral.setId(general.getId());
        body.setGeneral(cartGeneral);

        //Node
        CartNodeDate cnd = new CartNodeDate();
        cnd.setId(general.getActiveNodes().stream().filter(gn -> gn.getNode().getId() == nodeSelected.getId()).findFirst().get().getId());
        CartNode cn = new CartNode();
        cn.setId(nodeSelected.getId());
        cnd.setNode(cn);
        body.setNodeDate(cnd);

        body.setObservation(medioPago);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://ec2-3-227-239-131.compute-1.amazonaws.com")
                .addConverterFactory(JacksonConverterFactory.create(mapper)).build();

        Service service = retrofit.create(Service.class);

        service.saveCart("Bearer " + loggedUser.getValue(), body).enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                nDialog.hide();
                if (response.code() != 200){
                    showToast(true, "Se ha producido un error al intentar comprar", response.message());
                }else {
                    showToast(false, "Se ha realizado la compra con éxito", null);
                    changeFragmentToCategory();
                    cartProducts = new HashMap<>();
                    ((TextView) findViewById(R.id.valuePrice)).setText("0.00");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                showToast(true, "Se ha producido un error al intentar comprar", t.getMessage());
                nDialog.hide();
            }
        });
        this.invisibleTotal();
    }

    public void changeFragmentToRegister() {
        nDialog.show();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        RegisterFragment cf = new RegisterFragment();
        ft.replace(R.id.placeholder, cf);
        //ft.add(R.id.placeholder,f);
        this.invisibleTotal();
        ft.commit();
        nDialog.hide();
    }

    public void changeFragmentToAddress() {
        nDialog.show();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        AddressFragment cf = new AddressFragment();
        ft.replace(R.id.placeholder, cf);
        //ft.add(R.id.placeholder,f);
        this.invisibleTotal();
        ft.commit();
        nDialog.hide();
    }
}