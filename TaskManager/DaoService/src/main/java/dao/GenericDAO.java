package dao;

public interface GenericDAO<T> {
    void create(T item);
}
