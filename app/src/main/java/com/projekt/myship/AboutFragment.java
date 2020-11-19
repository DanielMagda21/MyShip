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

import static com.projekt.myship.R.id.*;

public class AboutFragment extends Fragment {
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState

    ) {
        ((MainActivity) getActivity()).setActionBarTitle("About");
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.about_fragment, container, false);
    }

    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_logged, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == Main) {
            NavHostFragment.findNavController(AboutFragment.this)
                    .navigate(action_aboutFragment_to_loggedFragment);
            return true;
        } else if (itemId == package_archives) {
            NavHostFragment.findNavController(AboutFragment.this)
                    .navigate(action_aboutFragment_to_packageArchives);
            return true;
        } else if (itemId == Logout) {
            NavHostFragment.findNavController(AboutFragment.this)
                    .navigate(action_aboutFragment_to_loginFragment);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
