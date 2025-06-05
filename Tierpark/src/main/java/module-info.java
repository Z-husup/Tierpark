module com.prog.tierpark {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires static lombok;
    requires atlantafx.base;
    requires java.desktop;

    opens com.prog.tierpark to javafx.fxml;
    exports com.prog.tierpark;
    exports com.prog.tierpark.controller;
    opens com.prog.tierpark.controller to javafx.fxml;
    exports com.prog.tierpark.model;
    opens com.prog.tierpark.model to javafx.fxml;
    exports com.prog.tierpark.controller.admin;
    opens com.prog.tierpark.controller.admin to javafx.fxml;
    exports com.prog.tierpark.controller.animal;
    opens com.prog.tierpark.controller.animal to javafx.fxml;
    exports com.prog.tierpark.controller.enclosure;
    opens com.prog.tierpark.controller.enclosure to javafx.fxml;
    exports com.prog.tierpark.controller.worker;
    opens com.prog.tierpark.controller.worker to javafx.fxml;
}