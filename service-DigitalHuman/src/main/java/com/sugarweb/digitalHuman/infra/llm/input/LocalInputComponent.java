package com.sugarweb.digitalHuman.infra.llm.input;

/**
 * 本地输入组件,用于测试
 *
 * @author xxd
 * @version 1.0
 */
public class LocalInputComponent {

    private final InputContainer inputContainer;

    public LocalInputComponent(InputContainer inputContainer) {
        this.inputContainer = inputContainer;
    }

    public void addInputContent(InputContent inputContent) {
        inputContainer.add(inputContent);
    }

}
