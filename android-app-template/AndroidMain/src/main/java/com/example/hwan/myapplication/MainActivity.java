/*
 * MyAwesomeApp project template
 *
 * Distributed under no licences and no warranty.
 */
package com.example.hwan.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.hwan.myapplication.api.ApiService;
import com.example.hwan.myapplication.dto.CounterDto;
import com.example.hwan.myapplication.rx.SubscriberBuilder;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 22 - Aug - 2016
 */
public class MainActivity extends AppCompatActivity {
    @Inject
    public ApiService apiService;
    @BindView(R.id.textView)
    /*default*/ TextView textView;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApplication.getInstance().getDiComponent().inject(this);
        this.unbinder = ButterKnife.bind(this);

        textView.setText(R.string.hello);
        apiService.getCounter()
                .subscribe(new SubscriberBuilder<CounterDto>()
                        .onNext(new Action1<CounterDto>() {
                            @Override
                            public void call(CounterDto counterDto) {
                                System.out.println("onNext");
                                System.out.println("Counter   : " + counterDto.getCounter());
                                System.out.println("LastAccess: " + counterDto.getLastAccessed());
                            }
                        })
                        .onError(new Action1<Throwable>() {
                            @Override
                            public void call(Throwable t) {
                                System.out.println("onError");
                                t.printStackTrace();
                            }
                        })
                        .onCompleted(new Action1<Void>() {
                            @Override
                            public void call(Void aVoid) {
                                System.out.println("onCompleted");
                            }
                        }).build()
                );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
