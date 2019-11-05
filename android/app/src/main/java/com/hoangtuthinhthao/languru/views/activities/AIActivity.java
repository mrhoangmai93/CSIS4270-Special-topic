package com.hoangtuthinhthao.languru.views.activities;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
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
import com.hoangtuthinhthao.languru.views.activities.helper.InternetCheck;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.util.List;

import dmax.dialog.SpotsDialog;

public class AIActivity extends AppCompatActivity {
    CameraView cameraView;
    Button btnDetect;
    AlertDialog waitingDialog;

    CameraSource cameraSource;
    final int RequestCameraPermissionId = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai);

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


}
