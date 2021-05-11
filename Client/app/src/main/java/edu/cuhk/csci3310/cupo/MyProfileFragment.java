package edu.cuhk.csci3310.cupo;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MyProfileFragment extends Fragment {

    final Backend.User user = MainActivity.USER;
    TextView profileEmail;
    EditText profileUsername;
    EditText profileGender;
    EditText profilePhone;
    EditText profileBio;
    Button profileSaveButton;
    Button profileChangePasswordButton;

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
        profileChangePasswordButton = view.findViewById(R.id.profileChangePassword);

        profileEmail.setText(user.email);
        profileUsername.setText(user.username);
        profileGender.setText(user.gender);
        profilePhone.setText(user.phone);
        profileBio.setText(user.bio);
        profileSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUsername();
                updateGender();
                updatePhone();
                updateBio();
            }
        });
        profileChangePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ChangePasswordActivity.class));
            }
        });
    }

    private void updateUsername() {
        final String email = profileEmail.getText().toString();
        final String username = profileUsername.getText().toString();

        Backend.getInstance().updateUsername(email, username, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("cupo", e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            // update USER
                            user.username = username;
                            // update UI
                            profileUsername.setText(username);
                        }
                    }
                });
            }
        });
    }

    private void updateGender() {
        final String email = profileEmail.getText().toString();
        final String gender = profileGender.getText().toString();

        Backend.getInstance().updateGender(email, gender, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("cupo", e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            // update USER
                            user.gender = gender;
                            // update UI
                            profileGender.setText(gender);
                        }
                    }
                });
            }
        });
    }

    private void updatePhone() {
        final String email = profileEmail.getText().toString();
        final String phone = profilePhone.getText().toString();

        Backend.getInstance().updatePhone(email, phone, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("cupo", e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            // update USER
                            user.phone = phone;
                            // update UI
                            profilePhone.setText(phone);
                        }
                    }
                });
            }
        });
    }

    private void updateBio() {
        final String email = profileEmail.getText().toString();
        final String bio = profileBio.getText().toString();

        Backend.getInstance().updateBio(email, bio, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("cupo", e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            // update USER
                            user.bio = bio;
                            // update UI
                            profileBio.setText(bio);
                        }
                    }
                });
            }
        });
    }
}