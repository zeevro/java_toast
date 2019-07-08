JAVAC=javac
JAR=jar

toast.jar: Toast.class
	$(JAR) -c -e Toast -f $@ Toast*.class

Toast.class: toast.java
	$(JAVAC) $^

clean:
	$(RM) *.jar *.class
