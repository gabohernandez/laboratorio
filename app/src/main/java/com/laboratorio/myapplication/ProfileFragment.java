package com.laboratorio.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.laboratorio.myapplication.model.Product;
import com.laboratorio.myapplication.model.User;

public class ProfileFragment extends Fragment {

    private Context context;
    public User user;

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ProfileFragment newInstance(int columnCount) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.context = container.getContext();

        View view = inflater.inflate(R.layout.profile, container, false);
        ((TextView)view.findViewById(R.id.userName)).setText(this.user.getFirstName());
        ((TextView)view.findViewById(R.id.userLastName)).setText(this.user.getLastName());
        ((TextView) view.findViewById(R.id.userEmail)).setText(this.user.getEmail());
        ((TextView) view.findViewById(R.id.userPhone)).setText(this.user.getPhone());
        ((TextView) view.findViewById(R.id.userStreet)).setText(this.user.getAddress() != null ? this.user.getAddress().getStreet(): "");
        ((TextView) view.findViewById(R.id.userNumber)).setText(this.user.getAddress() != null ? this.user.getAddress().getNumber(): "");
        ((TextView) view.findViewById(R.id.userAge)).setText(this.user.getAge());

        if (this.user.getImage() != null) {
            String s = "data:image/jpeg;base64,";
            byte[] decodedString = Base64.decode(this.user.getImage().replace(s, ""), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ((ImageView) view.findViewById(R.id.imageView5)).setImageBitmap(decodedByte);
        }
        Button updateButton = view.findViewById(R.id.updateProfile);

        updateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView name = (TextView) view.findViewById(R.id.userName);
                TextView lastname = (TextView) view.findViewById(R.id.userLastName);

                if (context instanceof MainActivity){
                    ((MainActivity) context).updateProfile(name.getText().toString(),lastname.getText().toString());
                }
            }
        });
        return view;
    }


}
