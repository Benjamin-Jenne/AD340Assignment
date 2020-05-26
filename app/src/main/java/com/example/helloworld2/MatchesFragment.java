package com.example.helloworld2;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        private static final int LENGTH = 6;
        private final String[] names;
        private final String[] pictures;
        private FirebaseViewModel vm;
        private RequestQueue queue;

        public ContentAdapter(Context context) {
            names = new String[6];
            pictures = new String[6];
            vm = new FirebaseViewModel();
            vm.getMatches(
                    (ArrayList<Match> matches) -> {
                        for(int i = 0; i < matches.size(); i++){
                            names[i] = matches.get(i).getName();
                            pictures[i] = matches.get(i).getImageUrl();
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
            Picasso.get().load(pictures[position % pictures.length]).into(holder.picture);
            holder.name.setText(names[position % names.length]);
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }
}