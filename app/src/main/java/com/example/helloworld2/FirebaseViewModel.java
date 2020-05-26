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

    public void addMatch(Match m) {
        firebaseModel.addMatch(m);
    }

    public void getMatches(Consumer<ArrayList<Match>> responseCallback) {
        firebaseModel.getMatches(
                (QuerySnapshot querySnapshot) -> {
                    if (querySnapshot != null) {
                        ArrayList<Match> matches = new ArrayList<>();
                        for (DocumentSnapshot todoSnapshot : querySnapshot.getDocuments()) {
                            Match m = todoSnapshot.toObject(Match.class);
                            assert m != null;
                            m.uid = todoSnapshot.getId();
                            matches.add(m);
                        }
                        responseCallback.accept(matches);
                    }
                },
                (databaseError -> System.out.println("Error reading Matches: " + databaseError))
        );
    }

    public void updateMatch(Match m) {
        firebaseModel.updateMatchById(m);
    }

    public void clear() {
        firebaseModel.clear();
    }
}