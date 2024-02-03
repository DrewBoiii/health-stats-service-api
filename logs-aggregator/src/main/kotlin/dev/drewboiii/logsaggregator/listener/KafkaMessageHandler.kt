package dev.drewboiii.logsaggregator.listener

interface KafkaMessageHandler<T> {

    fun handleMessage(message: T)

}