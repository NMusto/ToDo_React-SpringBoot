package com.todo.todoApp.mapper;

public interface IMapper<I, O> {
    public O map(I in);
}
