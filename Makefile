default: Controller

%: %.java
	javac --module-path /home/steven/java/graphics/javafx-sdk-11.0.2/lib 		--add-modules=javafx.controls Controller.java
	java --module-path /home/steven/java/graphics/javafx-sdk-11.0.2/lib          		--add-modules=javafx.controls Controller



