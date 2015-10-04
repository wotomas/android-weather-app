package com.lohasfarm.stormy.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lohasfarm.stormy.fragment.AlertDialogFragment;
import com.lohasfarm.stormy.R;
import com.lohasfarm.stormy.unit.CurrentWeather;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private String _url = "https://api.forecast.io/forecast/";
    private String _apiKey = "4a219dda8090253662ec9e70877d8e1b";
    private double _laditude = 22.15;
    private double _longitude = 114.10;

    private CurrentWeather _currentWeather;

    private TextView _country;
    private TextView _temperature;
    private TextView _humidity;
    private TextView _rainPercentage;
    private TextView _summary;

    private ImageView _icon;
    private ImageView _refresh;

    private RelativeLayout _background;
    private ProgressBar _progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _country = (TextView) findViewById(R.id.countryLabel);
        _temperature = (TextView) findViewById(R.id.temperatureLabel);
        _humidity = (TextView) findViewById(R.id.humidityLabel);
        _rainPercentage = (TextView) findViewById(R.id.rainLabel);
        _summary = (TextView) findViewById(R.id.summaryLabel);
        _refresh =(ImageView) findViewById(R.id.refreshImageView);
        _background = (RelativeLayout) findViewById(R.id.background);
        _icon = (ImageView) findViewById(R.id.iconImageView);
        _progress = (ProgressBar) findViewById(R.id.progressBar);

        _refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForecast();
            }
        });

        getForecast();

    }

    private void getForecast() {
        String forecastUrl = _url + _apiKey + "/" + _laditude + "," + _longitude;
        Log.d(TAG, "The Forcast URL is: " + forecastUrl);

        if(isNetworkAvaiable()) {
            toggleRefresh();

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder().url(forecastUrl).build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });

                    alaertUserAboutError();

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });

                    try {
                        String jsonData = response.body().string();

                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            _currentWeather = getCurrentDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    _country.setText(_currentWeather.get_timezone());
                                    _temperature.setText(Integer.toString((int) _currentWeather.get_temperature()));
                                    _humidity.setText(Integer.toString((int) (100 * _currentWeather.get_humidity())) + " %");
                                    _rainPercentage.setText(Integer.toString((int) (100 * _currentWeather.get_precipicationChance())) + " %");
                                    _summary.setText(_currentWeather.get_summary());

                                    _icon.setImageResource(_currentWeather.getIconId());

                                    _background.setBackgroundColor(getResources().getColor(_currentWeather.getBackgroundId()));
                                }
                            });
                        } else {
                            alaertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception Caught: ", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "Exception Caught: ", e);
                    }
                }
            });
        } else {
            Toast.makeText(this, "Network is unavailable!", Toast.LENGTH_LONG).show();
        }
    }

    private void toggleRefresh() {
        if(_progress.getVisibility() == View.INVISIBLE) {
            _progress.setVisibility(View.VISIBLE);
            _refresh.setVisibility(View.INVISIBLE);
        } else {
            _progress.setVisibility(View.INVISIBLE);
            _refresh.setVisibility(View.VISIBLE);
        }

    }

    private CurrentWeather getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        /*
        private String _icon;
        private long _time;
        private double _temperature;
        private double _humidity;
        private double _precipicationChance;
        private String _summary;
         */
        String timezone = forecast.getString("timezone");
        JSONObject currently = forecast.getJSONObject("currently");

        CurrentWeather currentWeather = new CurrentWeather();

        currentWeather.set_humidity(currently.getDouble("humidity"));
        currentWeather.set_icon(currently.getString("icon"));
        currentWeather.set_precipicationChance(currently.getDouble("precipProbability"));
        currentWeather.set_summary(currently.getString("summary"));
        currentWeather.set_temperature(currently.getDouble("temperature"));
        currentWeather.set_timezone(timezone);

        return currentWeather;

    }

    private boolean isNetworkAvaiable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if(networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        } else {
            isAvailable = false;
        }

        return isAvailable;
    }

    private void alaertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "Error_Dialog");
    }
}
