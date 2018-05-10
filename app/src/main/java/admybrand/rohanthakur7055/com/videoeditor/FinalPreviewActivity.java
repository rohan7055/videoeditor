package admybrand.rohanthakur7055.com.videoeditor;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.afollestad.easyvideoplayer.EasyVideoCallback;
import com.afollestad.easyvideoplayer.EasyVideoPlayer;

public class FinalPreviewActivity extends AppCompatActivity {
    EasyVideoPlayer easyVideoPlayer;
    private String path,duration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_preview);
        easyVideoPlayer = (EasyVideoPlayer) findViewById(R.id.player);
        path = getIntent().getStringExtra("path");
        duration = getIntent().getStringExtra("duration");
        easyVideoPlayer.setSource(Uri.parse(path));
        easyVideoPlayer.setAutoPlay(true);
        easyVideoPlayer.setCallback(new EasyVideoCallback() {
            @Override
            public void onStarted(EasyVideoPlayer player) {


            }

            @Override
            public void onPaused(EasyVideoPlayer player) {

            }

            @Override
            public void onPreparing(EasyVideoPlayer player) {

            }

            @Override
            public void onPrepared(EasyVideoPlayer player) {

            }

            @Override
            public void onBuffering(int percent) {

            }

            @Override
            public void onError(EasyVideoPlayer player, Exception e) {

            }

            @Override
            public void onCompletion(EasyVideoPlayer player) {
                easyVideoPlayer.hideControls();

            }

            @Override
            public void onRetry(EasyVideoPlayer player, Uri source) {

            }

            @Override
            public void onSubmit(EasyVideoPlayer player, Uri source) {

            }
        });



    }
}
