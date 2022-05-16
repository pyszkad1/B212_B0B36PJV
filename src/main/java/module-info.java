module bonken {
    requires javafx.controls;
    requires javafx.graphics;
    requires java.logging;
    requires com.fasterxml.jackson.databind;
    exports bonken;
    exports bonken.game;
    opens bonken.game;
}
