package com.example.helloworld2;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.function.Consumer;

public class FirebaseViewModel {

    private FirebaseModel firebaseModel;

    public FirebaseViewModel() {
        firebaseModel = new FirebaseModel();
    }

    public void addTodoItem(Match m) {
        firebaseModel.addTodoItem(m);
    }

    public void getMatches(Consumer<ArrayList<Match>> responseCallback) {
        firebaseModel.getMatches(
                (QuerySnapshot querySnapshot) -> {
                    if (querySnapshot != null) {
                        ArrayList<Match> todoItems = new ArrayList<>();
                        for (DocumentSnapshot todoSnapshot : querySnapshot.getDocuments()) {
                            Match m = todoSnapshot.toObject(Match.class);
                            assert m != null;
                            m.uid = todoSnapshot.getId();
                            todoItems.add(m);
                        }
                        responseCallback.accept(todoItems);
                    }
                },
                (databaseError -> System.out.println("Error reading Todo Items: " + databaseError))
        );
    }

    public void updateMatch(Match m) {
        firebaseModel.updateMatchById(m);
    }

    public void clear() {
        firebaseModel.clear();
    }
}