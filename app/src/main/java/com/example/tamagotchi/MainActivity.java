package com.example.tamagotchi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
	FragmentManager fragmentManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		fragmentManager = getSupportFragmentManager();

		if(savedInstanceState == null){
			fragmentManager
					.beginTransaction()
					.setReorderingAllowed(true)
					.replace(R.id.fragmentContainerView,
							Pet_View.class,null)
					.commit();
			fragmentManager
					.beginTransaction()
					.setReorderingAllowed(true)
					.replace(R.id.fragmentContainerView2,
							Function_Buttons.class,null)
					.commit();
		}
		
	
	}
}