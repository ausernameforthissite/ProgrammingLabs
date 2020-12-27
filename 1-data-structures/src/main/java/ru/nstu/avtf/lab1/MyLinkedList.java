package ru.nstu.avtf.lab1;

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
        Node<T> current = head.getNext();
        for (int i = 0; i < index; i++) {
            // если уже конец списка, но нужного индекса нет, значит, ничего не нашли
            if (current.getNext() == null) {
                return null;
            }

            current = current.getNext();
        }

        return current.getData();
    }

    /**
     * Удалить элемент из списка по индексу
     *
     * @param index индекс
     */
    public void remove(int index) {
        // если индекс неверный, либо список пуст, удаление не работает
        if (index < 0 || index >= size || head == null) {
            return;
        }

        // пробежимся по списку, считая индексы элементов
        // N.B. Храним ссылки на один элемент раньше по сравнению с get(),
        // чтобы соединить «предыдущий» элемент со «следующим»
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            // если уже конец списка, но нужного индекса нет, значит, ничего не нашли
            if (current.getNext() == null) {
                return;
            }
            current = current.getNext();
        }

        // соединим элементы списка в обход удаляемого
        current.setNext(current.getNext().getNext());

        // уменьшим размер
        size--;
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
