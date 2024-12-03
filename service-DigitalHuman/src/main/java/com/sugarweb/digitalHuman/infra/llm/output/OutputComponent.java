package com.sugarweb.digitalHuman.infra.llm.output;

/**
 * 输出组件
 * 针对各个消费者的统一处理
 *
 * @author xxd
 * @version 1.0
 */
public class OutputComponent {

    private final OutputContainer outputContainer;

    public OutputComponent(OutputContainer outputContainer) {
        this.outputContainer = outputContainer;
    }
}
