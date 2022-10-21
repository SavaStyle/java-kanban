package KVServer;

import Adapters.EpicAdapter;
import Adapters.SimpleTaskAdapter;
import Adapters.SubTaskAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import manager.TaskManager;
import tasks.Epic;
import tasks.SimpleTask;
import tasks.SubTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpTaskServer {

    public static final int PORT = 8080;

    TaskManager manager;
    Gson gson = getGson();

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(SimpleTask.class, new SimpleTaskAdapter());
        gsonBuilder.registerTypeAdapter(SubTask.class, new SubTaskAdapter());
        gsonBuilder.registerTypeAdapter(Epic.class, new EpicAdapter());
        return gsonBuilder.create();
    }

    HttpServer server;

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.manager = taskManager;
        server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/tasks/", this::handleUsers);
    }


    private void handleUsers(HttpExchange httpExchange) {
        try {
            System.out.println("/tasks/ " + httpExchange.getRequestURI());
            String requestMethod = httpExchange.getRequestMethod();
            String path = httpExchange.getRequestURI().getPath();
            switch (requestMethod) {
                case "GET": {
                    if (Pattern.matches("^/tasks/task$", path)) {
                        String response = gson.toJson(manager.getSimpleTask());
                        sendText(httpExchange, response);
                        return;
                    }
                    if (Pattern.matches("^/tasks/epic$", path)) {
                        String response = gson.toJson(manager.getEpicTask());
                        sendText(httpExchange, response);
                        return;
                    }
                    if (Pattern.matches("^/tasks/subtask$", path)) {
                        String response = gson.toJson(manager.getSubTasks());
                        sendText(httpExchange, response);
                        return;
                    }

                    if (Pattern.matches("^/tasks/task/\\d+$", path)) {
                        int id = parsePathId(path.replaceFirst("/tasks/task/", ""));
                        if (id != -1) {
                            String response = gson.toJson(manager.getSimpleTaskById(id));
                            sendText(httpExchange, response);
                            return;
                        }
                    }
                    if (Pattern.matches("^/tasks/epic/\\d+$", path)) {
                        int id = parsePathId(path.replaceFirst("/tasks/epic/", ""));
                        if (id != -1) {
                            String response = gson.toJson(manager.getEpicById(id));
                            sendText(httpExchange, response);
                            return;
                        }
                    }
                    if (Pattern.matches("^/tasks/subtask/\\d+$", path)) {
                        int id = parsePathId(path.replaceFirst("/tasks/subtask/", ""));
                        if (id != -1) {
                            String response = gson.toJson(manager.getSubTaskById(id));
                            sendText(httpExchange, response);
                            return;
                        }
                    }

                    if (Pattern.matches("^/tasks/history$", path)) {
                        String response = gson.toJson(manager.history());
                        sendText(httpExchange, response);
                        return;
                    }
                    break;
                }
                case "POST": {
                    if (Pattern.matches("/tasks/task", path)) {
                        String body = readText(httpExchange);
                        SimpleTask task = gson.fromJson(body, SimpleTask.class);
                        int id = task.getId();
                        manager.addSimpleTask(task);
                        System.out.println("Добавили задачу с id = " + id);
                        httpExchange.sendResponseHeaders(200, 0);
                        return;
                    }
                    if (Pattern.matches("/tasks/epic", path)) {
                        String body = readText(httpExchange);
                        Epic task = gson.fromJson(body, Epic.class);
                        int id = task.getId();
                        manager.addEpicTask(task);
                        System.out.println("Добавили задачу с id = " + id);
                        httpExchange.sendResponseHeaders(200, 0);
                        return;
                    }
                    if (Pattern.matches("/tasks/subtask", path)) {
                        String body = readText(httpExchange);
                        SubTask task = gson.fromJson(body, SubTask.class);
                        int id = task.getId();
                        manager.addSubEpicTask(task);
                        System.out.println("Добавили задачу с id = " + id);
                        httpExchange.sendResponseHeaders(200, 0);
                        return;
                    }
                    break;
                }
                case "DELETE": {
                    if (Pattern.matches("^/tasks/task/\\d+$", path)) {
                        int id = parsePathId(path.replaceFirst("/tasks/task/", ""));
                        if (id != -1) {
                            System.out.println("Задача с ID : " + id + "удалена.");
                            manager.deleteSimpleTaskById(id);
                            httpExchange.sendResponseHeaders(200, 0);
                            return;
                        }
                    }
                    if (Pattern.matches("^/tasks/epic/\\d+$", path)) {
                        int id = parsePathId(path.replaceFirst("/tasks/epic/", ""));
                        if (id != -1) {
                            System.out.println("Задача с ID : " + id + "удалена.");
                            manager.deleteEpicById(id);
                            httpExchange.sendResponseHeaders(200, 0);
                            return;
                        }
                    }
                    if (Pattern.matches("^/tasks/subtask/\\d+$", path)) {
                        int id = parsePathId(path.replaceFirst("/tasks/subtask/", ""));
                        if (id != -1) {
                            System.out.println("Задача с ID : " + id + "удалена.");
                            manager.deleteSubTaskById(id);
                            httpExchange.sendResponseHeaders(200, 0);
                            return;
                        }
                    }
                }
                default: {
                    System.out.println("Обработка запроса " + requestMethod + " не поддерживается.");
                    httpExchange.sendResponseHeaders(405, 0);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            httpExchange.close();
        }
    }

    private int parsePathId(String pathId) {
        try {
            return Integer.parseInt(pathId);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void start() {
        System.out.println("Started HttpTaskServer " + PORT);
        System.out.println("http://localhost:" + PORT + "/tasks");
        server.start();
    }

    public void stop() {
        server.stop(0);
        System.out.println("Остановили сервер на порту " + PORT);
    }

    private String readText(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(), UTF_8);
    }

    private void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
    }
}
