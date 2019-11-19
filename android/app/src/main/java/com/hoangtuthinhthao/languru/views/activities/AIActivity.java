package com.hoangtuthinhthao.languru.views.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.CameraSource;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions;
import com.google.firebase.ml.vision.cloud.label.FirebaseVisionCloudLabel;
import com.google.firebase.ml.vision.cloud.label.FirebaseVisionCloudLabelDetector;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetector;
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetectorOptions;
import com.hoangtuthinhthao.languru.R;
import com.hoangtuthinhthao.languru.controllers.authentication.AuthHelpers;
import com.hoangtuthinhthao.languru.views.activities.helper.InternetCheck;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.util.List;

import dmax.dialog.SpotsDialog;

import static com.hoangtuthinhthao.languru.views.activities.LoginActivity.apiAuthInterface;
import static com.hoangtuthinhthao.languru.views.activities.MainActivity.sessionControl;

public class AIActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    CameraView cameraView;
    Button btnDetect;
    AlertDialog waitingDialog;
    // drawer Layout
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle t;
    CameraSource cameraSource;
    final int RequestCameraPermissionId = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai);
        /*
         * BEGIN NAVIGATION DRAWER
         */
        drawerLayout = findViewById(R.id.drawer_layout);
        t = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);

        drawerLayout.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        /*
         * END NAVIGATION DRAWER
         */
        cameraView = (CameraView) findViewById(R.id.camera_view);
        btnDetect = (Button) findViewById(R.id.btn_detect);
        waitingDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Please wait...")
                .setCancelable(false).build();


        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                waitingDialog.show();
                Bitmap bitmap = cameraKitImage.getBitmap();
                bitmap = Bitmap.createScaledBitmap(bitmap, cameraView.getWidth(), cameraView.getHeight(), false);
                cameraView.stop();

                runDetector(bitmap);
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });
        btnDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.start();
                cameraView.captureImage();
            }
        });
    }

    private void runDetector(Bitmap bitmap) {
        final FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
        new InternetCheck(new InternetCheck.Consumer() {
            @Override
            public void accept(boolean internet) {
                if (internet) {
                    //if connected to internet , using cloud
                    FirebaseVisionCloudDetectorOptions options =
                            new FirebaseVisionCloudDetectorOptions.Builder().setMaxResults(1).build();
                    FirebaseVisionCloudLabelDetector detector =
                            FirebaseVision.getInstance().getVisionCloudLabelDetector(options);
                    detector.detectInImage(image)
                            .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionCloudLabel>>() {
                                @Override
                                public void onSuccess(List<FirebaseVisionCloudLabel> firebaseVisionCloudLabels) {
                                    processDataResultCloud(firebaseVisionCloudLabels);

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("EDMTERROR", e.getMessage());
                                }
                            });
                } else {
                    FirebaseVisionLabelDetectorOptions options =
                            new FirebaseVisionLabelDetectorOptions.Builder().setConfidenceThreshold(0.8f).build();
                    FirebaseVisionLabelDetector detector =
                            FirebaseVision.getInstance().getVisionLabelDetector(options);
                    detector.detectInImage(image)
                            .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionLabel>>() {
                                @Override
                                public void onSuccess(List<FirebaseVisionLabel> firebaseVisionLabels) {
                                    processDataResult(firebaseVisionLabels);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("ERROR", e.getMessage());
                                }
                            });
                }
            }
        });
    }

    private void processDataResultCloud(List<FirebaseVisionCloudLabel> firebaseVisionCloudLabels) {
        for (FirebaseVisionCloudLabel label : firebaseVisionCloudLabels) {
            Toast.makeText(this, "cloud result is ....." + label.getLabel(), Toast.LENGTH_SHORT).show();
        }
        if (waitingDialog.isShowing())
            waitingDialog.dismiss();
    }

    private void processDataResult(List<FirebaseVisionLabel> firebaseVisionLabels) {
        for (FirebaseVisionLabel label : firebaseVisionLabels) {
            Toast.makeText(this, "Device result is......" + label.getLabel(), Toast.LENGTH_SHORT).show();
        }
        if (waitingDialog.isShowing())
            waitingDialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.logout:
                sessionControl.setJwtToken(null);
                AuthHelpers.logoutUser(this, apiAuthInterface, sessionControl.getRefreshToken());
                startActivity(new Intent(AIActivity.this, LoginActivity.class));
                break;
            case R.id.gameCenter:
                startActivity(new Intent(AIActivity.this, GameActivity.class));
                break;
            case R.id.translation:
                startActivity(new Intent(AIActivity.this, TranslationActivity.class));
                break;
            case R.id.topics:
                startActivity(new Intent(AIActivity.this, MainActivity.class));
                break;
        }

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
