package cl.campuslab.ms_campuslab_notis.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public DirectExchange cmdDirectExchange() {
        return new DirectExchange("cmd.direct");
    }

    @Bean
    public Queue emailQueue() {
        return QueueBuilder.durable("q.cmd.email")
                .withArgument("x-dead-letter-exchange", "cmd.dead.dlx")
                .withArgument("x-dead-letter-routing-key", "email.dlq.route")
                .build();
    }

    @Bean
    public Queue prepQueue() {
        return QueueBuilder.durable("q.cmd.prep")
                .withArgument("x-dead-letter-exchange", "cmd.dead.dlx")
                .withArgument("x-dead-letter-routing-key", "prep.dlq.route")
                .build();
    }

    @Bean
    public Queue voucherQueue() {
        return QueueBuilder.durable("q.cmd.voucher")
                .withArgument("x-dead-letter-exchange", "cmd.dead.dlx")
                .withArgument("x-dead-letter-routing-key", "voucher.dlq.route")
                .build();
    }

    @Bean
    public Binding emailBinding(Queue emailQueue, DirectExchange cmdDirectExchange) {
        return BindingBuilder.bind(emailQueue).to(cmdDirectExchange).with("email.send");
    }

    @Bean
    public Binding prepBinding(Queue prepQueue, DirectExchange cmdDirectExchange) {
        return BindingBuilder.bind(prepQueue).to(cmdDirectExchange).with("prep.send");
    }

    @Bean
    public Binding voucherBinding(Queue voucherQueue, DirectExchange cmdDirectExchange) {
        return BindingBuilder.bind(voucherQueue).to(cmdDirectExchange).with("voucher.send");
    }
}