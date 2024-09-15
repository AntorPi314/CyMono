package com.antor.cymono;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.antor.cymono.RecyclerView.AdapterC1;
import com.antor.cymono.RecyclerView.ItemC1;
import com.antor.cymono.TimestampFormatter;

public class Channel1 extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sp;
    Context context = this;
    List<ItemC1> items = new ArrayList<>();
    Intent intent;
    private AdapterC1 adapterC1;


    private DocumentSnapshot lastVisible;
    private boolean isLoading = false;
    private static final int PAGE_SIZE = 5;

    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isRefreshing = false;

    private String C1_path, C1_post_path, userPublicAddress;

    private ImageView profile_pic;
    private TextView channel_name, channel_des;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.act_channel1);

        sp = context.getSharedPreferences("userdata", Context.MODE_PRIVATE);
        intent = getIntent();
        C1_path = intent.getStringExtra("C_path");
        C1_post_path = C1_path + "/data/post";
        userPublicAddress = sp.getString("publicAddress", "");

        profile_pic = findViewById(R.id.profile_pic);
        channel_name = findViewById(R.id.channel_name);
        channel_des = findViewById(R.id.channel_des);

        getChannelInfo(C1_path);


        RecyclerView recyclerView = findViewById(R.id.rv_channel1);

        adapterC1 = new AdapterC1(this, getApplicationContext(), items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterC1);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            isRefreshing = true;
            fetchFirestoreData();
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && !isLoading && layoutManager.findLastCompletelyVisibleItemPosition() == items.size() - 1) {
                    fetchFirestoreData();
                }
            }
        });
        fetchFirestoreData();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void clickFinish(View view){
        finish();
    }

    public void getChannelInfo(String path) {
        db.collection(path).document("info").get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String name = documentSnapshot.getString("channel_name");
                        String des = documentSnapshot.getString("des");
                        String pic = documentSnapshot.getString("profile_pic");

                        channel_name.setText(name);
                        channel_des.setText(des);
                        Glide.with(this).load(pic).into(profile_pic);
                    } else {
                        Toast.makeText(this, "Document does not exist", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error fetching data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    public void OnClickDeletePost(String postId) {
        if (postId != null && !postId.isEmpty()) {
            Toast.makeText(this, "Post deleted successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to delete the post.", Toast.LENGTH_SHORT).show();
        }
    }

    public void OnClickAddNewPost(View view) {
        showPostDialog();
    }

    public void showEditPostDialog(String postId, String postText, String imgLink) {
        if(!sp.getBoolean("login", false)){
            Toast.makeText(this, "Please Login First!", Toast.LENGTH_SHORT).show();
            return;
        }
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.cust_dia_c1_post_update, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        final AlertDialog dialog = builder.create();
        dialog.show();

        EditText etPostText = dialogView.findViewById(R.id.et_post_text);
        EditText etImgLink = dialogView.findViewById(R.id.txt_img_link);
        Button btnDelete = dialogView.findViewById(R.id.btn_delete);
        Button btnUpdate = dialogView.findViewById(R.id.btn_update);

        etPostText.setText(postText);
        etImgLink.setText(imgLink);

        btnDelete.setOnClickListener(v -> {
            db.collection(C1_post_path).document(postId).delete()
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(Channel1.this, "Post deleted", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(Channel1.this, "Error deleting post: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

        btnUpdate.setOnClickListener(v -> {
            String updatedText = etPostText.getText().toString();
            String updatedImgLink = etImgLink.getText().toString();

            Map<String, Object> updatedPost = new HashMap<>();
            updatedPost.put("post", updatedText);
            updatedPost.put("img_link", updatedImgLink);

            db.collection(C1_post_path).document(postId).update(updatedPost)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(Channel1.this, "Post updated", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(Channel1.this, "Error updating post: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }

    private void deletePost(String postId) {
        db.collection(C1_post_path).document(postId).delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(Channel1.this, "Post deleted", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Channel1.this, "Error deleting post: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void updatePost(String postId, String updatedPostText, String updatedImgLink) {
        if(!sp.getBoolean("login", false)){
            Toast.makeText(this, "Please Login First!", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, Object> postUpdates = new HashMap<>();
        postUpdates.put("post", updatedPostText);
        postUpdates.put("img_link", updatedImgLink);

        db.collection(C1_post_path).document(postId).update(postUpdates)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(Channel1.this, "Post updated", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Channel1.this, "Error updating post: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    public void onPostItemClicked(String postId, String postText, String imgLink) {
        showEditPostDialog(postId, postText, imgLink);
    }

    public void showPostDialog() {
        if(!sp.getBoolean("login", false)){
            Toast.makeText(this, "Please Login First!", Toast.LENGTH_SHORT).show();
            return;
        }
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.cust_dia_c1_post, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        final AlertDialog dialog = builder.create();
        dialog.show();

        final EditText etPostText = dialogView.findViewById(R.id.et_post_text);
        final EditText txt_img_link = dialogView.findViewById(R.id.txt_img_link);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
        Button btnPost = dialogView.findViewById(R.id.btn_post);
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnPost.setOnClickListener(v -> {
            String postText = etPostText.getText().toString().trim();
            String s_txt_img_link = txt_img_link.getText().toString().trim();

            if (postText.isEmpty()) {
                Toast.makeText(Channel1.this, "Post cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                postToFirestore(postText, s_txt_img_link);
                dialog.dismiss();
            }
        });
    }

    private void postToFirestore(String postText, String imgLink) {
        Map<String, Object> post = new HashMap<>();
        post.put("owner", userPublicAddress);
        post.put("timestamp", FieldValue.serverTimestamp());
        post.put("post", postText);
        post.put("img_link", imgLink);

        db.collection(C1_post_path).add(post)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(Channel1.this, "Post added", Toast.LENGTH_SHORT).show();

                    documentReference.get().addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String postId = documentSnapshot.getId();
                            String owner = documentSnapshot.getString("owner");
                            String imgLinkPost = documentSnapshot.getString("img_link");
                            Timestamp timestamp = documentSnapshot.getTimestamp("timestamp");
                            String formattedTime = TimestampFormatter.formatTimestamp(timestamp);
                            String postContent = documentSnapshot.getString("post");

                            db.collection("user").document(owner).get().addOnCompleteListener(userTask -> {
                                if (userTask.isSuccessful() && userTask.getResult() != null) {
                                    DocumentSnapshot userDocument = userTask.getResult();
                                    String ownerName = userDocument.getString("name");
                                    String profilePic = userDocument.getString("profile_pic");

                                    items.add(0, new ItemC1(postId, profilePic, ownerName, formattedTime, postContent, imgLinkPost));
                                    adapterC1.notifyItemInserted(0);
                                }
                            });
                        }
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Channel1.this, "Error posting: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }



    private void fetchFirestoreData() {
        if (isRefreshing) {
            isRefreshing = false;
            isLoading = false;
            lastVisible = null;
            items.clear();
            //adapterC1.notifyDataSetChanged();
        }
        if (isLoading) return;
        isLoading = true;

        CollectionReference postsRef = db.collection(C1_post_path);
        Query query = postsRef.orderBy("timestamp", Query.Direction.DESCENDING).limit(PAGE_SIZE);

        if (lastVisible != null) {
            query = query.startAfter(lastVisible);
        }
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String postId = document.getId();
                    String owner = document.getString("owner");
                    String img1 = document.getString("img_link");
                    Timestamp timestamp = document.getTimestamp("timestamp");
                    String formattedTime = TimestampFormatter.formatTimestamp(timestamp);
                    String post = document.getString("post");

                    db.collection("user").document(owner).get().addOnCompleteListener(userTask -> {
                        if (userTask.isSuccessful() && userTask.getResult() != null) {
                            DocumentSnapshot userDocument = userTask.getResult();
                            String ownerName = userDocument.getString("name");
                            String profilePic = userDocument.getString("profile_pic");

                            items.add(new ItemC1(postId, profilePic, ownerName, formattedTime, post, img1));
                            adapterC1.notifyDataSetChanged();
                        }
                    });
                }
                lastVisible = task.getResult().getDocuments().get(task.getResult().size() - 1);
                adapterC1.notifyDataSetChanged();
            } else {
                //Toast.makeText(Channel1.this, "No more data", Toast.LENGTH_SHORT).show();
            }
            swipeRefreshLayout.setRefreshing(false);
            isLoading = false;
        });
    }
}






