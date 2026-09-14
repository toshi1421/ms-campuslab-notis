package cl.campuslab.ms_campuslab_notis.dto;

import java.time.LocalDateTime;

public class NotificationEvent {
    
    private String idEvento;
    private LocalDateTime marcaTiempo;
    private String idTraza;
    private String idCorrelacion;
    private String para; 
    private String mensaje; 
    private String tipo;

    public NotificationEvent() {}

    public String getTipo() { return tipo; } 
    public void setTipo(String tipo) { this.tipo = tipo; }
    
    public String getIdEvento() { return idEvento; }
    public void setIdEvento(String idEvento) { this.idEvento = idEvento; }
    
    public LocalDateTime getMarcaTiempo() { return marcaTiempo; }
    public void setMarcaTiempo(LocalDateTime marcaTiempo) { this.marcaTiempo = marcaTiempo; }
    
    public String getIdTraza() { return idTraza; }
    public void setIdTraza(String idTraza) { this.idTraza = idTraza; }
    
    public String getIdCorrelacion() { return idCorrelacion; }
    public void setIdCorrelacion(String idCorrelacion) { this.idCorrelacion = idCorrelacion; }

    public String getPara() { return para; }
    public void setPara(String para) { this.para = para; }

    public String getMensaje() { return mensaje; }
    public void setMessage(String mensaje) { this.mensaje = mensaje; }
}