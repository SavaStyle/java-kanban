package Adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import tasks.Epic;
import tasks.Status;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EpicAdapter extends TypeAdapter<Epic> {

    static final DateTimeFormatter formatterWriter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
    static final DateTimeFormatter formatterReader = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");

    @Override
    public void write(JsonWriter jsonWriter, Epic epic) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("id");
        jsonWriter.value(epic.getId());
        jsonWriter.name("name");
        jsonWriter.value(epic.getName());
        jsonWriter.name("description");
        jsonWriter.value(epic.getDescription());
        jsonWriter.name("status");
        jsonWriter.value(String.valueOf(epic.getStatus()));
        jsonWriter.name("startTime");
        if (epic.getStartTime() != null) {
            jsonWriter.value(epic.getStartTime().format(formatterWriter));
        } else {
            jsonWriter.value((String) null);
        }
        jsonWriter.name("duration");
        if (epic.getDuration() != null) {
            jsonWriter.value(epic.getDuration().toMinutes());
        } else {
            jsonWriter.value((String) null);
        }
        jsonWriter.endObject();
    }


    @Override
    public Epic read(JsonReader jsonReader) throws IOException {
        Epic epic = new Epic();
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
                epic.setId(Integer.parseInt(jsonReader.nextString()));
            }

            if ("name".equals(fieldname)) {
                //move to next token
                token = jsonReader.peek();
                epic.setName(String.valueOf(jsonReader.nextString()));
            }

            if ("description".equals(fieldname)) {
                //move to next token
                token = jsonReader.peek();
                epic.setDescription(String.valueOf(jsonReader.nextString()));
            }

            if ("status".equals(fieldname)) {
                //move to next token
                token = jsonReader.peek();
                epic.setStatus(Status.valueOf((String.valueOf(jsonReader.nextString()))));
            }

            if ("startTime".equals(fieldname)) {
                //move to next token
                token = jsonReader.peek();
                if (jsonReader.nextString() == (null)) {
                    continue;
                } else {
                    epic.setStartTime(LocalDateTime.parse(jsonReader.nextString(), formatterReader));
                }
            }

            if ("duration".equals(fieldname)) {
                //move to next token
                token = jsonReader.peek();
                if (jsonReader.nextString() == (null)) {
                    continue;
                } else {
                    epic.setDuration(Duration.ofMinutes(Long.parseLong(String.valueOf(jsonReader.nextInt()))));
                }
            }
        }
        jsonReader.endObject();
        return epic;
    }
}
