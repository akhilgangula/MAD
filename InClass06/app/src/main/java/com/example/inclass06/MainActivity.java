package com.example.inclass06;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    private final String TASK_RESULT = "task_result";
    private final int DEFAULT_SEEKBAR_POS = 8;
    private final int THREAD_COUNT = 2;
    List<Double> resultSet = new ArrayList<>();
    ProgressBar progressBar;
    ArrayAdapter<Double> adapter;
    TextView progressStatusLabel;
    SeekBar seekBar;
    ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREAD_COUNT);
    AtomicInteger counter = new AtomicInteger(0);
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView seekBarIndicator = findViewById(R.id.seek_bar_status);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setProgress(DEFAULT_SEEKBAR_POS);
        progressStatusLabel = findViewById(R.id.progress_status_label);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(DEFAULT_SEEKBAR_POS);
        ListView listView = findViewById(R.id.result_list_view);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, resultSet);
        listView.setAdapter(adapter);

        Handler handler = new Handler(message -> {
            progressBar.setProgress(counter.incrementAndGet());
            resultSet.add(message.getData().getDouble(TASK_RESULT));
            progressStatusLabel.setText(counter.get() + "/" + seekBar.getProgress());
            ((TextView)findViewById(R.id.average_display)).setText(resultSet.stream().mapToDouble(x -> x).average().getAsDouble() + "");
            adapter.notifyDataSetChanged();
            if(resultSet.size() == seekBar.getProgress()) {
                findViewById(R.id.generate_btn).setEnabled(true);
                seekBar.setEnabled(true);
            }
            return false;
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBarIndicator.setText(i + " times");
                progressBar.setMax(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        findViewById(R.id.generate_btn).setOnClickListener(view -> {
            findViewById(R.id.generate_btn).setEnabled(false);
            ((TextView)findViewById(R.id.average_display)).setText("0");
            seekBar.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            progressStatusLabel.setVisibility(View.VISIBLE);
            findViewById(R.id.textView4).setVisibility(View.VISIBLE);
            resetStatus();
            int count = seekBar.getProgress();
            if(count > 0) {
                for(int i=0;i<count;i++) {
                    poolExecutor.execute(() -> {
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putDouble(TASK_RESULT, HeavyWork.getNumber());
                        message.setData(bundle);
                        handler.sendMessage(message);
                    });
                }

            }
            else {
                Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void resetStatus() {
        resultSet.clear();
        adapter.notifyDataSetChanged();

        progressBar.setProgress(0);
        counter.set(0);
        progressStatusLabel.setText(0+"/"+seekBar.getProgress());

    }
}