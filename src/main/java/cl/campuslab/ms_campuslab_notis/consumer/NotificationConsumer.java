package cl.campuslab.ms_campuslab_notis.consumer;

import cl.campuslab.ms_campuslab_notis.dto.NotificationEvent;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class NotificationConsumer {

    private final JavaMailSender mailSender;

    public NotificationConsumer(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @RabbitListener(queues = "q.cmd.email")
    public void consumeEmailQueue(NotificationEvent event, Channel channel, 
                                  @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            System.out.println("Enviando correo real al usuario registrado: " + event.getPara());

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(event.getPara());
            mailMessage.setSubject("CampusLab - Notificación de Reserva");
            mailMessage.setText(event.getMensaje());

            mailSender.send(mailMessage);
            System.out.println("Correo enviado con éxito");

            channel.basicAck(tag, false);
            
        } catch (Exception e) {
            System.err.println("Error enviando correo, enviando a DLQ: " + e.getMessage());
            channel.basicNack(tag, false, false); 
        }
    }

    @RabbitListener(queues = "q.cmd.prep")
    public void consumePrepTicketQueue(NotificationEvent event, Channel channel, 
                                       @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            System.out.println("Procesando ticket de preparación para sala/técnico: " + event.getPara());
            System.out.println("Detalles: " + event.getMensaje());

            channel.basicAck(tag, false);
            
        } catch (Exception e) {
            System.err.println("Error en ticket de preparación: " + e.getMessage());
            channel.basicNack(tag, false, false);
        }
    }

    @RabbitListener(queues = "q.cmd.voucher")
    public void consumeVoucherQueue(NotificationEvent event, Channel channel, 
                                    @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            System.out.println("Generando comprobante/voucher para: " + event.getPara());
            
            channel.basicAck(tag, false);
            
        } catch (Exception e) {
            System.err.println("Error generando voucher: " + e.getMessage());
            channel.basicNack(tag, false, false);
        }
    }
}