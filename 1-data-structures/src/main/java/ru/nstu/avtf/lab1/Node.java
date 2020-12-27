package ru.nstu.avtf.lab1;

/**
 * Элемент односвязного списка
 *
 * @param <T> тип данных, хранящихся в элементе и списке
 */
public class Node<T extends Comparable<T>> {

    /**
     * Данные, хранящиеся в узле списка
     */
    private T data;

    /**
     * Следующий элемент
     */
    private Node<T> next;

    public Node() {
        data = null;
        next = null;
    }

    public Node(T data) {
        this.data = data;
        next = null;
    }

    /**
     * @return данные, хранящиеся в узле списка
     */
    public T getData() {
        return data;
    }

    /**
     * Записать в элемент списка данные
     *
     * @param e данные
     */
    public void setData(T e) {
        data = e;
    }

    /**
     * Получить следующий элемент в списке
     *
     * @return узел либо {@code null}, если это последний элемент
     */
    public Node<T> getNext() {
        return next;
    }

    /**
     * Связать элемент с другим элементом
     *
     * @param n новый следующий элемент
     */
    public void setNext(Node<T> n) {
        next = n;
    }
}