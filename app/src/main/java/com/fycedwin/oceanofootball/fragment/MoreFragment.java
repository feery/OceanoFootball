package com.fycedwin.oceanofootball.fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.fycedwin.oceanofootball.R;
import com.fycedwin.oceanofootball.activity.AboutActivity;
import com.fycedwin.oceanofootball.activity.ContactActivity;
import com.fycedwin.oceanofootball.activity.LoginActivity;
import com.fycedwin.oceanofootball.activity.ProfilSettingActivity;
import com.fycedwin.oceanofootball.activity.StatusBookingActivity;
import com.fycedwin.oceanofootball.helper.DatabaseManager;
import com.fycedwin.oceanofootball.helper.SessionManager;
import java.util.HashMap;

public class MoreFragment extends Fragment {

    private LinearLayout profile,status,faq,about,logout;
    private SessionManager session;
    private DatabaseManager db;
    private TextView nama ,email;

    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_more, container, false);
        db = new DatabaseManager(getContext());

        nama =(TextView)rootView.findViewById(R.id.nama_user);
        email= (TextView)rootView.findViewById(R.id.email_user);
        profile=(LinearLayout)rootView.findViewById(R.id.profileSetting);
        status=(LinearLayout)rootView.findViewById(R.id.statusBooking);
        faq=(LinearLayout)rootView.findViewById(R.id.contact);
        about=(LinearLayout)rootView.findViewById(R.id.About);
        logout=(LinearLayout)rootView.findViewById(R.id.logout);

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        String name_database = user.get("name");
        String email_database = user.get("email");
        nama.setText(name_database);
        email.setText(email_database);
        session = new SessionManager(getActivity());

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ProfilSettingActivity.class));
            }
        });
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), StatusBookingActivity.class));
            }
        });

        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ContactActivity.class));
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AboutActivity.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    DialogLogout();

                }
            catch (Exception e){
                Toast.makeText(getActivity(), "ERROR"+e, Toast.LENGTH_LONG).show();
            }
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }

    private void logoutUser() {
        try {

            session.setLogin(false);

            // db.deleteUsers();
        }
        catch (Exception e){
            Toast.makeText(getActivity(), "ERROR :" +e,Toast.LENGTH_LONG).show();
        }

        // Launching the login activity

    }

    private void DialogLogout(){
        // Error in login. Get the error message
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Add the buttons
        builder.setMessage("Are you sure want to logout?").setTitle("Logout");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                db.deleteUsers();
                logoutUser();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
