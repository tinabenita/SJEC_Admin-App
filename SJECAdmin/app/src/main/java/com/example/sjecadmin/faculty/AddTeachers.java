package com.example.sjecadmin.faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sjecadmin.NoticeData;
import com.example.sjecadmin.R;
import com.example.sjecadmin.UploadNotice;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class AddTeachers extends AppCompatActivity {

    private ImageView addTeacherImage;
    private EditText addTeacherName, addTeacherEmail, addTeacherPost;
    private Spinner addTeacherCategory;
    private Button addTeachereBtn;
    private final int REQ=1;
    private Bitmap bitmap=null;
    private String category;
    private String name, email, post, downloadUrl="";
    private ProgressDialog pd;
    private StorageReference storageReference;
    private DatabaseReference reference, dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teachers);

        addTeacherImage = (ImageView) findViewById(R.id.addTeacherImage);
        addTeacherName = (EditText) findViewById(R.id.addTeacherName);
        addTeacherEmail = (EditText) findViewById(R.id.addTeacherEmail);
        addTeacherPost = (EditText) findViewById(R.id.addTeacherPost);
        addTeacherCategory = (Spinner) findViewById(R.id.addTeacherCategory);
        addTeachereBtn = (Button) findViewById(R.id.addTeachereBtn);

        pd = new ProgressDialog(this);

        reference = FirebaseDatabase.getInstance().getReference().child("teacher");
        storageReference = FirebaseStorage.getInstance().getReference();


        String[] items= new String[]{"Select Category","Computer Science & Engineering","Mechanical Engineering","Electrical & Electronics Engineering","Intelligent Computing & Business Studies","  "," ","Electronics & Communication Engineering","Civil Engineering","Physics","Mathematics","Chemistry"};
        addTeacherCategory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,items));


        addTeacherCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category=addTeacherCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        addTeacherImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();

            }
        });
        addTeachereBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });
    }

    private void checkValidation() {
        name = addTeacherName.getText().toString();
        email = addTeacherEmail.getText().toString();
        post = addTeacherPost.getText().toString();

        if(name.isEmpty()){
            addTeacherName.setError("Empty");
            addTeacherName.requestFocus();
        } else if (email.isEmpty()) {
            addTeacherEmail.setError("Empty");
            addTeacherEmail.requestFocus();
        } else if (post.isEmpty()) {
            addTeacherPost.setError("Empty");
            addTeacherPost.requestFocus();
        } else if (category.equals("Select Category")) {
            Toast.makeText(this, "Please Select Teacher Category", Toast.LENGTH_SHORT).show();
        } else if (bitmap==null) {
            pd.setMessage("Uploading...");
            pd.show();
            insertData();
        } else {
            uploadImage();
        }
    }

    private void uploadImage() {

        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalImg= baos.toByteArray();
        final StorageReference filePath;
        filePath=storageReference.child("Teachers").child(finalImg+"jpg");
        final UploadTask uploadTask=filePath.putBytes(finalImg);
        uploadTask.addOnCompleteListener(AddTeachers.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl=String.valueOf(uri);
                                    insertData();
                                }
                            });
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(AddTeachers.this,"Something went wrong",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void insertData() {



        dbRef=reference.child(category);
        final String uniqueKey= dbRef.push().getKey();


        TeacherData teacherData=new TeacherData(name, email, post, downloadUrl, uniqueKey);

        dbRef.child(uniqueKey).setValue(teacherData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();;
                Toast.makeText(AddTeachers.this, "Teacher Added", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AddTeachers.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery(){
        Intent pickImage= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage,REQ);
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ && resultCode==RESULT_OK){
            Uri uri = data.getData();
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            addTeacherImage.setImageBitmap(bitmap);
        }
    }
}