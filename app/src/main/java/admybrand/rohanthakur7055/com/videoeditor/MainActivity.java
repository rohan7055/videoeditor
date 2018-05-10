package admybrand.rohanthakur7055.com.videoeditor;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialcamera.MaterialCamera;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    protected String dstMediaPath = null;
    protected int videoWidthOut = 640;
    protected int videoHeightOut = 480;
    protected final String videoMimeType = "video/avc";
    protected int videoBitRateInKBytes = 6000;
    protected final int videoFrameRate = 30;
    protected final int videoIFrameInterval = 1;
    protected final String audioMimeType = "audio/mp4a-latm";
    protected final int audioBitRate = 44100;//96 * 1024;
    private boolean isStopped = false;
    ProgressBar progressBar;
    TextView message;
    Button compress;
    int ACTION;
    int DURATION=60;
    Button capture;
    private final static int CAMERA_RQ = 6969;
    File saveFolder = new File(Environment.getExternalStorageDirectory(), "VideoEditingApp/Videos");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        capture = (Button)findViewById(R.id.capture);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialCamera(MainActivity.this)
                        .primaryColorAttr(R.attr.md_divider_color)
                        .saveDir(saveFolder)
                        .showPortraitWarning(false)
                        .iconRecord(R.drawable.mcam_action_capture)
                        .iconStop(R.drawable.mcam_action_stop)
                        .iconFrontCamera(R.drawable.mcam_camera_front)
                        .iconRearCamera(R.drawable.mcam_camera_rear)
                        .iconPlay(R.drawable.evp_action_play)
                        .iconPause(R.drawable.evp_action_pause)
                        .iconRestart(R.drawable.evp_action_restart)
                        .qualityProfile(MaterialCamera.QUALITY_480P)
                        .videoEncodingBitRate(1714000)
                        .audioEncodingBitRate(95000)
                        .videoPreferredAspect(4f/3f)
                        .countdownMinutes(0.5f)
                        .start(CAMERA_RQ);


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_RQ && resultCode == RESULT_OK) {
            Intent intent = new Intent(MainActivity.this, EditVideoActivity.class);
            intent.putExtra("path", data.getDataString().substring(7,data.getDataString().length()));
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(data.getDataString().substring(7,data.getDataString().length()));
            String duration = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            int intduration = (Integer.parseInt(duration)) / 1000;
            intent.putExtra("duration", "" + intduration);
            startActivity(intent);
            //Log.e("File", "" + data.getStringExtra(CameraConfiguration.Arguments.FILE_PATH));
            //Toast.makeText(this, data.getDataString().substring(7,data.getDataString().length()), Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, saveFolder.getPath(), Toast.LENGTH_SHORT).show();

        }
    }
}
