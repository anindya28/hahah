package com.example.dadudobel;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvDadu1;
    private TextView tvDadu2;
    private Button btStartStop;
    private Thread acakThread;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.tvDadu1 = findViewById(R.id.tvDadu1);
        this.tvDadu2 = findViewById(R.id.tvDadu2);
        this.btStartStop = findViewById(R.id.btStartStop);
        this.btStartStop.setOnClickListener(this);



        this.mainHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Bundle b = msg.getData();
                int a1 = b.getInt("acak1");
                int a2 = b.getInt("acak2");
                tvDadu1.setText(String.valueOf(a1));
                tvDadu2.setText(String.valueOf(a2));
            }
        };

        buatThread();
    }

    private void buatThread() {
        this.acakThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true){
                        int acak1 = (int) (Math.random() * 6 + 1);
                        int acak2 = (int) (Math.random() * 6 + 1);

                        Message m = mainHandler.obtainMessage();
                        Bundle b = new Bundle();
                        b.putInt("acak1", acak1);
                        b.putInt("acak2", acak2);
                        m.setData(b);
                        mainHandler.sendMessage(m);
                        Thread.sleep(100);
                    }
                } catch (Exception e){}
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (this.acakThread == null) this.buatThread();
        else if (this.acakThread.isInterrupted()) this.buatThread();
        else if (!this.acakThread.isAlive()) this.buatThread();

        if (this.acakThread.isAlive()) this.acakThread.interrupt();
        else this.acakThread.start();
    }
}