package com.example.helloworld2;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
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

        newTodoItemText = findViewById(R.id.newTodoItemText);

        vm.getMatches(
                (ArrayList<Match> matches) -> {
                    FragmentManager manager = getSupportFragmentManager();
                    TodoItemFragment fragment = (TodoItemFragment) manager.findFragmentByTag("todoItemFragment");

                    if (fragment != null) {
                        // Remove fragment to re-add it
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.remove(fragment);
                        transaction.commit();
                    }

                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(ARG_DATA_SET, todoItems);

                    TodoItemFragment todoItemFragment = new TodoItemFragment();
                    todoItemFragment.setArguments(bundle);

                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.add(R.id.todoItemListFragmentContainer, todoItemFragment, "todoItemFragment");
                    transaction.commit();
                });
        return recyclerView;
    }
    //Referenced
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView picture;
        public TextView name;
        public TextView description;
        public Button like_button;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_matches, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.card_image);
            name = (TextView) itemView.findViewById(R.id.card_title);
            description = (TextView) itemView.findViewById(R.id.card_text);
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
        private final String[] mPlaces;
        private final String[] mPlaceDesc;
        private final Drawable[] mPlacePictures;
        public ContentAdapter(Context context) {
            Resources resources = context.getResources();
            mPlaces = resources.getStringArray(R.array.places);
            mPlaceDesc = resources.getStringArray(R.array.place_desc);
            TypedArray a = resources.obtainTypedArray(R.array.places_picture);
            mPlacePictures = new Drawable[a.length()];
            for (int i = 0; i < mPlacePictures.length; i++) {
                mPlacePictures[i] = a.getDrawable(i);
            }
            a.recycle();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.picture.setImageDrawable(mPlacePictures[position % mPlacePictures.length]);
            holder.name.setText(mPlaces[position % mPlaces.length]);
            holder.description.setText(mPlaceDesc[position % mPlaceDesc.length]);
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }
}