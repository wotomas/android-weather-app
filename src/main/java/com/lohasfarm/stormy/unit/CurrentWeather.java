package com.lohasfarm.stormy.unit;

import com.lohasfarm.stormy.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Jih on 10/4/2015.
 */
public class CurrentWeather {
    private String _icon;
    private double _temperature;
    private double _humidity;
    private double _precipicationChance;
    private String _summary;

    public String get_timezone() {
        return _timezone;
    }

    public void set_timezone(String _timezone) {
        this._timezone = _timezone;
    }

    private String _timezone;


    public String get_summary() {
        return _summary;
    }

    public void set_summary(String _summary) {
        this._summary = _summary;
    }

    public String get_icon() {
        return _icon;
    }

    public void set_icon(String _icon) {
        this._icon = _icon;
    }

    public int getBackgroundId(){
        int backgroundId = R.color.initialBackground;

        if(_temperature < 50){
            backgroundId = R.color.coldDay;
        } else if(_temperature > 99){
            backgroundId = R.color.hotDay;
        } else {
            backgroundId = R.color.averageDay;
        }

        return backgroundId;
    }
    public int getIconId(){
        //clear-day, clear-night, rain, snow, sleet, wind, fog, cloudy, partly-cloudy-day, or partly-cloudy-night
        int iconId = R.drawable.clear_day;

        if (_icon.equals("clear-day")) {
            iconId = R.drawable.clear_day;
        }
        else if (_icon.equals("clear-night")) {
            iconId = R.drawable.clear_night;
        }
        else if (_icon.equals("rain")) {
            iconId = R.drawable.rain;
        }
        else if (_icon.equals("snow")) {
            iconId = R.drawable.snow;
        }
        else if (_icon.equals("sleet")) {
            iconId = R.drawable.sleet;
        }
        else if (_icon.equals("wind")) {
            iconId = R.drawable.wind;
        }
        else if (_icon.equals("fog")) {
            iconId = R.drawable.fog;
        }
        else if (_icon.equals("cloudy")) {
            iconId = R.drawable.cloudy;
        }
        else if (_icon.equals("partly-cloudy-day")) {
            iconId = R.drawable.partly_cloudy;
        }
        else if (_icon.equals("partly-cloudy-night")) {
            iconId = R.drawable.cloudy_night;
        }

        return iconId;
    }


    public double get_temperature() {
        return _temperature;
    }

    public void set_temperature(double _temperature) {
        this._temperature = _temperature;
    }

    public double get_humidity() {
        return _humidity;
    }

    public void set_humidity(double _humidity) {
        this._humidity = _humidity;
    }

    public double get_precipicationChance() {
        return _precipicationChance;
    }

    public void set_precipicationChance(double _precipicationChance) {
        this._precipicationChance = _precipicationChance;
    }
}
