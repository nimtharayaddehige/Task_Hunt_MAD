package com.example.afinal;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.afinal.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new ManageTaskFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item-> {

            switch (item.getItemId()) {

                case R.id.manage_task:
                    replaceFragment(new ManageTaskFragment());
                    break;
                case R.id.post_task:
                    replaceFragment(new PostTaskFragment());
                    break;
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.chat:
                    replaceFragment(new ChatFragment());
                    break;

            }
            return true;
        });

        }

        private void replaceFragment(ChatFragment fragment){

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransition = fragmentManager.beginTransaction();
            FragmentTransaction.replace(R.id.frame_layout,fragment);
            fragmentTransition.commit();


        }
    }
