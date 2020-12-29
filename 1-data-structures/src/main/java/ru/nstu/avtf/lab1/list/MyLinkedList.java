package ru.nstu.avtf.lab1.list;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Класс для операций над связным списком
 *
 * @param <T> тип данных, хранимых в списке
 */
public class MyLinkedList<T extends Comparable<T>> implements Iterable<T> {
    /**
     * Число элементов в списке
     */
    private int size;

    /**
     * Начало списка
     */
    private Node<T> head;

    public MyLinkedList() {
    }

    /**
     * Добавить новый элемент в конец списка
     *
     * @param data данные
     */
    public void add(T data) {
        // если список пуст, создадим первый элемент
        if (head == null) {
            head = new Node<>(data);
            size++;
            return;
        }

        // создадим новый элемент
        Node<T> tmp = new Node<>(data);

        // пробежимся в конец списка
        Node<T> current = head;
        while (current.getNext() != null) {
            current = current.getNext();
        }

        // сделаем новый элемент следующим у последнего в списке
        current.setNext(tmp);

        // увеличим размер
        size++;
    }

    /**
     * Вставить новый элемент по индексу
     *
     * @param data  данные
     * @param index индекс
     */
    public void add(T data, int index) {
        // если список пуст, то работает только добавление по индексу 0
        if (head == null) {
            if (index == 0) {
                head = new Node<>(data);
            } else {
                return;
            }
        }

        // новый элемент
        Node<T> tmp = new Node<>(data);

        // пробежимся по списку, считая индексы элементов
        Node<T> current = head;
        for (int i = 0; i < index && current.getNext() != null; i++) {
            current = current.getNext();
        }

        // вставим новый элемент между существующими
        tmp.setNext(current.getNext());
        current.setNext(tmp);

        // увеличим размер
        size++;
    }

    /**
     * Получить элемент списка по индексу
     *
     * @param index индеус
     * @return элемент с искомым индексом, если он существует, иначе {@code null}
     */
    public Node<T> getNode(int index) {
        Node<T> current = head;
        int counter = 0;
        while (counter < index && current != null) {
            current = current.getNext();
            counter++;
        }
        return current;
    }

    /**
     * Получить данные элемента списка по индексу
     *
     * @param index индекс
     * @return данные, если элемент с искомым индексом существует, иначе {@code null}
     */
    public T get(int index) {
        // индексов меньше нуля не бывает
        // если список пуст, возвращать тоже нечего
        if (index < 0 || head == null) {
            return null;
        }

        // пробежимся по списку, считая индексы элементов
        Node<T> current = getNode(index);
        if (current == null) {
            return null;
        } else {
            return current.getData();
        }
    }

    /**
     * Удалить элемент из списка по индексу
     *
     * @param index индекс
     */
    public boolean remove(int index) {
        // если индекс неверный, либо список пуст, удаление не работает
        if (index < 0 || index >= size || head == null) {
            return false;
        }
        if (index == 0) {
            head = head.getNext();
        } else {
            Node<T> previous = getNode(index - 1);
            Node<T> element = previous.getNext();
            previous.setNext(element.getNext());
        }
        size--;
        return true;
    }

    /**
     * Сортировка списка вставками
     */
    public void sort() {
        Node<T> root = head;
        Node<T> newRoot = null;
        while (root != null) {
            Node<T> node = root;
            root = root.getNext();

            if (newRoot == null || isLess(node.getData(), newRoot.getData())) {
                node.setNext(newRoot);
                newRoot = node;
            } else {
                // перебираем элементы, пока текущий не встанет на нужное место
                Node<T> current = newRoot;
                while (current.getNext() != null && !isLess(node.getData(), current.getNext().getData())) {
                    current = current.getNext();
                }
                node.setNext(current.getNext());
                current.setNext(node);
            }
        }
        head = newRoot;
    }

    /**
     * @return размер списка
     */
    public int size() {
        return size;
    }

    /**
     * @return {@link Stream} для последовательного преобразования каждого отдельного элемента списка
     */
    public Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    @Override
    public String toString() {
        return "[ " + stream().
                map(Objects::toString).
                collect(Collectors.joining(" ")) + " ]";
    }

    /**
     * @return итератор для обхода всех элементов списка
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (hasNext()) {
                    T data = current.getData();
                    current = current.getNext();
                    return data;
                } else {
                    return null;
                }
            }
        };
    }

    /**
     * Применить некоторое действие ко всем элементам списка, используя итератор
     *
     * @param action действие
     */
    @Override
    public void forEach(Consumer<? super T> action) {
        for (T t : this) {
            action.accept(t);
        }
    }

    private static <T> boolean isLess(Comparable<T> o1, T o2) {
        return o1.compareTo(o2) < 0;
    }
}
