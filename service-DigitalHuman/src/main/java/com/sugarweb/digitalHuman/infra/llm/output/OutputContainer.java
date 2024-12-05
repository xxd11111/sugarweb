package com.sugarweb.digitalHuman.infra.llm.output;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * OutputContainer
 *
 * @author xxd
 * @since 2024/10/19 14:45
 */
public class OutputContainer {

    private List<OutputConsumer> outputConsumers;

    public OutputContainer(List<OutputConsumer> outputConsumers) {
        this.outputConsumers = outputConsumers;
    }

    public void add(OutputContent outputContent) {
        outputConsumers.forEach(outputConsumer -> outputConsumer.accept(outputContent));
    }

}
