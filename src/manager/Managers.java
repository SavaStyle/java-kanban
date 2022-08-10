package manager;

import tasks.Epic;
import tasks.SimpleTask;
import tasks.SubTask;

import java.util.HashMap;

public class Managers {

    static TaskManager getDefault() {

        TaskManager manager = new TaskManager() {
            @Override
            public int getNextId() {
                return 0;
            }

            @Override
            public HashMap printSimpleTask() {
                return null;
            }

            @Override
            public HashMap printEpicTask() {
                return null;
            }

            @Override
            public HashMap printSubTasks() {
                return null;
            }

            @Override
            public void cleanSimpleTask() {

            }

            @Override
            public void cleanEpicTask() {

            }

            @Override
            public void cleanSubTask() {

            }

            @Override
            public SimpleTask getSimpleTaskById(int id) {
                return null;
            }

            @Override
            public Epic getEpicById(int id) {
                return null;
            }

            @Override
            public SubTask getSubTaskById(int id) {
                return null;
            }

            @Override
            public void addST(SimpleTask task) {

            }

            @Override
            public void addET(Epic task) {

            }

            @Override
            public void addSET(SubTask task) {

            }

            @Override
            public void updateSimpleTask(SimpleTask task) {

            }

            @Override
            public void updateEpicTask(Epic task) {

            }

            @Override
            public void updateSubTask(SubTask task) {

            }

            @Override
            public void deleteSimpleTaskById(int id) {

            }

            @Override
            public void deleteSubTaskById(int id) {

            }

            @Override
            public void deleteEpicById(int id) {

            }

            @Override
            public Object getListSubTasks(int id) {
                return null;
            }

            @Override
            public void updateEpicStatus(Epic task) {

            }
        };

        return manager;
    }

    static InMemoryHistoryManager  getDefaultHistory() {

        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

        return  inMemoryHistoryManager;
    }

}
