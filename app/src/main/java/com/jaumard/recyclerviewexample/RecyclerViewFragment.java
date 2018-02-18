package com.jaumard.recyclerviewexample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jaumard.recyclerviewbinding.BindableRecyclerAdapter;
import com.jaumard.recyclerviewexample.data.RecyclerItem;
import com.jaumard.recyclerviewexample.databinding.RecyclerViewFragmentBinding;

public class RecyclerViewFragment extends Fragment implements BindableRecyclerAdapter.OnClickListener<RecyclerItem> {
    private RecyclerViewModel viewModel = new RecyclerViewModel();

    public static Fragment newInstance() {
        return new RecyclerViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler_view_fragment, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new RecyclerViewModel();

        RecyclerViewFragmentBinding binding = DataBindingUtil.bind(this.getView());
        binding.setViewModel(viewModel);
        binding.setClickHandler(this);
    }

    @Override
    public void onClick(RecyclerItem item) {
        Toast.makeText(getActivity(), item.getName() + " " + item.getFirstName(), Toast.LENGTH_SHORT).show();
    }
}
