package com.example.helloworld2;

import android.Manifest;
import android.content.DialogInterface;
import android.provider.Settings;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.RequestQueue;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MatchesFragment extends Fragment{
    private TextView matchesPlaceholder;
    private Context fragmentContext;
    private FirebaseViewModel vm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        vm = new FirebaseViewModel();
        return recyclerView;
    }
    //Referenced
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView picture;
        public TextView name;
        public Button like_button;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_matches, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.card_image);
            name = (TextView) itemView.findViewById(R.id.card_title);
            like_button = (Button) itemView.findViewById(R.id.like_button);
            like_button.setOnClickListener((View.OnClickListener) this);
        }
        public void onClick(View v) {
            if (v.getId() == R.id.like_button) {
                Context context = itemView.getContext();
                //referenced https://stackoverflow.com/questions/5861191/using-strings-xml-w-android
                Toast toast = Toast.makeText(context, context.getResources().getString(R.string.you_liked) + " " + name.getText().toString(), Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
    /**
     * Adapter to display recycler view.
     */
    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        private static final int LENGTH = 6;
        private final ArrayList<String> names = new ArrayList<String>();
        private final ArrayList<String> pictures = new ArrayList<String>();
        private final ArrayList<String> lat = new ArrayList<String>();
        private final ArrayList<String> longitude = new ArrayList<String>();
        private FirebaseViewModel vm;
        private RequestQueue queue;
        //Location
        private LocationManager locationManager;
        private Location currentLocation = new Location("");

        public ContentAdapter(Context context) {
            vm = new FirebaseViewModel();
            //Location
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle(getContext().getResources().getString(R.string.Location_Services_Disabled));
                builder.setTitle(getContext().getResources().getString(R.string.Enable_Location));
                builder.setPositiveButton(getContext().getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60 * 1000, 10, locationListenerNetwork);
                Toast.makeText(getContext(), R.string.network_provider_started_running, Toast.LENGTH_LONG).show();
            }
            vm.getMatches(
                    (ArrayList<Match> matches) -> {
                        for (int i = 0; i < matches.size(); i++) {
                            String latCheck = matches.get(i).getLat();
                            String longCheck = matches.get(i).getLongitude();
                            //Referenced https://stackoverflow.com/questions/17983865/making-a-location-object-in-android-with-latitude-and-longitude-values
                            Location locationCheck = new Location("");
                            locationCheck.setLatitude(Double.parseDouble(latCheck));
                            locationCheck.setLongitude(Double.parseDouble(longCheck));
                            double d = locationCheck.distanceTo(currentLocation);
                            double max_distance = 16093.4;
                            if(d < max_distance){
                                names.add(matches.get(i).getName());
                                pictures.add(matches.get(i).getImageUrl());
                                lat.add(matches.get(i).getLat());
                                longitude.add(matches.get(i).getLongitude());
                            }
                        }
                        notifyDataSetChanged();
                    });

        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            try{
                Picasso.get().load(pictures.get(position)).into(holder.picture);
                holder.name.setText(names.get(position));
            }
            catch (IndexOutOfBoundsException e){
                Log.i("inside onBindViewHoldeer", "caught the array out of bounds exception");
            }
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }

        final LocationListener locationListenerNetwork = new LocationListener() {
            public void onLocationChanged(Location location) {
                Double longitudeNetwork = location.getLongitude();
                Double latitudeNetwork = location.getLatitude();
                currentLocation.setLongitude(longitudeNetwork);
                currentLocation.setLatitude(latitudeNetwork);

                getActivity().runOnUiThread(()-> {
                    Log.i("Longitude", String.format("%s", longitudeNetwork));
                    Log.i("Latitude", String.format("%s", latitudeNetwork));
                    Toast.makeText(getContext(), R.string.network_provider_update, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), longitudeNetwork + ", " + latitudeNetwork, Toast.LENGTH_LONG).show();
                });
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {}

            @Override
            public void onProviderEnabled(String s) {}

            @Override
            public void onProviderDisabled(String s) {}
        };
    }
}