package com.example.midterm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.midterm.response.AuthenticationResponse;
import com.example.midterm.response.PostPOJO;
import com.example.midterm.response.PostResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostListFragment extends Fragment implements PaginationAdapter.IPage, PostAdapter.IDeletePost {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "authInfo";
    private static final String FIRST_PAGE = "1";
    protected String pageNumber = FIRST_PAGE;
    // TODO: Rename and change types of parameters
    private AuthenticationResponse authInfo;
    protected String results = "";
    private String mParam2;
List<PostPOJO> postPOJOList = new ArrayList<>();
    List<Integer> pageList = new ArrayList<>();
    RecyclerView.Adapter adapter;
    RecyclerView.Adapter pageAdapter;
    public PostListFragment() {
        // Required empty public constructor
    }

    public static PostListFragment newInstance(IPosts mListener, AuthenticationResponse authResponse) {
        PostListFragment fragment = new PostListFragment(mListener);
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, authResponse);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            authInfo = (AuthenticationResponse) getArguments().getSerializable(ARG_PARAM1);
        }
    }
View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_post_list, container, false);
        getActivity().setTitle(getString(R.string.posts_title));
        ((TextView)view.findViewById(R.id.post_welcome)).setText(getString(R.string.welcome) +" "+ authInfo.getUser_fullname());
        view.findViewById(R.id.post_create).setOnClickListener(view1 -> {
            mListener.createPost(authInfo);
        });
        view.findViewById(R.id.post_logout).setOnClickListener(view1 -> {
            mListener.logout();
        });
        RecyclerView recyclerView = view.findViewById(R.id.post_list_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PostAdapter(postPOJOList, authInfo, Integer.parseInt(pageNumber), this);
        pageAdapter = new PaginationAdapter(pageList, authInfo, this);
        refreshList(FIRST_PAGE);
        recyclerView.setAdapter(adapter);
        RecyclerView paginationRecyclerView = view.findViewById(R.id.pagination);
        LinearLayoutManager linearLayoutManagerHorizontal = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        paginationRecyclerView.setAdapter(pageAdapter);
        paginationRecyclerView.setLayoutManager(linearLayoutManagerHorizontal);
        ((TextView)view.findViewById(R.id.post_page_show)).setText(results);
        return view;
    }

    protected void refreshList(String pageNumber) {
        this.pageNumber = pageNumber;
        Service.getPost(authInfo, pageNumber, new Handler(message -> {
            if(message.arg1 != 1) {
                Toast.makeText(getContext(), getString(R.string.failed_get_post), Toast.LENGTH_SHORT).show();
                return false;
            }
            PostResponse response = (PostResponse) message.obj;
            postPOJOList.clear();
            postPOJOList.addAll(response.getPosts());
            double pages = Math.floor(Integer.parseInt(response.getTotalCount())/ Integer.parseInt(response.getPageSize()));
            pageList.clear();
            for(int i=1;i<=pages+1;i++) {
                pageList.add(i);
            }
            ((TextView)view.findViewById(R.id.post_page_show)).setText(getString(R.string.showing_page)+ " " + pageNumber + " out of " +  ((int)pages +1));
            adapter.notifyDataSetChanged();
            pageAdapter.notifyDataSetChanged();
            return false;
        }));
    }

    public PostListFragment(IPosts mListener) {
        this.mListener = mListener;
    }

    IPosts mListener;

    @Override
    public void gotoPage(int page) {
        refreshList(Integer.toString(page));
    }

    @Override
    public void postDeleted() {
        refreshList(pageNumber);
    }

    interface IPosts {
        void createPost(AuthenticationResponse auth);
        void logout();
    }
}