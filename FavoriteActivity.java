package com.mohraflif55121111.githubusers;

public class FavoriteActivity extends AppCompatActivity {

    ArrayList<ModelUser> modelUserArrayList = new ArrayList<>();
    private FavoriteHelper favoriteHelper;
    private FavoriteAdapter favoriteAdapter;
    Toolbar toolbar;
    ConstraintLayout layoutEmpty;
    RecyclerView rvListFavoriteUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        toolbar = findViewById(R.id.toolbar);
        layoutEmpty = findViewById(R.id.layoutEmpty);
        rvListFavoriteUser = findViewById(R.id.rvListFavoriteUser);

        favoriteHelper = FavoriteHelper.getFavoriteHelper(FavoriteActivity.this);
        favoriteHelper.open();

        toolbar.setTitle(null);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        favoriteAdapter = new FavoriteAdapter();
        rvListFavoriteUser.setLayoutManager(new LinearLayoutManager(this));
        rvListFavoriteUser.setAdapter(favoriteAdapter);
        rvListFavoriteUser.setHasFixedSize(true);

        getDataFavorite();
    }

    //method show data
    private void getDataFavorite() {
        if (modelUserArrayList.size() == 0) {
            layoutEmpty.setVisibility(View.VISIBLE);
            rvListFavoriteUser.setVisibility(View.GONE);
        } else {
            layoutEmpty.setVisibility(View.GONE);
            rvListFavoriteUser.setVisibility(View.VISIBLE);
            favoriteAdapter.setFavoriteUserList(modelUserArrayList);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        getDataFavorite();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
