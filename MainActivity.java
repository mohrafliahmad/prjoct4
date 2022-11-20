package com.mohraflif55121111.githubusers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    SearchAdapter searchAdapter;
    UserViewModel searchViewModel;
    ProgressDialog progressDialog;
    RecyclerView rvListUser;
    EditText searchUser;
    ImageView imageClear, imageFavorite;
    ConstraintLayout layoutEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchUser = findViewById(R.id.searchUser);
        imageClear = findViewById(R.id.imageClear);
        imageFavorite = findViewById(R.id.imageFavorite);
        rvListUser = findViewById(R.id.rvListUser);
        layoutEmpty = findViewById(R.id.layoutEmpty);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Mohon Tunggu...");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Sedang menampilkan data");

        imageClear.setOnClickListener(view -> {
            searchUser.getText().clear();
            imageClear.setVisibility(View.GONE);
            layoutEmpty.setVisibility(View.VISIBLE);
            rvListUser.setVisibility(View.GONE);
        });

        imageFavorite.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
            startActivity(intent);
        });

        //method action search
        searchUser.setOnEditorActionListener((v, actionId, event) -> {
            String strUsername = searchUser.getText().toString();
            if (strUsername.isEmpty()) {
                Toast.makeText(MainActivity.this, "Form tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            } else {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    progressDialog.show();
                    searchViewModel.setSearchUser(strUsername);
                    InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    imageClear.setVisibility(View.VISIBLE);
                    layoutEmpty.setVisibility(View.GONE);
                    return true;
                }
            }
            return false;
        });

        searchAdapter = new SearchAdapter(this);
        rvListUser.setLayoutManager(new LinearLayoutManager(this));
        rvListUser.setAdapter(searchAdapter);
        rvListUser.setHasFixedSize(true);

        //method set viewmodel
        searchViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(UserViewModel.class);
        searchViewModel.getResultList().observe(this, modelSearchData -> {
            progressDialog.dismiss();
            if (modelSearchData.size() != 0) {
                searchAdapter.setSearchUserList(modelSearchData);
            } else {
                Toast.makeText(MainActivity.this, "Pengguna Tidak Ditemukan!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}