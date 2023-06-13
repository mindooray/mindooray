package com.nhnacademy.team4.mindooray.domain.type;

import lombok.Getter;

@Getter
public enum TaskStatus {
    TODO("할일"), IN_PROGRESS("진행중"), DONE("종료"), DELAYED("지연");

    private String name;

    TaskStatus(String name) {
        this.name = name;
    }
}
