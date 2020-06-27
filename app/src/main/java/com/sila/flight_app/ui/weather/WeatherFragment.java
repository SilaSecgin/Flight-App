package com.sila.flight_app.ui.weather;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sila.flight_app.R;
import com.sila.flight_app.ui.weather.WeatherModel.GetWeather;

import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

public class WeatherFragment extends Fragment {


    private String url;
    public GetWeather model;
    public TextView city, temp, tempMin, tempMax, windSpeed, humidity, pressure, visibility, sunrise, sunset;
    ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_weather, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Yükleniyor...");
        progressDialog.show();

        city = root.findViewById(R.id.location);
        temp = root.findViewById(R.id.temp);
        tempMin = root.findViewById(R.id.tempMin);
        tempMax = root.findViewById(R.id.tempMax);
        windSpeed = root.findViewById(R.id.windSpeed);
        humidity = root.findViewById(R.id.humidity);
        pressure = root.findViewById(R.id.pressure);
        visibility = root.findViewById(R.id.visibility);
        sunrise = root.findViewById(R.id.sunrise);
        sunset = root.findViewById(R.id.sunset);

        sendReq();


        return root;

    }

    private String getUrl() {

        url = "https://api.openweathermap.org/data/2.5/weather?q=istanbul&appid=405bf69d477427b5faebf0a9bef0fccb&lang=tr&units=metric";
        return url;
    }

    private void sendReq() {
        AsyncHttpClient client = new AsyncHttpClient();
        url = getUrl();
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //epochhh uygulaması saate cevırır.

                model = new Gson().fromJson(response.toString(), GetWeather.class);
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm a");

                long sunriseTsSecond = model.getSys().getSunrise();
                long sunriseTsMilisecond = sunriseTsSecond * 1000;
                Timestamp sunriseTs = new Timestamp(sunriseTsMilisecond);
                Date sunriseDate = new Date(sunriseTs.getTime() + TimeUnit.HOURS.toMillis(3));

                long sunsetTsSecond = model.getSys().getSunset();
                long sunsetTsMilisecond = sunsetTsSecond * 1000;
                Timestamp sunsetTs = new Timestamp(sunsetTsMilisecond);
                Date sunsetDate = new Date(sunsetTs.getTime() + TimeUnit.HOURS.toMillis(3));

                city.setText(model.getName());
                temp.setText((int) Double.parseDouble(model.getMain().getTemp().toString()) + "°C");
                tempMin.setText(" Min Sıcaklık " + model.getMain().getTempMin().toString() + "°C");
                tempMax.setText(" Max Sıcaklık " + model.getMain().getTempMax().toString() + "°C");
                windSpeed.setText(model.getWind().getSpeed().toString() + "km/s");
                humidity.setText("%" + model.getMain().getHumidity().toString());
                pressure.setText(model.getMain().getPressure().toString());
                visibility.setText((Integer.parseInt(model.getVisibility().toString()) / 1000) + "km");
                sunrise.setText(formatter.format(sunriseDate));
                sunset.setText(formatter.format(sunsetDate));

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Bir sorun oluştu.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}