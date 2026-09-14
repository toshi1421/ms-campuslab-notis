package cl.campuslab.ms_campuslab_notis.consumer;

import cl.campuslab.ms_campuslab_notis.dto.NotificationEvent;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class NotificationConsumer {

    @RabbitListener(queues = "q.cmd.email")
    public void consumeEmailQueue(NotificationEvent event, Channel channel, 
                                  @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            System.out.println("======================================");
            System.out.println("NUEVA NOTIFICACIÓN DE EMAIL");
            System.out.println("Destinatario: " + event.getPara());
            System.out.println("Mensaje: " + event.getMensaje());
            System.out.println("======================================");
            
            channel.basicAck(tag, false);
            
        } catch (Exception e) {
            System.err.println("Error al procesar el email: " + e.getMessage());
            channel.basicNack(tag, false, false); 
        }
    }

    @RabbitListener(queues = "q.cmd.prep")
    public void consumePrepTicketQueue(NotificationEvent event, Channel channel, 
                                       @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            System.out.println("======================================");
            System.out.println("NUEVO TICKET DE PREPARACIÓN");
            System.out.println("Técnico/Sala: " + event.getPara());
            System.out.println("Instrucciones: " + event.getMensaje());
            System.out.println("======================================");

            channel.basicAck(tag, false);
            
        } catch (Exception e) {
            System.err.println("Error al procesar el ticket: " + e.getMessage());
            channel.basicNack(tag, false, false);
        }
    }
}