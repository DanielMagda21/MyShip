package com.projekt.myship;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.jetbrains.annotations.NotNull;

public class AboutFragment extends Fragment {
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState

    ) {
        ((MainActivity) getActivity()).setActionBarTitle("About");  /// Setting new Title for This Fragment
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.about_fragment, container, false); /// Inflate the layout for this fragment
    }

    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    ///Setting up Toolbar Menu
    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_logged, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) /// Handle Menu item selection
        {
            case R.id.Main:
                NavHostFragment.findNavController(AboutFragment.this)
                        .navigate(R.id.action_aboutFragment_to_loggedFragment);
                return true;
            case R.id.package_archives:
                NavHostFragment.findNavController(AboutFragment.this)
                        .navigate(R.id.action_aboutFragment_to_packageArchives);
                return true;
            case R.id.Logout:
                NavHostFragment.findNavController(AboutFragment.this)
                        .navigate(R.id.action_aboutFragment_to_loginFragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
