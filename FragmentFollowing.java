package com.mohraflif55121111.githubusers;

public class FragmentFollowing extends Fragment {

    ModelSearchData modelSearchData;
    UserViewModel followingViewModel;
    FollowAdapter followingAdapter;
    RecyclerView rvListFollowing;
    ConstraintLayout layoutEmpty;
    ProgressDialog progressDialog;
    String strUsername;

    public FragmentFollowing() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Mohon Tunggu...");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Sedang menampilkan data");

        rvListFollowing = view.findViewById(R.id.rvListFollowing);
        layoutEmpty = view.findViewById(R.id.layoutEmpty);

        modelSearchData = this.getArguments().getBundle("modelSearchData");
        if (modelSearchData != null) {
            strUsername = modelSearchData.getLogin();
        }

        followingAdapter = new FollowAdapter(getContext());
        rvListFollowing.setLayoutManager(new LinearLayoutManager(getContext()));
        rvListFollowing.setAdapter(followingAdapter);
        rvListFollowing.setHasFixedSize(true);

        //method set viewmodel
        followingViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(UserViewModel.class);
        followingViewModel.setFollowingUser(strUsername);
        progressDialog.show();
        followingViewModel.getFollowingUser().observe(getViewLifecycleOwner(), modelFollowing -> {
            if (modelFollowing.size() != 0) {
                layoutEmpty.setVisibility(View.GONE);
                followingAdapter.setFollowList(modelFollowing);
            } else {
                layoutEmpty.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Following Tidak Ditemukan!", Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
        });

        return view;
    }

}
