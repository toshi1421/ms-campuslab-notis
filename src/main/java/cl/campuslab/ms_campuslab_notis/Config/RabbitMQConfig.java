package cl.campuslab.ms_campuslab_notis.Config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @SuppressWarnings("removal")
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public DirectExchange cmdDirectExchange() {
        return new DirectExchange("cmd.direct");
    }

    @Bean
    public DirectExchange cmdDeadLetterExchange() {
        return new DirectExchange("cmd.dead.dlx");
    }

    @Bean
    public Queue emailQueue() {
        return QueueBuilder.durable("q.cmd.email")
                .withArgument("x-dead-letter-exchange", "cmd.dead.dlx")
                .withArgument("x-dead-letter-routing-key", "email.dlq.route")
                .build();
    }

    @Bean
    public Queue emailDlq() {
        return QueueBuilder.durable("q.cmd.email.dlq").build();
    }

    @Bean
    public Binding emailBinding(Queue emailQueue, DirectExchange cmdDirectExchange) {
        return BindingBuilder.bind(emailQueue).to(cmdDirectExchange).with("email.send");
    }

    @Bean
    public Binding emailDlqBinding(Queue emailDlq, DirectExchange cmdDeadLetterExchange) {
        return BindingBuilder.bind(emailDlq).to(cmdDeadLetterExchange).with("email.dlq.route");
    }

    @Bean
    public Queue prepQueue() {
        return QueueBuilder.durable("q.cmd.prep")
                .withArgument("x-dead-letter-exchange", "cmd.dead.dlx")
                .withArgument("x-dead-letter-routing-key", "prep.dlq.route")
                .build();
    }

    @Bean
    public Queue prepDlq() {
        return QueueBuilder.durable("q.cmd.prep.dlq").build();
    }

    @Bean
    public Binding prepBinding(Queue prepQueue, DirectExchange cmdDirectExchange) {
        return BindingBuilder.bind(prepQueue).to(cmdDirectExchange).with("prep.ticket");
    }

    @Bean
    public Binding prepDlqBinding(Queue prepDlq, DirectExchange cmdDeadLetterExchange) {
        return BindingBuilder.bind(prepDlq).to(cmdDeadLetterExchange).with("prep.dlq.route");
    }
}