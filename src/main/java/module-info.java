module project.tubespbo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires mysql.connector.j;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens project.tubespbo.Controller to javafx.fxml;
    exports project.tubespbo.Controller;
    exports project.tubespbo.Util;
    opens project.tubespbo.Util to javafx.fxml;
    exports project.tubespbo.Controller.Admin;
    opens project.tubespbo.Controller.Admin to javafx.fxml;
    opens project.tubespbo.Models to javafx.base;
    exports project.tubespbo.Models;
    exports project.tubespbo.Controller.Nasabah;
    opens project.tubespbo.Controller.Nasabah to javafx.fxml;
}