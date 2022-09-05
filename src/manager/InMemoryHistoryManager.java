package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private final Map<Integer, Node> nodeMap = new HashMap<>();
    private Node first;
    private Node last;

    // добавление ноды в мапу
    @Override
    public void add(Task task) {
        remove(task.getId());
        Node newNode = linkLast(task);
        nodeMap.put(task.getId(), newNode);
    }

    // добавление ноды в список
    public Node linkLast(Task task) {
        Node node = new Node(task, null, last);
        if (first == null) {
            first = node;
        } else {
            last.next = node;
        }
        last = node;
        return node;
    }

    // удаление ноды из мапы
    @Override
    public void remove(Integer id) {
        Node node = nodeMap.remove(id);
        removeNode(node);
    }

    // удаление ноды из списка
    public void removeNode(Node node) {
        if (node == null) {
            return;
        }
        if (node.getPrev() != null) {
            node.prev.next = node.getNext();
            if (node.getNext() == null) {
                last = node.getPrev();
            } else {
                node.next.prev = node.getPrev();
            }
        } else {
            first = node.next;
            if (first == null) {
                last = null;
            } else {
                first.prev = null;
            }
        }
    }

    //история задач
    public List<Task> getHistory() {
        List<Task> history = new ArrayList<>();
        Node node = first;
        if (node != null) {
            history.add(node.getTask());
            while (node.next != null) {
                node = node.next;
                history.add(node.getTask());
            }
        } else {
            return history;
        }
        return history;
    }
}