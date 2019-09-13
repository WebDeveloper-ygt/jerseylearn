package com.learn.jersey.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.util.Calendar;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import com.learn.jersey.model.MyDate;
import com.learn.jersey.model.ZonedDateTimeModel;

@Provider
public class MyDateParamConvertor implements ParamConverterProvider {

	@Override
	public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
		if(rawType.getName().equals(MyDate.class.getName())){
			return new ParamConverter<T>() {

				@Override
				public T fromString(String value) {
					Calendar calendar = Calendar.getInstance();
					if(value.equalsIgnoreCase("tomorrow")){
						calendar.add(Calendar.DATE, 1);
					}else if (value.equalsIgnoreCase("yesterday")) {
						calendar.add(Calendar.DATE, -1);
					}
					
					MyDate date = new MyDate();
					date.setDate(calendar.get(Calendar.DAY_OF_WEEK));
					date.setMonth(calendar.get(Calendar.MONTH));
					date.setYear(calendar.get(Calendar.YEAR));
					
					return rawType.cast(date);
				}

				@Override
				public String toString(T value) {
					return (value == null)? null : value.toString();
				}
				
			};
		}else if (rawType.getName().equals(ZonedDateTimeModel.class.getName())) {
			return new ParamConverter<T>() {

				@Override
				public T fromString(String value) {
					ZonedDateTime dateTime = ZonedDateTime.now();
					ZonedDateTimeModel model = new ZonedDateTimeModel();
					if(value.equalsIgnoreCase("tomorrow")){
						dateTime = dateTime.plusDays(1);
						
					}else if (value.equalsIgnoreCase("yesterday")) {
						dateTime = dateTime.minusDays(1);
						
					}
					
					model.setInput(value);
					model.setReq_day(dateTime.getDayOfWeek().toString().toLowerCase());
					model.setReq_month(dateTime.getMonth().toString().toLowerCase());
					model.setReq_year(dateTime.getYear());
					model.setReq_time(dateTime.getHour() + ":"+ dateTime.getMinute() + ":" + dateTime.getSecond() + " " + dateTime.getZone());
					
					return rawType.cast(model);
				}

				@Override
				public String toString(T value) {
					return (value == null)? null : value.toString();
				}
				
			};
		}
		return null;
	}

}
