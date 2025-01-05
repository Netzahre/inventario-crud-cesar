module org.example.dad_aed_proyectocrudeas {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.naming;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.desktop;
    opens org.example.dad_aed_proyectocrudeas to javafx.fxml;
    opens modelo to org.hibernate.orm.core, javafx.base;
    exports org.example.dad_aed_proyectocrudeas;
    exports controlador;
    exports modelo;
    opens controlador to javafx.fxml;
    opens org.example.vistas to javafx.fxml;
}
