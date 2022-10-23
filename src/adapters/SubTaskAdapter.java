package adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import tasks.Status;
import tasks.SubTask;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SubTaskAdapter extends TypeAdapter<SubTask> {

    static final DateTimeFormatter formatterWriter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
    static final DateTimeFormatter formatterReader = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");

    @Override
    public void write(JsonWriter jsonWriter, SubTask subTask) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("id");
        jsonWriter.value(subTask.getId());
        jsonWriter.name("name");
        jsonWriter.value(subTask.getName());
        jsonWriter.name("description");
        jsonWriter.value(subTask.getDescription());
        jsonWriter.name("status");
        jsonWriter.value(String.valueOf(subTask.getStatus()));
        jsonWriter.name("startTime");
        jsonWriter.value(subTask.getStartTime().format(formatterWriter));
        jsonWriter.name("duration");
        jsonWriter.value(subTask.getDuration().toMinutes());
        jsonWriter.name("epicId");
        jsonWriter.value(subTask.getEpicId());
        jsonWriter.endObject();
    }


    @Override
    public SubTask read(JsonReader jsonReader) throws IOException {
        SubTask subTask = new SubTask();
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
                subTask.setId(Integer.parseInt(jsonReader.nextString()));
            }

            if ("name".equals(fieldname)) {
                //move to next token
                token = jsonReader.peek();
                subTask.setName(String.valueOf(jsonReader.nextString()));
            }

            if ("description".equals(fieldname)) {
                //move to next token
                token = jsonReader.peek();
                subTask.setDescription(String.valueOf(jsonReader.nextString()));
            }

            if ("status".equals(fieldname)) {
                //move to next token
                token = jsonReader.peek();
                subTask.setStatus(Status.valueOf((String.valueOf(jsonReader.nextString()))));
            }

            if ("startTime".equals(fieldname)) {
                //move to next token
                token = jsonReader.peek();
                subTask.setStartTime(LocalDateTime.parse(jsonReader.nextString(), formatterReader));
            }

            if ("duration".equals(fieldname)) {
                //move to next token
                token = jsonReader.peek();
                subTask.setDuration(Duration.ofMinutes(Long.parseLong(String.valueOf(jsonReader.nextInt()))));
            }

            if ("epicId".equals(fieldname)) {
                //move to next token
                token = jsonReader.peek();
                subTask.setEpicId(Integer.parseInt(jsonReader.nextString()));
            }
        }
        jsonReader.endObject();
        return subTask;
    }
}
