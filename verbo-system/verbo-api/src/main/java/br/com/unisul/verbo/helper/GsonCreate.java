package br.com.unisul.verbo.helper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

public class GsonCreate {

	private GsonCreate() {
		super();
	}

	public static Gson register() {

		return new GsonBuilder()
				.registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (json, typeOfSrc, context) ->

				new JsonPrimitive(json.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))

				).registerTypeAdapter(LocalDateTime.class,
						(JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) ->

						LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME)

				).registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (json, typeOfSrc, context) ->

				new JsonPrimitive(json.format(DateTimeFormatter.ISO_LOCAL_DATE))

				).registerTypeAdapter(LocalDate.class,
						(JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) ->

						LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE)

				).registerTypeAdapter(ZonedDateTime.class, (JsonSerializer<ZonedDateTime>) (json, typeOfSrc, context) ->

				new JsonPrimitive(json.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))

				).registerTypeAdapter(ZonedDateTime.class,
						(JsonDeserializer<ZonedDateTime>) (json, type, jsonDeserializationContext) ->

						ZonedDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_OFFSET_DATE_TIME)

				).create();
	}
}
