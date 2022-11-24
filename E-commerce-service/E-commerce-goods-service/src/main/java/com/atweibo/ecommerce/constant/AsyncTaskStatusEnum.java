package com.atweibo.ecommerce.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 异步任务状态枚举
 * */

@Getter
@AllArgsConstructor
public enum AsyncTaskStatusEnum {
    STARTED(0,"已经启动"),
    RUNNING(1,"正在运行"),
    SUCCESS(2,"成功"),
    FAIlED(3,"失败"),;


    /*执行状态编码*/
    private final int state;

    /*执行状态描述*/
    private final String stateInfo;
}
