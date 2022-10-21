package Adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import tasks.SimpleTask;
import tasks.Status;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimpleTaskAdapter extends TypeAdapter<SimpleTask> {

    static final DateTimeFormatter formatterWriter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
    static final DateTimeFormatter formatterReader = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");

    @Override
    public void write(JsonWriter jsonWriter, SimpleTask simpleTask) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("id");
        jsonWriter.value(simpleTask.getId());
        jsonWriter.name("name");
        jsonWriter.value(simpleTask.getName());
        jsonWriter.name("description");
        jsonWriter.value(simpleTask.getDescription());
        jsonWriter.name("status");
        jsonWriter.value(String.valueOf(simpleTask.getStatus()));
        jsonWriter.name("startTime");
        jsonWriter.value(simpleTask.getStartTime().format(formatterWriter));
        jsonWriter.name("duration");
        jsonWriter.value(simpleTask.getDuration().toMinutes());
        jsonWriter.endObject();
    }

    @Override
    public SimpleTask read(JsonReader jsonReader) throws IOException {
        SimpleTask simpleTask = new SimpleTask();
        jsonReader.beginObject();
        String fieldname = null;

        while (jsonReader.hasNext()) {
            JsonToken token = jsonReader.peek();

            if (token.equals(JsonToken.NAME)) {
                //get the current token
                fieldname = jsonReader.nextName();
            }

            if ("id".equals(fieldname)) {
                //move to next token
                token = jsonReader.peek();
                simpleTask.setId(Integer.parseInt(jsonReader.nextString()));
            }

            if ("name".equals(fieldname)) {
                //move to next token
                token = jsonReader.peek();
                simpleTask.setName(String.valueOf(jsonReader.nextString()));
            }

            if ("description".equals(fieldname)) {
                //move to next token
                token = jsonReader.peek();
                simpleTask.setDescription(String.valueOf(jsonReader.nextString()));
            }

            if ("status".equals(fieldname)) {
                //move to next token
                token = jsonReader.peek();
                simpleTask.setStatus(Status.valueOf((String.valueOf(jsonReader.nextString()))));
            }

            if ("startTime".equals(fieldname)) {
                //move to next token
                token = jsonReader.peek();
                simpleTask.setStartTime(LocalDateTime.parse(jsonReader.nextString(), formatterReader));
            }

            if ("duration".equals(fieldname)) {
                //move to next token
                token = jsonReader.peek();
                simpleTask.setDuration(Duration.ofMinutes(Long.parseLong(String.valueOf(jsonReader.nextInt()))));
            }
        }
        jsonReader.endObject();
        return simpleTask;
    }
}
