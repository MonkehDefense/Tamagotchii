package com.example.tamagotchi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import pl.droidsonroids.gif.GifImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Pet_View#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Pet_View extends Fragment {
	
//	int slep_mode = 0;
	boolean alive = true,
		poop = true,
		awake = true;
	int energy, hunger, happiness, evo_stage;
	ProgressBar bar_happiness, bar_hunger, bar_energy;
	GifImageView pic_pet;
	ImageButton pic_exclam, pic_zs, pic_poop;
	
	int pet_sprite;
	
	
	
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	
	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;
	
	public Pet_View() {
		// Required empty public constructor
	}
	
	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment Pet_View.
	 */
	// TODO: Rename and change types and number of parameters
	public static Pet_View newInstance(String param1, String param2) {
		Pet_View fragment = new Pet_View();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_pet__view, container, false);

		int transparent = 17170445;
		evo_stage = 0;
		
		TextView tv_name = view.findViewById(R.id.tv_name);
		bar_happiness = view.findViewById(R.id.bar_happiness);
		bar_hunger = view.findViewById(R.id.bar_hunger);
		bar_energy = view.findViewById(R.id.bar_energy);
		pic_exclam = view.findViewById(R.id.pic_exclam);
		pic_zs = view.findViewById(R.id.pic_zs);
		pic_poop = view.findViewById(R.id.pic_poop);
		pic_pet = view.findViewById(R.id.pic_pet);
		
		hunger = bar_hunger.getProgress();
		happiness = bar_happiness.getProgress();
		energy = bar_energy.getProgress();
		
		pet_sprite = R.drawable.spr_4_egg;
		
		
		Thread updates = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (alive){
						TimeUnit.SECONDS.sleep(2);
						
						if (evo_stage > 0) {
							needs_update();
							update_UI();
						}
						
					}
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		});
		
		
		
		pic_zs.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(awake){
					awake = false;
					pic_zs.setImageResource(R.drawable.alarm);
				}else{
					awake = true;
					pic_zs.setImageResource(R.drawable.comic_effect_zzz);
				}
				
			}
		});
		
		pic_poop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				poop = false;
				pic_poop.setEnabled(false);
				pic_poop.setImageResource(transparent);
			}
		});

		pic_exclam.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pic_exclam.setEnabled(false);
				pic_exclam.setImageResource(transparent);

				switch(evo_stage){
					case 0:
						pet_sprite = R.drawable.cyndaquil;
						break;
					case 1:
						pet_sprite = R.drawable.quilava;
						break;
					case 2:
						pet_sprite = R.drawable.typhlosion;
						break;
				}

				evo_stage++;
				pic_pet.setImageResource(pet_sprite);



				Thread growth_thread = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							TimeUnit.SECONDS.sleep(10);
							growth();
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}
					}
				});

				growth_thread.start();

			}
		});
		
		
		updates.start();
//		try {
//			growth();
//		} catch (InterruptedException e) {
//			throw new RuntimeException(e);
//		}
		
		return view;
	}
	
	public void needs_update() throws InterruptedException {
			
		// decrease hunger bar over time
		// manage happiness accordingly
//		hunger--;
		hunger -= 5;
		if(hunger<=0){
			alive = false;
		} else if (hunger < 40) {
//			happiness--;
			happiness-=5;
		} else if (hunger > 65) {
//			happiness++;
			happiness+=5;
		}
		
		
		// lower happiness if there's poop around
		if(poop){
			happiness-=3;
		}
		

		// use energy while awake
		// regain energy while asleep
		if(awake){
			energy--;
		} else {
			energy++;
		}

			

		// hard caps on energy and happiness
		// no need for a hard 0 cap for hunger as hunger reaching or going below 0 stops the loop and sets alive to false
		if(energy < 0){energy = 0;}
		if (energy > 100) {energy = 100;}
		if(happiness < 0){ happiness = 0;}
		if(happiness > 100){ happiness = 100;}
		
	}
	
	public void update_UI(){
		bar_hunger.setProgress(hunger);
		bar_happiness.setProgress(happiness);
		bar_energy.setProgress(energy);
	}
	
	public void growth() throws InterruptedException {
		while (alive & evo_stage < 3){
			if(happiness > 70 & hunger > 70){
				Log.d("Growth", "Inside");
				pic_exclam.setEnabled(true);
				pic_exclam.setImageResource(R.drawable.exclm);
			}else{
				TimeUnit.SECONDS.sleep(3);
			}
		}
	}
	
}