package edu.cuhk.csci3310.cupo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MyProfileFragment extends Fragment {

    final Backend.User user = MainActivity.USER;
    TextView profileEmail;
    EditText profileUsername;
    EditText profileGender;
    EditText profilePhone;
    EditText profileBio;
    Button profileSaveButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileEmail = view.findViewById(R.id.profileEmail);
        profileUsername = view.findViewById(R.id.profileUsername);
        profileGender = view.findViewById(R.id.profileGender);
        profilePhone = view.findViewById(R.id.profilePhone);
        profileBio = view.findViewById(R.id.profileBio);
        profileSaveButton = view.findViewById(R.id.profileSave);

        profileEmail.setText(user.email);
        profileUsername.setText(user.username);
        profileGender.setText(user.gender);
        profilePhone.setText(user.phone);
        profileBio.setText(user.bio);
        profileSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.username = profileUsername.getText().toString();
                user.gender = profileGender.getText().toString();
                user.phone = profilePhone.getText().toString();
                user.bio = profileBio.getText().toString();
            }
        });
    }
}